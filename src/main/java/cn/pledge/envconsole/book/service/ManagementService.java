package cn.pledge.envconsole.book.service;

import cn.pledge.envconsole.book.entity.*;
import cn.pledge.envconsole.book.mapper.*;
import cn.pledge.envconsole.book.model.enums.GainInterestType;
import cn.pledge.envconsole.book.model.enums.PledgeType;
import cn.pledge.envconsole.book.model.enums.RoleType;
import cn.pledge.envconsole.book.model.param.*;
import cn.pledge.envconsole.book.model.vo.*;
import cn.pledge.envconsole.common.enums.Code;
import cn.pledge.envconsole.common.exception.BizException;
import cn.pledge.envconsole.common.model.PageResult;
import cn.pledge.envconsole.common.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 89466
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManagementService {
    @Autowired
    private ConfigurationMapper configurationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExperienceGoldRecordMapper experienceGoldRecordMapper;
    @Autowired
    private FlowRecordMapper flowRecordMapper;
    @Autowired
    private PledgeRecordMapper pledgeRecordMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ConfigExperienceFeeMapper configExperienceFeeMapper;
    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;

    public PledgeGlobalConfigurationVO getGlobalConfiguration() {
        PledgeGlobalConfigurationVO pledgeGlobalConfigurationVO = new PledgeGlobalConfigurationVO();
        Configuration configuration = configurationMapper.selectByPrimaryKey(1);
        if (configuration==null){
            return pledgeGlobalConfigurationVO;
        }
        List<PledgeGlobalConfigurationVO.FlowMining> flowMiningList = JSON.parseArray(configuration.getFlowMiningList(), PledgeGlobalConfigurationVO.FlowMining.class);
        List<PledgeGlobalConfigurationVO.Period> periods = JSON.parseArray(configuration.getPeriodList(), PledgeGlobalConfigurationVO.Period.class);
        BeanUtils.copyProperties(configuration,pledgeGlobalConfigurationVO);
        pledgeGlobalConfigurationVO.setFlowMiningList(flowMiningList);
        pledgeGlobalConfigurationVO.setPeriodList(periods);
        return pledgeGlobalConfigurationVO;
    }

    public void globalConfiguration(PledgeGlobalConfigurationParam param) {

        Configuration configuration = new Configuration();
        BeanUtils.copyProperties(param,configuration);
        if (CollectionUtils.isEmpty(param.getFlowMiningList())){
            throw new BizException(Code.FLOW_IS_NULL);
        }
        if (CollectionUtils.isEmpty(param.getPeriodList())){
            throw new BizException(Code.PLEDGE_IS_NULL);
        }
        String flowMiningJSONString = JSON.toJSONString(param.getFlowMiningList());
        String periodJSONString = JSON.toJSONString(param.getPeriodList());
        configuration.setFlowMiningList(flowMiningJSONString);
        configuration.setPeriodList(periodJSONString);
        configuration.setId(1);
        Configuration selectByPrimaryKey = configurationMapper.selectByPrimaryKey(1);
        if (selectByPrimaryKey==null){
            configurationMapper.insertSelective(configuration);
        }
        configurationMapper.updateByPrimaryKeySelective(configuration);
    }

    public void remark(RemarkParam param) {
        User user = new User();
        user.setId(param.getId());
        user.setRemark(param.getRemark());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void configureExperienceFee(ConfigureExperienceFeeParam param) {

        if (param.getId() != null) {
            ConfigExperienceFee configExperienceFee = new ConfigExperienceFee();
            configExperienceFee.setId(param.getId());
            configExperienceFee.setIsConfigureExperienceFee(true);
            configExperienceFeeMapper.updateByPrimaryKeySelective(configExperienceFee);
        }
        ExperienceGoldRecord experienceGoldRecord = new ExperienceGoldRecord();
        experienceGoldRecord.setAmount(param.getAmount());
        LocalDateTime now = LocalDateTime.now();
        experienceGoldRecord.setCreateTime(now);
        experienceGoldRecord.setConfigTime(now);
        experienceGoldRecord.setUserAddress(param.getUserAddress());
        experienceGoldRecord.setUserId(param.getUserId());
        if (StringUtils.isNotEmpty(param.getCurrencyType())) {
            experienceGoldRecord.setCurrencyType(param.getCurrencyType());
        }
        experienceGoldRecord.setExperienceTime(0);
        experienceGoldRecord.setIncome(Double.valueOf(0));
        experienceGoldRecord.setProfit(param.getProfit());
        experienceGoldRecord.setProfitSwitch(true);
        experienceGoldRecordMapper.insert(experienceGoldRecord);


    }

    public UserDetailBaseInfoVO userDetailBaseInfo(Integer id) {
        UserDetailBaseInfoVO userDetailBaseInfoVO = new UserDetailBaseInfoVO();
        User user = userMapper.selectByPrimaryKey(id);
        if (user==null){
            throw new BizException(Code.USER_NOT_EXIST);
        }
        Integer subordinateNum = userMapper.subordinateNum(user.getId());
        BeanUtils.copyProperties(user,userDetailBaseInfoVO);
        Statistics statistics = statisticsMapper.selectOneByUserId(user.getId());
        userDetailBaseInfoVO.setCreateTime(user.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        userDetailBaseInfoVO.setParentAddress(user.getSuperiorUserAddress());
        userDetailBaseInfoVO.setSubordinateNum(subordinateNum);
        userDetailBaseInfoVO.setExperienceGoldTotal(statistics.getUnreceivedExperienceReward());
        userDetailBaseInfoVO.setExperienceGoldRewardsTotal(statistics.getTotalExperienceReward());
        userDetailBaseInfoVO.setFlowRewardsTotal(statistics.getUnreceivedFlowReward());

        FlowRecord flowRecord = flowRecordMapper.selectFlowRecordByUserId(user.getId());
        userDetailBaseInfoVO.setFlowAmountTotal(new Double(0));
        if (flowRecord!=null){
        userDetailBaseInfoVO.setFlowAmountTotal(flowRecord.getAmount().doubleValue());
        }
        userDetailBaseInfoVO.setPledgeRewardsTotal(statistics.getUnreceivedPledgeReward());
        userDetailBaseInfoVO.setAmountTotal(statistics.getUnwithdrawPledge());
        return userDetailBaseInfoVO;
    }

    public void isFlowReward(Integer userId, Boolean isFlowReward) {
        User user = new User();
        user.setId(userId);
        user.setIsFlowReward(isFlowReward);
        userMapper.updateByPrimaryKeySelective(user);

    }

    public void isAuth(Integer userId, Boolean isAuth) {
        User user = new User();
        user.setId(userId);
        user.setIsWithdrawalAuth(isAuth);
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void isPledgeReward(Integer id, Boolean isPledgeReward) {
        PledgeRecord record = new PledgeRecord();
        record.setId(id);
        record.setIsReward(isPledgeReward);
        pledgeRecordMapper.updateByPrimaryKeySelective(record);
    }

    public void closeExperienceFee(Integer id, Boolean isExperienceFee) {
        ExperienceGoldRecord experienceGoldRecord = new ExperienceGoldRecord();
        experienceGoldRecord.setId(id);
        experienceGoldRecord.setProfitSwitch(isExperienceFee);
        experienceGoldRecordMapper.updateByPrimaryKeySelective(experienceGoldRecord);
    }

    public void withdrawalComplete(WithdrawalCompleteParam param) {
        PledgeRecord record = pledgeRecordMapper.selectByPrimaryKey(param.getId());
        if (PledgeType.WITHDRAWING.toString().equals(record.getStatus())) {

        PledgeRecord pledgeRecord = new PledgeRecord();
        pledgeRecord.setId(param.getId());
        pledgeRecord.setStatus(PledgeType.COMPLETE.toString());
        pledgeRecord.setPledgeHash(param.getPlayHash());
        pledgeRecordMapper.updateByPrimaryKeySelective(pledgeRecord);

        }
    }

    public void delAgencyAdmin(Integer id) {
        UserSession currentUser = UserUtils.getCurrentUser();
        String userRole = currentUser.getUserRole();
        if (RoleType.admin.toString().equals(userRole)) {
            adminMapper.deleteByPrimaryKey(id);
        }else {
            throw new BizException(Code.NOT_ALLOW);
        }

    }

    public void updateAgencyAdmin(UpdateAgencyAdminParam param) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(param,admin);
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addAgencyAdmin(AddAgencyAdminParam param) {
        Admin byUsername =adminMapper.selectByUserName(param.getUsername());
        Admin byUserAddress = adminMapper.selectByUserAddress(param.getUserAddress());
        if ( byUsername!=null){
            throw new BizException(Code.HAS_USERNAME_ADMIN);
        }
        if (byUserAddress!=null)
        {
            throw new BizException(Code.HAS_USER_ADDRESS_ADMIN);

        }
        User user = userMapper.selectUserByUserAddress(param.getUserAddress());

        if (user!=null){
            user.setSuperiorUserAddress("0");
            user.setSuperiorId(0);
            userMapper.updateByPrimaryKeySelective(user);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(param,admin);
        admin.setCreateTime(LocalDateTime.now());
        admin.setRole(RoleType.agency.toString());
        adminMapper.insertSelective(admin);
    }

    public PageResult<AgencyAdminVO> agencyAdminList(AgencyAdminListParam param) {

        List<Admin> adminList = adminMapper.agencyAdminList((param.getPage()-1)*param.getSize(), param.getSize(),param.getUserAddress(),param.getRemark());
        Integer total = adminMapper.agencyAdminTotal(param.getUserAddress(),param.getRemark());
        List<AgencyAdminVO> collect = adminList.stream().map(o -> {
            AgencyAdminVO agencyAdminVO = new AgencyAdminVO();
            BeanUtils.copyProperties(o, agencyAdminVO);
            agencyAdminVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            return agencyAdminVO;
        }).collect(Collectors.toList());
        PageResult<AgencyAdminVO> pageResult = new PageResult<>();
        pageResult.setItems(collect);
        pageResult.setTotal(total);
        return pageResult;
    }

    public PageResult<UserVO> userList(UserListParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();
        String userRole = currentUser.getUserRole();
        Integer userId = null;
        if (RoleType.agency.toString().equals(userRole)) {

            //代理管理查询用户
            User user = userMapper.selectUserByUserAddress(currentUser.getUserAddress());
            userId = user.getId();
        }
        List<Integer> userList = userMapper.userList((param.getPage()-1)*param.getSize(), param.getSize(),userId,param.getRemark(),param.getUserAddress());
        Integer total = userMapper.userListTotal(userId,param.getRemark(),param.getUserAddress());

        List<UserVO> collect = new ArrayList<>();
        for (Integer integer : userList) {
            UserVO userVO = new UserVO();
            UserDetailBaseInfoVO userDetailBaseInfoVO = userDetailBaseInfo(integer);
            BeanUtils.copyProperties(userDetailBaseInfoVO, userVO);
            collect.add(userVO);
        }
        PageResult<UserVO> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setItems(collect);
        return pageResult;
    }

    public PageResult<SubordinateUserVO> subordinateUserList(SubordinateUserListParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();

        if (RoleType.agency.toString().equals(currentUser.getUserRole())||StringUtils.isNotEmpty(param.getUserAddress())) {
            String address = currentUser.getUserAddress();
            if (StringUtils.isNotEmpty(param.getUserAddress())){
                address = param.getUserAddress();
            }
            User user = userMapper.selectUserByUserAddress(address);
            if (user==null){
                throw new BizException(Code.USER_NOT_EXIST);
            }
            List<ConfigExperienceFee> userList = new ArrayList<>();
            configExperienceFeeList(user.getId(),userList,param.getIsConfigureExperienceFee(),param.getRemark());
            if (CollectionUtils.isEmpty(userList)){
                PageResult<SubordinateUserVO> pageResult = new PageResult<>();
                pageResult.setItems(Collections.EMPTY_LIST);
                return pageResult;
            }
            List<ConfigExperienceFee> configExperienceFees = userList;
            userList.sort((o1,o2)->o2.getIsConfigureExperienceFee().compareTo(o1.getIsConfigureExperienceFee()));
            if (userList.size()>=(param.getPage())*param.getSize()){
                configExperienceFees = userList.subList((param.getPage() - 1) * param.getSize(), (param.getPage()) * param.getSize());
            }else if (userList.size()<(param.getPage())*param.getSize() && userList.size()>=(param.getPage()-1)*param.getSize()){
                configExperienceFees = userList.subList((param.getPage() - 1) * param.getSize(), userList.size());
            }
            List<SubordinateUserVO> collect = configExperienceFees.stream().map(o -> {
                SubordinateUserVO subordinateUserVO = new SubordinateUserVO();
                BeanUtils.copyProperties(o, subordinateUserVO);
                subordinateUserVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());

                return subordinateUserVO;
            }).collect(Collectors.toList());
            PageResult<SubordinateUserVO> pageResult = new PageResult<>();
            pageResult.setTotal(userList.size());

            pageResult.setItems(collect);
            return pageResult;
        }else {

            List<ConfigExperienceFee>  configExperienceFees = configExperienceFeeMapper.selectAll((param.getPage()-1)*param.getSize(), param.getSize(),param.getIsConfigureExperienceFee(),param.getRemark());
            Integer total = configExperienceFeeMapper.selectAllTotal(param.getIsConfigureExperienceFee(),param.getRemark());
            List<SubordinateUserVO> collect = configExperienceFees.stream().map(o -> {
                SubordinateUserVO subordinateUserVO = new SubordinateUserVO();
                BeanUtils.copyProperties(o, subordinateUserVO);
                subordinateUserVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                return subordinateUserVO;
            }).collect(Collectors.toList());
            PageResult<SubordinateUserVO> pageResult = new PageResult<>();
            pageResult.setTotal(total);

            pageResult.setItems(collect);
            return pageResult;

        }
    }

    private void configExperienceFeeList(Integer id, List<ConfigExperienceFee> userList, Boolean isConfigureExperienceFee, String remark){

      List<ConfigExperienceFee>  configExperienceFees = configExperienceFeeMapper.selectByCurrentId(id,isConfigureExperienceFee,remark);
      if (!CollectionUtils.isEmpty(configExperienceFees)){
          userList.addAll(configExperienceFees);
      }else {
          return;
      }
      for (ConfigExperienceFee configExperienceFee : configExperienceFees) {
          configExperienceFeeList(configExperienceFee.getSubordinateId(),userList, isConfigureExperienceFee, remark);
        }


    }

    public void withdrawalRecordComplete(WithdrawalRecordCompleteParam param) {
        WithdrawRecord withdrawRecord = withdrawRecordMapper.selectByPrimaryKey(param.getId());
        if (PledgeType.WITHDRAWING.toString().equals(withdrawRecord.getStatus())){
            withdrawRecord.setStatus(PledgeType.COMPLETE.toString());
            withdrawRecord.setPlayHash(param.getPlayHash());
            withdrawRecord.setPlayTime(LocalDateTime.now());
            withdrawRecordMapper.updateByPrimaryKeySelective(withdrawRecord);
            if ( GainInterestType.pledgePrincipal.toString().equals(withdrawRecord.getWithdrewType())){
                PledgeRecord record = pledgeRecordMapper.selectByPrimaryKey(withdrawRecord.getPledgeRecordId());
                if (PledgeType.WITHDRAWING.toString().equals(record.getStatus())) {

                    PledgeRecord pledgeRecord = new PledgeRecord();
                    pledgeRecord.setId(param.getId());
                    pledgeRecord.setStatus(PledgeType.COMPLETE.toString());
                    pledgeRecord.setPledgeHash(param.getPlayHash());
                    pledgeRecordMapper.updateByPrimaryKeySelective(pledgeRecord);

                }
            }
        }
    }

    public PageResult<WithdrawRecordVO> withdrawRecordList(ManagementWithdrawRecordParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();
        String userRole = currentUser.getUserRole();
        List<Integer> userList = null;
        if (RoleType.agency.toString().equals(userRole)) {
            User userManage = userMapper.selectUserByUserAddress(currentUser.getUserAddress());
            userList = userMapper.selectAllByRootId(userManage.getId());
            if (CollectionUtils.isEmpty(userList)){
                PageResult<WithdrawRecordVO> pageResult = new PageResult<>();
                pageResult.setItems(Collections.EMPTY_LIST);
                return pageResult;
            }
        }
        List<WithdrawRecord> withdrawRecordList = withdrawRecordMapper.manageSelectWithdrawRecordList((param.getPage()-1)*param.getSize(), param.getSize(),userList,param.getRemark(),param.getPlayHash(),param.getUserAddress());
        Integer total = withdrawRecordMapper.manageWithdrawRecordTotal(userList,param.getRemark(),param.getPlayHash(),param.getUserAddress());
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

    public PageResult<ExperienceGoldRecordVO> experienceGoldRecordList(ExperienceGoldRecordParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();
        String userRole = currentUser.getUserRole();
        List<Integer> userList = null;
        if (RoleType.agency.toString().equals(userRole)) {
            User userManage = userMapper.selectUserByUserAddress(currentUser.getUserAddress());
            userList = userMapper.selectAllByRootId(userManage.getId());
            if (CollectionUtils.isEmpty(userList)){
                PageResult<ExperienceGoldRecordVO> experienceGoldRecordVOPageResult = new PageResult<>();
                experienceGoldRecordVOPageResult.setItems(Collections.EMPTY_LIST);
                return experienceGoldRecordVOPageResult;
            }
        }
        List<ExperienceGoldRecord> list = experienceGoldRecordMapper.manageExperienceGoldRecord((param.getPage()-1)*param.getSize(), param.getSize(),userList,param.getRemark(),param.getUserAddress());
        Integer total = experienceGoldRecordMapper.manageExperienceGoldRecordTotal(userList,param.getRemark(),param.getUserAddress());
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
}
