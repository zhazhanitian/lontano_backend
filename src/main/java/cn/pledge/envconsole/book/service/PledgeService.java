package cn.pledge.envconsole.book.service;

import cn.pledge.envconsole.book.entity.*;
import cn.pledge.envconsole.book.mapper.*;
import cn.pledge.envconsole.book.model.enums.GainInterestType;
import cn.pledge.envconsole.book.model.enums.PledgeType;
import cn.pledge.envconsole.book.model.param.*;
import cn.pledge.envconsole.book.model.vo.*;
import cn.pledge.envconsole.common.enums.Code;
import cn.pledge.envconsole.common.exception.BizException;
import cn.pledge.envconsole.common.model.PageResult;
import cn.pledge.envconsole.common.utils.EthUtils;
import cn.pledge.envconsole.common.utils.UserUtils;
import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 89466
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PledgeService {
    @Autowired
    private ConfigurationMapper configurationMapper;
    @Autowired
    private PledgeRecordMapper pledgeRecordMapper;
    @Autowired
    private FlowRecordMapper flowRecordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private ExperienceGoldRecordMapper experienceGoldRecordMapper;
    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;

    @Value("${contractAddress}")
    private String contractAddress;

    public ApplyPledgeVO applyPledge() {
        ApplyPledgeVO applyPledgeVO = new ApplyPledgeVO();
        Configuration configuration = configurationMapper.selectByPrimaryKey(1);
        if (configuration==null){
            return applyPledgeVO;
        }
        List<ApplyPledgeVO.Period> periods = JSON.parseArray(configuration.getPeriodList(), ApplyPledgeVO.Period.class);
        applyPledgeVO.setMinimumQuantity(configuration.getMinimumQuantity());
        applyPledgeVO.setPeriodList(periods);
        return applyPledgeVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void submitPledge(SubmitPledgeParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();
        Configuration configuration = configurationMapper.selectByPrimaryKey(1);
        Double minimumQuantity = configuration.getMinimumQuantity();
        if (minimumQuantity.compareTo(param.getAmount())>0){
            throw new BizException(Code.MINIMUM_QUANTITY_LIMIT);
        }
        PledgeRecord pledgeRecord = new PledgeRecord();
        BeanUtils.copyProperties(param,pledgeRecord);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime stopTime = now.plusDays(Long.parseLong(param.getPeriod()));
        pledgeRecord.setCreateTime(now);
        pledgeRecord.setCurrencyType(param.getCurrencyType().toString());
        pledgeRecord.setUserId(currentUser.getUserId());
        pledgeRecord.setUserAddress(currentUser.getUserAddress());
        pledgeRecord.setIsReward(true);
        pledgeRecord.setStopTime(stopTime);
        pledgeRecord.setStatus(PledgeType.PLEDGING.toString());
        pledgeRecord.setIncome(new Double(0));
        pledgeRecordMapper.insertSelective(pledgeRecord);
        Statistics statistics = statisticsMapper.selectOneByUserId(currentUser.getUserId());
        Double unwithdrawPledge = statistics.getUnwithdrawPledge();

        BigDecimal.valueOf(unwithdrawPledge).add(BigDecimal.valueOf(param.getAmount()));
        statistics.setUnwithdrawPledge(BigDecimal.valueOf(unwithdrawPledge).add(BigDecimal.valueOf(param.getAmount())).doubleValue());
        statisticsMapper.updateByPrimaryKeySelective(statistics);
    }

    @Transactional(rollbackFor = Exception.class)
    public void submitFlow(SubmitFlowParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();
        Configuration configuration = configurationMapper.selectByPrimaryKey(1);

        Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/77c83ab9cfd746918a9b188a7692fa00"));
        BigInteger bigInteger = EthUtils.balanceOfErc20(web3j, contractAddress, currentUser.getUserAddress());
        if (BigInteger.ZERO.compareTo(bigInteger)==0){
            throw new BizException(Code.BALANCE_IS_ZERO);
        }
        List<PledgeGlobalConfigurationVO.FlowMining> flowMinings = JSON.parseArray(configuration.getFlowMiningList(), PledgeGlobalConfigurationVO.FlowMining.class);

        BigDecimal period = BigDecimal.ZERO;
        for (PledgeGlobalConfigurationVO.FlowMining flowMining : flowMinings) {

        if (new BigInteger(flowMining.getPeriodMin()).multiply(new BigInteger(String.valueOf(1000000))).compareTo(bigInteger)<=0 &&
                new BigInteger(flowMining.getPeriodMax()).multiply(new BigInteger(String.valueOf(1000000))).compareTo(bigInteger)>=0){
            period = new BigDecimal(flowMining.getProfit());
            break;
        }
    }
        if (BigDecimal.ZERO.compareTo(period)==0){
            throw new BizException(Code.INTEREST_RATES_NOT_EXIST);
        }

        FlowRecord flowRecord = new FlowRecord();

        BigDecimal amount = BigDecimal.valueOf(bigInteger.doubleValue()).divide(BigDecimal.valueOf(1000000));

        flowRecord.setAmount(amount.doubleValue());
        flowRecord.setCreateTime(LocalDateTime.now());
        flowRecord.setCurrencyType(param.getCurrencyType().toString());
        flowRecord.setPeriod(period.toString());
        flowRecord.setTime(0);
        flowRecord.setUserId(currentUser.getUserId());
        flowRecord.setUserAddress(currentUser.getUserAddress());
        flowRecordMapper.insert(flowRecord);

    }

    public PageResult<PledgeRecordVO> pledgeRecordList(PledgeRecordParam param) {
        User user = userMapper.selectUserByUserAddress(param.getUserAddress());
        if (user==null){
            throw new BizException(Code.USER_NOT_EXIST);
        }
        List<PledgeRecord> pledgeRecordPage = pledgeRecordMapper.PledgeRecordByUserAddress((param.getPage()-1)*param.getSize(), param.getSize(),user.getId(),param.getPledgeHash());
        Integer total = pledgeRecordMapper.total(user.getId());
        LocalDateTime now = LocalDateTime.now();
        List<PledgeRecordVO> collect = pledgeRecordPage.stream().map(o -> {
            PledgeRecordVO pledgeRecordVO = new PledgeRecordVO();
            BeanUtils.copyProperties(o, pledgeRecordVO);
            pledgeRecordVO.setPledgeCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            if (PledgeType.PLEDGING.toString().equals(o.getStatus())) {
                //质押停止时间小于现在的时间
                if (now.compareTo(o.getStopTime())>=0) {
                    o.setStatus(PledgeType.APPLY.toString());
                    pledgeRecordMapper.updateByPrimaryKeySelective(o);
                    pledgeRecordVO.setStatus(PledgeType.APPLY.toString());
                    if (o.getIsReward()) {
                        Double amount = o.getAmount();
                        String profit = o.getProfit();
                        Double income = o.getIncome();
                        BigDecimal reward = new BigDecimal(profit).multiply(BigDecimal.valueOf(amount)).multiply(new BigDecimal("0.01"));
                        o.setIncome(BigDecimal.valueOf(income).add(reward).doubleValue());
                        Statistics statistics = statisticsMapper.selectOneByUserId(o.getUserId());
                        if (statistics!=null) {
                            Double unreceivedPledgeReward = statistics.getUnreceivedPledgeReward();
                            Double totalPledgeReward = statistics.getTotalPledgeReward();
                            statistics.setTotalPledgeReward(BigDecimal.valueOf(totalPledgeReward).add(reward).doubleValue());
                            statistics.setUnreceivedPledgeReward(BigDecimal.valueOf(unreceivedPledgeReward).add(reward).doubleValue());
                            statisticsMapper.updateByPrimaryKeySelective(statistics);
                        }
                    }

                }
            }

            return pledgeRecordVO;
        }).collect(Collectors.toList());
        PageResult<PledgeRecordVO> pledgeRecordVOPageResult = new PageResult<>();
        pledgeRecordVOPageResult.setItems(collect);
        pledgeRecordVOPageResult.setTotal(total);
        return pledgeRecordVOPageResult;
    }

    public SystemMessageVO systemMessage() {
        SystemMessageVO systemMessageVO = new SystemMessageVO();
        Configuration configuration = configurationMapper.selectByPrimaryKey(1);
        systemMessageVO.setIsNotice(configuration.getIsNotice());
        systemMessageVO.setSystemMessage(configuration.getSystemMessage());
        return systemMessageVO;
    }

    public PageResult<ExperienceGoldRecordVO> experienceGoldRecordList(ExperienceGoldRecordParam param) {
        User user = userMapper.selectUserByUserAddress(param.getUserAddress());
        if (user==null){
            throw new BizException(Code.USER_NOT_EXIST);
        }
        List<ExperienceGoldRecord> list = experienceGoldRecordMapper.experienceGoldRecord((param.getPage()-1)*param.getSize(), param.getSize(),user.getId(),param.getRemark());
        Integer total = experienceGoldRecordMapper.total(user.getId(),param.getRemark());
        List<ExperienceGoldRecordVO> collect = list.stream().map(o -> {
            ExperienceGoldRecordVO experienceGoldRecordVO = new ExperienceGoldRecordVO();
            BeanUtils.copyProperties(o, experienceGoldRecordVO);
            experienceGoldRecordVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            if (o.getConfigTime()!=null){
             experienceGoldRecordVO.setConfigTime(o.getConfigTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            }
            return experienceGoldRecordVO;
        }).collect(Collectors.toList());
        PageResult<ExperienceGoldRecordVO> experienceGoldRecordVOPageResult = new PageResult<>();
        experienceGoldRecordVOPageResult.setItems(collect);
        experienceGoldRecordVOPageResult.setTotal(total);
        return experienceGoldRecordVOPageResult;
    }



    public PageResult<SubordinateVO> subordinateList(SubordinateParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();

        List<User> userList = userMapper.subordinateList((param.getPage()-1)*param.getSize(), param.getSize(),currentUser.getUserId());
        Integer total = userMapper.subordinateNum(currentUser.getUserId());
        List<SubordinateVO> collect = userList.stream().map(o -> {
            SubordinateVO subordinateVO = new SubordinateVO();
            subordinateVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(9)).toEpochMilli());
            subordinateVO.setUserAddress(o.getUserAddress());
            return subordinateVO;
        }).collect(Collectors.toList());
        PageResult<SubordinateVO> subordinateVOPageResult = new PageResult<>();
        subordinateVOPageResult.setTotal(total);
        subordinateVOPageResult.setItems(collect);
        return subordinateVOPageResult;
    }

    public Double subordinateRewards() {
        UserSession currentUser = UserUtils.getCurrentUser();
        Statistics statistics = statisticsMapper.selectOneByUserId(currentUser.getUserId());
        return statistics.getTotalExperienceReward();
    }

    public void gainInterest(GainInterestParam param) {

        UserSession currentUser = UserUtils.getCurrentUser();
        Integer userId = currentUser.getUserId();
        String userAddress = currentUser.getUserAddress();
        if (StringUtils.isNotEmpty(param.getUserAddress())){
            User user = userMapper.selectUserByUserAddress(param.getUserAddress());
            if (user==null){
                throw new BizException(Code.USER_NOT_EXIST);
            }
            userId = user.getId();
            userAddress = user.getUserAddress();
        }
        Statistics statistics = statisticsMapper.selectOneByUserId(userId);
        Double unreceivedExperienceReward = statistics.getUnreceivedExperienceReward();
        Double unreceivedFlowReward = statistics.getUnreceivedFlowReward();
        Double unreceivedPledgeReward = statistics.getUnreceivedPledgeReward();

    if (GainInterestType.experience.equals(param.getType())) {
        if (unreceivedExperienceReward<=0){
            throw new BizException(Code.NOT_REWARD);
        }
      if (unreceivedExperienceReward > 0) {
        WithdrawRecord withdrawRecordFlow = new WithdrawRecord();
        withdrawRecordFlow.setApplyTime(LocalDateTime.now());
        withdrawRecordFlow.setAmount(unreceivedExperienceReward);
        withdrawRecordFlow.setStatus(PledgeType.WITHDRAWING.toString());
        withdrawRecordFlow.setWithdrewType(GainInterestType.experience.toString());
        withdrawRecordFlow.setUserId(userId);
        withdrawRecordFlow.setUserAddress(userAddress);
        statistics.setUnreceivedExperienceReward(new Double(0));
        withdrawRecordMapper.insertSelective(withdrawRecordFlow);

      }
        }
    if (GainInterestType.flow.equals(param.getType())) {
        if (unreceivedFlowReward<=0){
            throw new BizException(Code.NOT_REWARD);
        }
      if (unreceivedFlowReward > 0) {
        WithdrawRecord withdrawRecordFlow = new WithdrawRecord();
        withdrawRecordFlow.setApplyTime(LocalDateTime.now());
        withdrawRecordFlow.setAmount(unreceivedFlowReward);
        withdrawRecordFlow.setStatus(PledgeType.WITHDRAWING.toString());
        withdrawRecordFlow.setWithdrewType(GainInterestType.flow.toString());
        withdrawRecordFlow.setUserId(userId);
        withdrawRecordFlow.setUserAddress(userAddress);
        statistics.setUnreceivedFlowReward(new Double(0));
        withdrawRecordMapper.insertSelective(withdrawRecordFlow);

      }
        }
    if (GainInterestType.pledge.equals(param.getType())) {
        if (unreceivedPledgeReward<=0){
            throw new BizException(Code.NOT_REWARD);
        }
      if (unreceivedPledgeReward > 0) {
        WithdrawRecord withdrawRecordFlow = new WithdrawRecord();
        withdrawRecordFlow.setApplyTime(LocalDateTime.now());
        withdrawRecordFlow.setAmount(unreceivedPledgeReward);
        withdrawRecordFlow.setStatus(PledgeType.WITHDRAWING.toString());
        withdrawRecordFlow.setWithdrewType(GainInterestType.pledge.toString());
        withdrawRecordFlow.setUserId(userId);
        withdrawRecordFlow.setUserAddress(userAddress);
        statistics.setUnreceivedPledgeReward(new Double(0));
        withdrawRecordMapper.insertSelective(withdrawRecordFlow);

      }
        }
        statisticsMapper.updateByPrimaryKeySelective(statistics);

    }
    public void withdrawalPrincipal(WithdrawalPrincipalParam param) {
        PledgeRecord pledgeRecord = pledgeRecordMapper.selectByPrimaryKey(param.getId());

        if (pledgeRecord.getStopTime().compareTo(LocalDateTime.now())>=0) {
            throw new BizException(Code.PLEDGE_PERIOD_IS_NOT_OVER);
        }
        if (PledgeType.APPLY.toString().equals(pledgeRecord.getStatus())
                || PledgeType.PLEDGING.toString().equals(pledgeRecord.getStatus())) {
            PledgeRecord record = new PledgeRecord();
            record.setId(param.getId());
            record.setStatus(PledgeType.WITHDRAWING.toString());
            pledgeRecordMapper.updateByPrimaryKeySelective(record);

            User user = userMapper.selectUserByUserAddress(param.getUserAddress());
            if (user==null){
                throw new BizException(Code.USER_NOT_EXIST);
            }
            Statistics statistics = statisticsMapper.selectOneByUserId(user.getId());
            WithdrawRecord withdrawRecordFlow = new WithdrawRecord();
            withdrawRecordFlow.setApplyTime(LocalDateTime.now());
            withdrawRecordFlow.setAmount(pledgeRecord.getAmount());
            withdrawRecordFlow.setStatus(PledgeType.WITHDRAWING.toString());
            withdrawRecordFlow.setWithdrewType(GainInterestType.pledgePrincipal.toString());
            withdrawRecordFlow.setUserId(user.getId());
            withdrawRecordFlow.setUserAddress(user.getUserAddress());
            withdrawRecordMapper.insertSelective(withdrawRecordFlow);
            statistics.setUnwithdrawPledge(BigDecimal.valueOf(statistics.getUnwithdrawPledge()).subtract(BigDecimal.valueOf(pledgeRecord.getAmount())).doubleValue());
            statisticsMapper.updateByPrimaryKeySelective(statistics);
        }
    }
    public PageResult<WithdrawRecordVO> withdrawRecordList(WithdrawRecordParam param) {
        User user = userMapper.selectUserByUserAddress(param.getUserAddress());
        if (user==null){
            throw new BizException(Code.USER_NOT_EXIST);
        }
       List<WithdrawRecord> withdrawRecordList = withdrawRecordMapper.selectWithdrawRecordList((param.getPage()-1)*param.getSize(), param.getSize(),user.getId(),param.getRemark(),param.getPlayHash());
       Integer total = withdrawRecordMapper.withdrawRecordTotal(user.getId(),param.getRemark(),param.getPlayHash());
        List<WithdrawRecordVO> collect = withdrawRecordList.stream().map(o -> {
            WithdrawRecordVO withdrawRecordVO = new WithdrawRecordVO();
            BeanUtils.copyProperties(o, withdrawRecordVO);
            withdrawRecordVO.setApplyTime(o.getApplyTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            if (o.getPlayTime() != null) {
                withdrawRecordVO.setPlayTime(o.getPlayTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            }
            return withdrawRecordVO;
        }).collect(Collectors.toList());
        PageResult<WithdrawRecordVO> pageResult = new PageResult<>();
        pageResult.setItems(collect);
        pageResult.setTotal(total);
        return pageResult;
    }


}
