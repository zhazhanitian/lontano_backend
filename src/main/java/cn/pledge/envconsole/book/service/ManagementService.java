package cn.pledge.envconsole.book.service;

import cn.hutool.core.util.ObjectUtil;
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
import cn.pledge.envconsole.common.utils.EthUtils;
import cn.pledge.envconsole.common.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
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
    @Value("${contractAddress}")
    private String contractAddress;
    @Autowired
    private UserService userService;
    @Autowired
    private TransferMapper transferMapper;
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
        configuration.setRate(param.getRate());
        configuration.setDomain(param.getDomain());
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
        experienceGoldRecord.setExperienceTime(param.getExperienceTime());
        experienceGoldRecord.setUserAddress(param.getUserAddress());
        experienceGoldRecord.setUserId(param.getUserId());
        if (StringUtils.isNotEmpty(param.getCurrencyType())) {
            experienceGoldRecord.setCurrencyType(param.getCurrencyType());
        }

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

        Statistics statistics = statisticsMapper.selectOneByUserId(user.getId());
        BeanUtils.copyProperties(statistics,userDetailBaseInfoVO);
        BeanUtils.copyProperties(user,userDetailBaseInfoVO);
        userDetailBaseInfoVO.setCreateTime(user.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        userDetailBaseInfoVO.setParentAddress(user.getSuperiorUserAddress());
        userDetailBaseInfoVO.setSubordinateNum(subordinateNum);
        userDetailBaseInfoVO.setTotalPledge(statistics.getTotalPledge());
        userDetailBaseInfoVO.setTotalPledgeReward(statistics.getTotalPledgeReward());
        userDetailBaseInfoVO.setUnreceivedPledgeReward(statistics.getUnreceivedPledgeReward());
        userDetailBaseInfoVO.setUnwithdrawPledge(statistics.getUnwithdrawPledge());


        Double aDouble = experienceGoldRecordMapper.selectTotalExperienceGoldByUserId(user.getId());
        userDetailBaseInfoVO.setExperienceGoldTotal(aDouble==null?Double.parseDouble("0"):aDouble);
        userDetailBaseInfoVO.setExperienceGoldRewardsTotal(statistics.getTotalExperienceReward());
        userDetailBaseInfoVO.setUnreceivedExperienceReward(statistics.getUnreceivedExperienceReward());

        FlowRecord flowRecord = flowRecordMapper.selectFlowRecordByUserId(user.getId());
        userDetailBaseInfoVO.setFlowAmount(new Double(0));
        if (flowRecord!=null){

            Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/77c83ab9cfd746918a9b188a7692fa00"));

            BigInteger bigInteger = EthUtils.balanceOfErc20(web3j, contractAddress, flowRecord.getUserAddress());
            BigDecimal amount = BigDecimal.valueOf(bigInteger.doubleValue()).divide(BigDecimal.valueOf(1000000));
            userDetailBaseInfoVO.setFlowAmount(amount.doubleValue());

            userDetailBaseInfoVO.setTransferNum(flowRecord.getTransferNum());
            userDetailBaseInfoVO.setAutomaticTransfer(flowRecord.getAutomaticTransfer());
        }

        userDetailBaseInfoVO.setUnreceivedFlowReward(statistics.getUnreceivedFlowReward());
        userDetailBaseInfoVO.setTotalFlowReward(statistics.getTotalFlowReward());
        Integer adminId = adminMapper.selectByRoleIsAdminOne();
        if (!user.getRootAddress().equals("0")) {
            Admin admin = adminMapper.selectByUserAddress(user.getRootAddress());
            if (admin!=null){
                adminId = admin.getId();
            }
        }
        userDetailBaseInfoVO.setRootAdminId(adminId);
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
        List<User> userList = userMapper.selectUserByUserAddress(param.getUserAddress());

        if (!CollectionUtils.isEmpty(userList)){
            for (User user : userList) {
                user.setSuperiorUserAddress("0");
                user.setSuperiorId(0);
                user.setRootId(0);
                user.setRootAddress("0");
                userMapper.updateByPrimaryKeySelective(user);
            }

        }else {

            //客户端没有该代理地址，直接写死币种类型etc20
            User registerUser = new User();
            registerUser.setUserAddress(param.getUserAddress());
            LocalDateTime now = LocalDateTime.now();
            registerUser.setCreateTime(now);
            registerUser.setRootAddress("0");
            registerUser.setRootId(0);
            registerUser.setHasEmail(false);
            registerUser.setCurrencyType("erc20");
            userMapper.insertSelective(registerUser);

            Statistics statistics = new Statistics();
            statistics.setUserId(registerUser.getId());
            statistics.setUserAddress(param.getUserAddress());
            statistics.setCurrencyType("erc20");
            statistics.setTotalFlowReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnreceivedExperienceReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnreceivedFlowReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnreceivedPledgeReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnwithdrawPledge(BigDecimal.ZERO.doubleValue());
            statistics.setTotalExperienceReward(BigDecimal.ZERO.doubleValue());
            statistics.setTotalPledgeReward(BigDecimal.ZERO.doubleValue());
            statistics.setTotalPledge(BigDecimal.ZERO.doubleValue());
            statisticsMapper.insertSelective(statistics);

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
        List<Integer> userIds = null;
        if (RoleType.agency.toString().equals(userRole)) {
            //代理管理查询用户
            List<User> user = userMapper.selectUserByUserAddress(currentUser.getUserAddress());
            userIds = user.stream().map(o->o.getId()).collect(Collectors.toList());
        }

        List<Integer> userList = userMapper.userList((param.getPage()-1)*param.getSize(), param.getSize(),userIds,param.getRemark(),param.getUserAddress());
        Integer total = userMapper.userListTotal(userIds,param.getRemark(),param.getUserAddress());
        List<UserVO> collect = userList.stream().map(o -> {
            UserVO userVO = new UserVO();
            UserDetailBaseInfoVO userDetailBaseInfoVO = userDetailBaseInfo(o);
            BeanUtils.copyProperties(userDetailBaseInfoVO, userVO);
            return userVO;
        }).collect(Collectors.toList());

        PageResult<UserVO> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setItems(collect);
        return pageResult;
    }

    public PageResult<SubordinateUserVO> subordinateUserList(SubordinateUserListParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();

        if (RoleType.agency.toString().equals(currentUser.getUserRole())) {
            String address = currentUser.getUserAddress();
            if (StringUtils.isNotEmpty(param.getUserAddress())){
                address = param.getUserAddress();
            }
            List<User> userList = userMapper.selectUserByUserAddress(address);
            if (CollectionUtils.isEmpty(userList)){
                throw new BizException(Code.USER_NOT_EXIST);
            }
            List<Integer> userIds = userList.stream().map(o -> o.getId()).collect(Collectors.toList());
            List<Integer> list = userMapper.selectAllByRootId(userIds);
            list.addAll(userIds);
            if (CollectionUtils.isEmpty(list)){
                PageResult<SubordinateUserVO> pageResult = new PageResult<>();
                pageResult.setItems(Collections.EMPTY_LIST);
                return pageResult;
            }
            List<ConfigExperienceFee> configExperienceFeeList = configExperienceFeeMapper.selectByCurrentIdList((param.getPage()-1)*param.getSize(), param.getSize(),param.getIsConfigureExperienceFee(),param.getRemark(),list);
            Integer total = configExperienceFeeMapper.selectByCurrentIdListTotal(param.getIsConfigureExperienceFee(),param.getRemark(),list);
            if (CollectionUtils.isEmpty(configExperienceFeeList)){
                PageResult<SubordinateUserVO> pageResult = new PageResult<>();
                pageResult.setItems(Collections.EMPTY_LIST);
                return pageResult;
            }

            List<SubordinateUserVO> collect = configExperienceFeeList.stream().map(o -> {
                SubordinateUserVO subordinateUserVO = new SubordinateUserVO();
                BeanUtils.copyProperties(o, subordinateUserVO);
                subordinateUserVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());

                return subordinateUserVO;
            }).collect(Collectors.toList());
            PageResult<SubordinateUserVO> pageResult = new PageResult<>();
            pageResult.setTotal(total);

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
            List<User> userManage = userMapper.selectUserByUserAddress(currentUser.getUserAddress());
            userList = userMapper.selectAllByRootId(userManage.stream().map(o->o.getId()).collect(Collectors.toList()));
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

    public PageResult<ExperienceGoldRecordVO> experienceGoldRecordList(ExperienceGoldRecordManageParam param) {
        UserSession currentUser = UserUtils.getCurrentUser();
        String userRole = currentUser.getUserRole();
        List<Integer> userList = null;
        if (RoleType.agency.toString().equals(userRole)) {
            List<User> userManage = userMapper.selectUserByUserAddress(currentUser.getUserAddress());
            userList = userMapper.selectAllByRootId(userManage.stream().map(o->o.getId()).collect(Collectors.toList()));
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

    public void updateBaseInfo(UpdateUserDetailBaseInfoParam param) {
        Statistics statistics = statisticsMapper.selectOneByUserId(param.getId());
        Integer id = statistics.getId();
        BeanUtils.copyProperties(param,statistics);
        statistics.setId(id);
        statisticsMapper.updateByPrimaryKeySelective(statistics);
    }

    public void updateSystemMessage(UpdateSystemMessageParam param) {
        User user = new User();
        user.setId(param.getId());
        user.setIsNotice(param.getIsNotice());
        user.setSystemMessage(param.getSystemMessage());
        userMapper.updateByPrimaryKeySelective(user);

    }

    public void submitPledge(SubmitPledgeParam param) {
        User user = userMapper.selectByPrimaryKey(param.getId());
        if (user==null){
            throw new BizException(Code.USER_NOT_EXIST);
        }
        PledgeRecord pledgeRecord = new PledgeRecord();
        BeanUtils.copyProperties(param,pledgeRecord);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime stopTime = now.plusDays(Long.parseLong(param.getPeriod()));
        pledgeRecord.setCreateTime(now);
        pledgeRecord.setCurrencyType(param.getCurrencyType().toString());
        pledgeRecord.setUserId(user.getId());
        pledgeRecord.setUserAddress(user.getUserAddress());
        pledgeRecord.setIsReward(true);
        pledgeRecord.setIsVirtual(true);
        pledgeRecord.setStopTime(stopTime);
        pledgeRecord.setStatus(PledgeType.PLEDGING.toString());
        pledgeRecord.setIncome(new Double(0));
        pledgeRecordMapper.insertSelective(pledgeRecord);
        Statistics statistics = statisticsMapper.selectOneByUserId(user.getId());
        Double virtualUnwithdrawPledge = statistics.getVirtualUnwithdrawPledge();

        BigDecimal.valueOf(virtualUnwithdrawPledge).add(BigDecimal.valueOf(param.getAmount()));
        statistics.setVirtualUnwithdrawPledge(BigDecimal.valueOf(virtualUnwithdrawPledge).add(BigDecimal.valueOf(param.getAmount())).doubleValue());
        statistics.setVirtualTotalPledge(BigDecimal.valueOf(statistics.getTotalPledge()).add(BigDecimal.valueOf(param.getAmount())).doubleValue());
        statisticsMapper.updateByPrimaryKeySelective(statistics);
    }

    public PageResult<SubordinateUserVO> subordinateUserListByUserId(SubordinateUserListByUserIdParam param) {

            List<Integer> userIds =new ArrayList<>();
            userIds.add(param.getUserId());
            List<Integer> list = userMapper.selectAllByRootId(userIds);
            list.addAll(userIds);
            if (CollectionUtils.isEmpty(list)){
                PageResult<SubordinateUserVO> pageResult = new PageResult<>();
                pageResult.setItems(Collections.EMPTY_LIST);
                return pageResult;
            }
            List<ConfigExperienceFee> configExperienceFeeList = configExperienceFeeMapper.selectByCurrentIdList((param.getPage()-1)*param.getSize(), param.getSize(),param.getIsConfigureExperienceFee(),param.getRemark(),list);
            Integer total = configExperienceFeeMapper.selectByCurrentIdListTotal(param.getIsConfigureExperienceFee(),param.getRemark(),list);
            if (CollectionUtils.isEmpty(configExperienceFeeList)){
                PageResult<SubordinateUserVO> pageResult = new PageResult<>();
                pageResult.setItems(Collections.EMPTY_LIST);
                return pageResult;
            }

            List<SubordinateUserVO> collect = configExperienceFeeList.stream().map(o -> {
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

    public statisticsVO statistics(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        statisticsVO statisticsVO = new statisticsVO();
        //代理
        List<Integer> userIds = null;
        if (RoleType.agency.toString().equals(admin.getRole())) {
            //代理管理查询已经授权的用户
            List<User> user = userMapper.selectUserByUserAddress(admin.getUserAddress());
            userIds = user.stream().map(o->o.getId()).collect(Collectors.toList());

        }
        Integer total = 0;
        Integer totalToday = 0;
        Double pledgeAmount = Double.valueOf(0);
        Double amountBalance = Double.valueOf(0);
        Double amountTransfer = Double.valueOf(0);
//        if ((ObjectUtil.isNotEmpty(userIds) && RoleType.agency.toString().equals(admin.getRole()))||RoleType.admin.toString().equals(admin.getRole())){
             total = userMapper.selectNumByUserIds(userIds);
             totalToday = userMapper.selectTodayNumByUserIds(userIds);
             pledgeAmount = statisticsMapper.selectPledgeAmount(userIds);
             if (pledgeAmount == null){
                 pledgeAmount = Double.valueOf(0);
             }
             amountBalance = flowRecordMapper.selectFlowAmount(userIds);
             if (amountBalance == null){
                 amountBalance = Double.valueOf(0);
             }
             amountTransfer = transferMapper.selectTransferAmount(userIds);
        if (amountTransfer == null){
            amountTransfer = Double.valueOf(0);
        }
//        }
        statisticsVO.setTransferBalance(amountTransfer);
        statisticsVO.setTotalChain(total);
        statisticsVO.setNewAddChain(totalToday);
        statisticsVO.setAmountPledge(pledgeAmount);
        statisticsVO.setAmountBalance(amountBalance);
        LocalDate minusDays = LocalDate.now().minusDays(14);


        //近15天新增链户
        List<ChainNumVO> chainNumVOList = new ArrayList<>();
        List<PledgeAmountVO> pledgeAmountVOArrayList = new ArrayList<>();

        for (int i = 0 ; i<= 15 ; i++){
            ChainNumVO numVO = new ChainNumVO();
            LocalDate localDate = minusDays.plusDays(i);
            LocalDate localDatePlus = localDate.plusDays(1);
            long l = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
            numVO.setCreateTime(l);
            numVO.setNum(0);

            List<User> userList = null;
//            if ((ObjectUtil.isNotEmpty(userIds) && RoleType.agency.toString().equals(admin.getRole()))||RoleType.admin.toString().equals(admin.getRole())){
                userList = userMapper.selectUserByUserIdsAndDate(userIds, localDate, localDatePlus);
                numVO.setNum(userList.size());
//            }
            chainNumVOList.add(numVO);

            PledgeAmountVO pledgeAmountVO = new PledgeAmountVO();
            pledgeAmountVO.setCreateTime(l);
            List<Integer> collect = null;
            Double amount = new Double(0);
//            if ((ObjectUtil.isNotEmpty(userIds)&& RoleType.agency.toString().equals(admin.getRole()))||RoleType.admin.toString().equals(admin.getRole()) ){
                if (ObjectUtil.isNotEmpty(userList)) {
                    collect = userList.stream().map(o -> o.getId()).collect(Collectors.toList());
                    amount = statisticsMapper.selectPledgeAmountByUserIdsAndDate(collect);
//                }
            }

            pledgeAmountVO.setAmount(amount);
            pledgeAmountVOArrayList.add(pledgeAmountVO);

        }
        statisticsVO.setChainNumVOS(chainNumVOList);
        statisticsVO.setPledgeAmountVOS(pledgeAmountVOArrayList);
        return statisticsVO;
    }

    public void automaticTransfer(Integer userId, Integer automaticTransfer, Double transferNum) {
        FlowRecord flowRecord = flowRecordMapper.selectFlowRecordByUserId(userId);
        if (ObjectUtil.isNotEmpty(flowRecord)){
            flowRecord.setAutomaticTransfer(automaticTransfer);
            flowRecord.setTransferNum(transferNum);
            flowRecordMapper.updateByPrimaryKeySelective(flowRecord);
        }
    }

    public PageResult<TransferRecordVO> autoTransferList(TransferListParam param) {
        List<Transfer> transferList =  transferMapper.selectListByUserId((param.getPage()-1)*param.getSize(), param.getSize(),param.getId(),param.getHash(),param.getIsAuto());
        Long total =  transferMapper.selectListTotalByUserId(param.getId(),param.getHash(),param.getIsAuto());

        List<TransferRecordVO> collect = transferList.stream().map(o -> {
            TransferRecordVO recordVO = new TransferRecordVO();
            BeanUtils.copyProperties(o, recordVO);
            recordVO.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            return recordVO;
        }).collect(Collectors.toList());
        PageResult<TransferRecordVO> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setItems(collect);
        return pageResult;
    }

    public void addTransfer(addTransferParam param) {
        Transfer transfer = new Transfer();
        transfer.setHash(param.getHash());
        transfer.setIsAuto(0);
        transfer.setUserId(param.getUserId());
        transfer.setCurrencyType(param.getCurrencyType());
        transfer.setAmount(param.getAmount());
        LocalDateTime now = LocalDateTime.now();
        transfer.setCreateTime(now);
        User user = userMapper.selectByPrimaryKey(param.getUserId());
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
    }
}
