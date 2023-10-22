package cn.pledge.envconsole.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 以太坊工具类
 *
 * @Autor Tricky
 * @Date 2021-04-01 21:02:11
 */
@Slf4j
public class EthUtils {

    public static final BigDecimal ETH_DECIMALS = new BigDecimal(1_000_000_000_000_000_000L);

    public static final BigInteger ETH_GAS_LIMIT = new BigInteger("100000");

    /**
     * 获取区块数据
     *
     * @param web3j
     * @param block                  块高
     * @param fullTransactionObjects 是否需要交易数据
     * @return
     */
    public static EthBlock getBlock(Web3j web3j, long block, boolean fullTransactionObjects) {
        try {
            return web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(block), fullTransactionObjects).send();
        } catch (Throwable t) {
            log.error(String.format("Get Block Error %d", block), t);
        }
        return null;
    }

    /**
     * 获取当前块高
     *
     * @param web3j
     * @return
     */
    public static long getNowBlockNumber(Web3j web3j) {
        try {
            EthBlockNumber send = web3j.ethBlockNumber().send();
            return send.getBlockNumber().longValue();
        } catch (Throwable t) {
            log.error("GetBlockNumberError", t);
        }
        return -1;
    }

    /**
     * 发送erc20
     *
     * @param web3j
     * @param contractAddress 合约地址
     * @param privateKey      私钥
     * @param to              收款地址
     * @param value           额度
     * @return
     */
    public static String sendErc20(Web3j web3j, String contractAddress, String privateKey,
                                   String to, BigInteger value) {
        String from = getAddressByPrivateKey(privateKey);
        log.info(String.format("Start:SendErc20 from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
        try {
            //加载转账所需的凭证，用私钥
            Credentials credentials = Credentials.create(privateKey);
            //获取nonce，交易笔数
            BigInteger nonce = getNonce(web3j, from);
            if (nonce == null) {
                log.error(String.format("END:GetNonceError from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
                return null;
            }
            //gasPrice和gasLimit 都可以手动设置
            BigInteger gasPrice = getGasPrice(web3j);
            if (gasPrice == null) {
                log.error(String.format("END:GetGasPriceError from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
                return null;
            }
            //BigInteger.valueOf(4300000L) 如果交易失败 很可能是手续费的设置问题
            BigInteger gasLimit = BigInteger.valueOf(60000L);
            //ERC20代币合约方法
            Function function = new Function(
                    "transfer",
                    Arrays.asList(new Address(to), new Uint256(value)),
                    Collections.singletonList(new TypeReference<Type>() {
                    }));
            //创建RawTransaction交易对象
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                    contractAddress, encodedFunction);

            //签名Transaction
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //发送交易
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            if (hash != null) {
                return hash;
            }
            log.error(String.format("END:HashIsNull from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
        } catch (Throwable t) {
            log.error(String.format("发送ERC20失败 from=%s to=%s erc20=%s amount=%s",
                    from, to, contractAddress, value.toString()), t);
        }
        return null;
    }

    /**
     * 列出交易信息
     *
     * @param block  区块高度
     * @param filter 过滤器
     * @return
     */
    public static List<EthBlock.TransactionResult> getTransactions(Web3j web3j, long block, java.util.function.Function<EthBlock.TransactionResult, Boolean> filter) {
        EthBlock send = getBlock(web3j, block, true);
        if (send == null) {
            log.error(String.format("GetBlockDataError:%d", block));
            return Collections.emptyList();
        }
        List<EthBlock.TransactionResult> transactions = send.getBlock().getTransactions();
        if (filter != null) {
            List<EthBlock.TransactionResult> result = new ArrayList<>();
            for (EthBlock.TransactionResult e : transactions) {
                try {
                    if (filter.apply(e)) {
                        result.add(e);
                    }
                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                }
            }
            return result;
        }
        return transactions;

    }

    /**
     * 根据私钥获取地址
     *
     * @param privateKey
     * @return
     */
    public static String getAddressByPrivateKey(String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        return "0x" + Keys.getAddress(ecKeyPair).toLowerCase();
    }



    /**
     * 查询地址以太坊数量
     *
     * @param web3j
     * @param address 查询地址
     * @return
     */
    public static BigDecimal balanceOf(Web3j web3j, String address) {
        try {
            EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            BigInteger amount = balance.getBalance();
            if (amount == null || amount.compareTo(BigInteger.ZERO) <= 0) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(amount).divide(ETH_DECIMALS, 18, RoundingMode.FLOOR);
        } catch (Throwable t) {
            log.error(String.format("获取以太坊数量出错 %s", address), t);
        }
        return BigDecimal.ZERO;
    }


    /**
     * 转换成最小单位 Wei
     *
     * @param ethAmount
     * @return
     */
    public static BigInteger toWei(BigDecimal ethAmount) {
        return ethAmount.multiply(ETH_DECIMALS).toBigInteger();
    }

    /**
     * wei to eth
     *
     * @param wei
     * @return
     */
    public static BigDecimal toEth(BigInteger wei) {
        return new BigDecimal(wei).divide(ETH_DECIMALS, 18, RoundingMode.FLOOR);
    }
    /**
     * erc20代币转账
     *
     * @param from            转账地址
     * @param to              收款地址
     * @param value           转账金额
     * @param privateKey      转账这私钥
     * @param contractAddress 代币合约地址
     * @return 交易哈希
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public static String transferERC20Token(Web3j web3j ,String from, String to, BigInteger value, String privateKey, String contractAddress) throws ExecutionException, InterruptedException, IOException {
//        加载转账所需的凭证，用私钥
        Credentials credentials = Credentials.create(privateKey);
        //获取nonce，交易笔数
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.PENDING).send();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice和gasLimit 都可以手动设置
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        System.out.println(BigDecimal.valueOf(gasPrice.doubleValue()).divide(BigDecimal.valueOf(1000000)));

        //BigInteger.valueOf(4300000L) 如果交易失败 很可能是手续费的设置问题

        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20代币合约方法
//        value = value.multiply(VALUE);

        Function function = new Function(
                "TRANSFER1",
                Arrays.asList(new Address(to), new Uint256(value)),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //创建RawTransaction交易对象
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                contractAddress, encodedFunction);
        //签名Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //发送交易
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }
    /**
     * 查询erc20的余额
     *
     * @param web3j
     * @param contract 合约地址
     * @param address  查询地址
     * @return
     */
    public static BigInteger balanceOfErc20(Web3j web3j, String contract, String address) {
        try {
            final String DATA_PREFIX = "0x70a08231000000000000000000000000";
            String value = web3j.ethCall(org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address,
                    contract, DATA_PREFIX + address.substring(2)), DefaultBlockParameterName.PENDING).send().getValue();
            if (StrUtil.isBlank(value)) {
                return BigInteger.ZERO;
            }
            if (StringUtils.isEmpty(value)) {
                return BigInteger.ZERO;
            }

            return new BigInteger(value.substring(2), 16);
        } catch (Throwable t) {
            log.error(String.format("查询ERC20失败 contract:%s address:%s", contract, address), t);
        }
        return BigInteger.ZERO;
    }

    /**
     * 查询trc20的余额
     *
     *
     * @param contract 合约地址
     * @param address  查询地址
     * @return
     */
    public static BigInteger balanceOfTrc20(String contract, String address) {
        try {
            String tronUrl = "https://api.trongrid.io";
            String hexAddress = TronUtils.toHexAddress(address);

            String hexContract = TronUtils.toHexAddress(contract);

            String url = tronUrl +"/wallet/triggerconstantcontract";
            JSONObject param = new JSONObject();

            param.put("owner_address", hexAddress);
            param.put("contract_address", hexContract);
            param.put("function_selector", "balanceOf(address)");
            List<Type> inputParameters = new ArrayList<>();
            inputParameters.add(new Address(hexAddress.substring(2)));
            param.put("parameter", FunctionEncoder.encodeConstructor(inputParameters));
            String result = HttpUtils.HttpPostWithJson(url, param.toJSONString());
            if (StringUtils.isNotEmpty(result)) {
                JSONObject obj = JSONObject.parseObject(result);
                JSONArray results = obj.getJSONArray("constant_result");
                if (results != null && results.size() > 0) {
                    BigInteger amount = new BigInteger(results.getString(0), 16);
                    return amount;
                }
            }
        } catch (Throwable t) {
            log.error(String.format("查询TRC20失败 contract:%s address:%s", contract, address), t);
        }
        return BigInteger.ZERO;
    }


    /**
     * 获取gas-price
     *
     * @param web3j
     * @return
     */
    public static BigInteger getGasPrice(Web3j web3j) {
        try {
            EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
            if (ethGasPrice == null) {
                log.error("GetGasPriceError");
                return null;
            }
            return ethGasPrice.getGasPrice();
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
        return null;
    }

    /**
     * 获取nonce
     *
     * @param web3j
     * @param address
     * @return
     */
    public static BigInteger getNonce(Web3j web3j, String address) {
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            if (ethGetTransactionCount == null) {
                log.error("GetNonceError:" + address);
                return null;
            }
            return ethGetTransactionCount.getTransactionCount();
        } catch (Throwable t) {
            log.error("GetNonceError:" + address);
        }
        return null;
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param wei        wei为单位的数量
     * @param gasPrice   gas-price
     * @param gasLimit   gas-limit
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigInteger wei, BigInteger gasPrice, BigInteger gasLimit) {
        String from = getAddressByPrivateKey(privateKey);
        try {
            //加载转账所需的凭证，用私钥
            Credentials credentials = Credentials.create(privateKey);
            //获取nonce，交易笔数
            BigInteger nonce = getNonce(web3j, from);
            //创建RawTransaction交易对象
            Function function = new Function(
                    "TRANSFER1",
                    Arrays.asList(new Address(to), new Uint256(wei)),
                    Collections.singletonList(new TypeReference<Type>() {
                    }));
            //创建RawTransaction交易对象
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, wei,encodedFunction);
            //签名Transaction，这里要对交易做签名
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //发送交易
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            return ethSendTransaction.getTransactionHash();
        } catch (Throwable t) {
            log.error(String.format("发送ETH失败 from:%s to:%s amount-eth:%s", from, to, toEth(wei).toString()));
        }
        return null;
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param wei        wei为单位的数量
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigInteger wei) {
        return sendEth(web3j, privateKey, to, wei, getGasPrice(web3j), ETH_GAS_LIMIT);
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param eth        wei为单位的数量
     * @param gasPrice   gas-price
     * @param gasLimit   gas-limit
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigDecimal eth, BigInteger gasPrice, BigInteger gasLimit) {
        return sendEth(web3j, privateKey, to, toWei(eth), gasPrice, gasLimit);
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param eth        wei为单位的数量
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigDecimal eth) {
        return sendEth(web3j, privateKey, to, toWei(eth), getGasPrice(web3j), ETH_GAS_LIMIT);
    }

    /**
     * 根据hash获取交易信息
     * @param web3j
     * @param hash
     * @return
     */
    public static EthTransaction getTransaction(Web3j web3j, String hash) {
        try {
            EthTransaction tx = web3j.ethGetTransactionByHash(hash).send();
            return tx;
        } catch (Throwable t) {
            log.error("GetTransactionError:" + hash, t);
        }
        return null;
    }
}

