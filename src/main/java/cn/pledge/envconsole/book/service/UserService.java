package cn.pledge.envconsole.book.service;

import cn.pledge.envconsole.book.entity.*;

import cn.pledge.envconsole.book.mapper.*;
import cn.pledge.envconsole.book.model.param.RegisterParam;
import cn.pledge.envconsole.book.model.vo.RegisterVO;

import cn.pledge.envconsole.book.model.vo.UserDetailBaseInfoVO;
import cn.pledge.envconsole.common.constants.SessionAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author 89466
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private ConfigurationMapper configurationMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ConfigExperienceFeeMapper configExperienceFeeMapper;
    @Autowired
    private ExperienceGoldRecordMapper experienceGoldRecordMapper;
    @Autowired
    private ManagementService managementService;

    @Autowired
    private  StatisticsMapper statisticsMapper;
    @Transactional(rollbackFor = Exception.class)
    public RegisterVO register(RegisterParam param, HttpSession session) {
        RegisterVO registerVO = new RegisterVO();
//        User user = userMapper.selectUserByUserAddress(param.getRegisterUserAddress());
        User user = userMapper.selectUserByUserAddressAndCurrencyType(param.getRegisterUserAddress(),param.getCurrencyType());
        //新用户
        if (user==null){
            User registerUser = new User();
            registerUser.setUserAddress(param.getRegisterUserAddress());
            LocalDateTime now = LocalDateTime.now();
            registerUser.setCreateTime(now);
            registerUser.setRootAddress("0");
            registerUser.setRootId(0);
            registerUser.setHasEmail(false);
            registerUser.setCurrencyType(param.getCurrencyType());
            User superiorUser = null;
            Admin admin = null;
            //有上级
            if (StringUtils.isNoneEmpty(param.getSuperiorUserAddress())){
                superiorUser = userMapper.selectByPrimaryKey(Integer.parseInt(param.getSuperiorUserAddress()));
                admin = adminMapper.selectByUserAddress(param.getRegisterUserAddress());
                if (superiorUser!=null && admin == null){
                    registerUser.setSuperiorId(superiorUser.getId());
                    registerUser.setSuperiorUserAddress(superiorUser.getUserAddress());
                    if (superiorUser.getRootId().equals(0)){
                        registerUser.setRootAddress(superiorUser.getUserAddress());
                        registerUser.setRootId(superiorUser.getId());
                    }else {
                        registerUser.setRootAddress(superiorUser.getRootAddress());
                        registerUser.setRootId(superiorUser.getRootId());
                    }

                }
            }
            userMapper.insertSelective(registerUser);
            if (superiorUser!=null && admin == null){
                ConfigExperienceFee configExperienceFee = new ConfigExperienceFee();
                configExperienceFee.setCurrentAddress(superiorUser.getUserAddress());
                configExperienceFee.setCurrentId(superiorUser.getId());
                configExperienceFee.setCreateTime(now);
                configExperienceFee.setIsConfigureExperienceFee(false);
                configExperienceFee.setSubordinateAddress(registerUser.getUserAddress());
                configExperienceFee.setSubordinateId(registerUser.getId());
                configExperienceFee.setParentAddress(superiorUser.getSuperiorUserAddress());
                configExperienceFee.setParentId(superiorUser.getSuperiorId());
                configExperienceFeeMapper.insertSelective(configExperienceFee);
            }


            Statistics statistics = new Statistics();
            statistics.setUserId(registerUser.getId());
            statistics.setUserAddress(param.getRegisterUserAddress());
            statistics.setCurrencyType(param.getCurrencyType());
            statistics.setTotalFlowReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnreceivedExperienceReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnreceivedFlowReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnreceivedPledgeReward(BigDecimal.ZERO.doubleValue());
            statistics.setUnwithdrawPledge(BigDecimal.ZERO.doubleValue());
            statistics.setTotalExperienceReward(BigDecimal.ZERO.doubleValue());
            statistics.setTotalPledgeReward(BigDecimal.ZERO.doubleValue());
            statistics.setTotalPledge(BigDecimal.ZERO.doubleValue());
            statisticsMapper.insertSelective(statistics);
            BeanUtils.copyProperties(statistics,registerVO);
            registerVO.setUserId(registerUser.getId());
            registerVO.setUserAddress(param.getRegisterUserAddress());
            registerVO.setHasFlow(false);
            registerVO.setIsNotice(false);
            registerVO.setHasEmail(false);
            registerVO.setCurrencyType(param.getCurrencyType());
//
        }else {
            //老用户
            registerVO.setUserId(user.getId());
            registerVO.setHasFlow(user.getHasFlow());
            registerVO.setIsWithdrawalAuth(user.getIsWithdrawalAuth());
            registerVO.setHasEmail(user.getHasEmail());
            registerVO.setUserAddress(user.getUserAddress());
            registerVO.setIsNotice(user.getIsNotice());
            registerVO.setSystemMessage(user.getSystemMessage());
            registerVO.setCurrencyType(user.getCurrencyType());


        }
        session.setAttribute(SessionAttribute.USER_ID, registerVO.getUserId());
        session.setAttribute(SessionAttribute.USER_ADDRESS, registerVO.getUserAddress());
        session.setAttribute(SessionAttribute.CURRENCY_TYPE, registerVO.getCurrencyType());

        session.setMaxInactiveInterval(5 * 60 * 60);
        return registerVO;
    }

}
