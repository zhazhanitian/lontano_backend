package cn.pledge.envconsole.book.task;

import cn.pledge.envconsole.book.entity.*;
import cn.pledge.envconsole.book.mapper.*;
import cn.pledge.envconsole.book.model.enums.PledgeType;
import cn.pledge.envconsole.common.utils.EthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 89466
 */
@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class RewardTask {
    @Autowired
    private ExperienceGoldRecordMapper experienceGoldRecordMapper;
    @Autowired
    private FlowRecordMapper flowRecordMapper;
    @Autowired
    private PledgeRecordMapper pledgeRecordMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private UserMapper userMapper;
    @Value("${contractAddress}")

    private String contractAddress;
    @Scheduled(cron = "0 0 3 * * ?")
    @Async
    public void experienceGold() {
        log.info("体验金收益定时任务"+ LocalDateTime.now());
        List<ExperienceGoldRecord> list = experienceGoldRecordMapper.selectHasReward();
        for (ExperienceGoldRecord experienceGoldRecord : list) {
            Integer experienceTime = experienceGoldRecord.getExperienceTime();
            experienceGoldRecord.setExperienceTime(experienceTime-1);
            if (experienceGoldRecord.getProfitSwitch()){
                Double amount = experienceGoldRecord.getAmount();
                Double income = experienceGoldRecord.getIncome();
                String profit = experienceGoldRecord.getProfit();
                BigDecimal reward = BigDecimal.valueOf(amount).multiply(new BigDecimal(profit)).multiply(new BigDecimal("0.01"));
                BigDecimal totalIncome = BigDecimal.valueOf(income).add(reward);
                experienceGoldRecord.setIncome(totalIncome.doubleValue());
                Statistics statistics = statisticsMapper.selectOneByUserId(experienceGoldRecord.getUserId());
                if (statistics!=null){
                    Double totalExperienceReward = statistics.getTotalExperienceReward();
                    Double unreceivedExperienceReward = statistics.getUnreceivedExperienceReward();
                    statistics.setUnreceivedExperienceReward(new BigDecimal(unreceivedExperienceReward).add(reward).doubleValue());
                    statistics.setTotalExperienceReward(new BigDecimal(totalExperienceReward).add(reward).doubleValue());
                    statisticsMapper.updateByPrimaryKeySelective(statistics);
                }
            }
            experienceGoldRecordMapper.updateByPrimaryKeySelective(experienceGoldRecord);

        }
    }

    @Scheduled(cron = "0 0 0/6 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void  flowReward(){
       List<FlowRecord> flowRecordList = flowRecordMapper.selectAll();
    for (FlowRecord flowRecord : flowRecordList) {

        Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/77c83ab9cfd746918a9b188a7692fa00"));
        BigInteger bigInteger = EthUtils.balanceOfErc20(web3j, contractAddress, flowRecord.getUserAddress());

        BigDecimal amount = BigDecimal.valueOf(bigInteger.doubleValue()).divide(BigDecimal.valueOf(1000000));

        //比对结果相同
        if (flowRecord.getAmount().compareTo(amount.doubleValue())==0) {
            User user = userMapper.selectByPrimaryKey(flowRecord.getUserId());
            if (user!=null && user.getIsFlowReward()&& flowRecord.getAmount()!=0){
                Integer time = flowRecord.getTime()+1;
                flowRecord.setTime(time);
                if (time%4==0){
                    BigDecimal period = new BigDecimal(flowRecord.getPeriod());
                    BigDecimal reward = amount.multiply(period).multiply(new BigDecimal("0.01"));
                    Statistics statistics = statisticsMapper.selectOneByUserId(flowRecord.getUserId());
                    if (statistics!=null){
                        Double unreceivedFlowReward = statistics.getUnreceivedFlowReward();
                        Double totalFlowReward = statistics.getTotalFlowReward();
                        statistics.setUnreceivedFlowReward(BigDecimal.valueOf(unreceivedFlowReward).add(reward).doubleValue());
                        statistics.setTotalFlowReward(BigDecimal.valueOf(totalFlowReward).add(reward).doubleValue());
                        flowRecord.setTime(0);
                        statisticsMapper.updateByPrimaryKeySelective(statistics);
                    }
                }

            }
        }else {
            flowRecord.setAmount(amount.doubleValue());
            flowRecord.setTime(0);
        }
        flowRecord.setUpdateTime(LocalDateTime.now());
        flowRecordMapper.updateByPrimaryKeySelective(flowRecord);
    }
    }

    @Scheduled(cron = "0 30 3 * * ?")
    @Async
    public void pledgeReward() {
        log.info("质押收益定时任务"+ LocalDateTime.now());
       List<PledgeRecord> pledgeRecordList = pledgeRecordMapper.selectAll();
        LocalDateTime now = LocalDateTime.now();
        for (PledgeRecord pledgeRecord : pledgeRecordList) {
            if (pledgeRecord.getStopTime().compareTo(now)<=0) {
                pledgeRecord.setStatus(PledgeType.APPLY.toString());
            }
            if (pledgeRecord.getIsReward()) {
                Double amount = pledgeRecord.getAmount();
                String profit = pledgeRecord.getProfit();
                Double income = pledgeRecord.getIncome();
                BigDecimal reward = new BigDecimal(profit).multiply(BigDecimal.valueOf(amount)).multiply(new BigDecimal("0.01"));
                pledgeRecord.setIncome(BigDecimal.valueOf(income).add(reward).doubleValue());
                Statistics statistics = statisticsMapper.selectOneByUserId(pledgeRecord.getUserId());
                if (statistics!=null) {
                    Double unreceivedPledgeReward = statistics.getUnreceivedPledgeReward();
                    Double totalPledgeReward = statistics.getTotalPledgeReward();
                    statistics.setTotalPledgeReward(BigDecimal.valueOf(totalPledgeReward).add(reward).doubleValue());
                    statistics.setUnreceivedPledgeReward(BigDecimal.valueOf(unreceivedPledgeReward).add(reward).doubleValue());
                    statisticsMapper.updateByPrimaryKeySelective(statistics);
                }
            }
            pledgeRecordMapper.updateByPrimaryKeySelective(pledgeRecord);

    }
    }


}

