package cn.pledge.envconsole.book.service;

import cn.hutool.core.util.ObjectUtil;
import cn.pledge.envconsole.book.entity.Admin;
import cn.pledge.envconsole.book.entity.User;
import cn.pledge.envconsole.book.mapper.AdminMapper;
import cn.pledge.envconsole.book.mapper.UserMapper;
import cn.pledge.envconsole.book.model.param.LoginParam;
import cn.pledge.envconsole.book.model.vo.LoginVO;
import cn.pledge.envconsole.common.constants.SessionAttribute;
import cn.pledge.envconsole.common.enums.Code;
import cn.pledge.envconsole.common.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 89466
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService implements InitializingBean {
    private LinkedList<String> requestHeader;
    private ConcurrentHashMap<String, LocalDateTime> ipLimit;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void afterPropertiesSet()  {
        ipLimit = new ConcurrentHashMap<>(12);
        requestHeader = new LinkedList<String>(){{
            add("x-forwarded-for");
            add("Proxy-Client-IP");
            add("WL-Proxy-Client-IP");
            add("HTTP_CLIENT_IP");
            add("HTTP_X_FORWARDED_FOR");
        }};
    }

    public void loginPre() {
        String ip = getIp();
        ipLimit.put(ip,LocalDateTime.now());
    }
    private String getIp(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String ip = null;

        Iterator<String> iterator = requestHeader.iterator();
        while (isRealityIpAdr(ip) && iterator.hasNext()){
            String requestHeaderValue = iterator.next();
            log.info("获取 requestHeader :--------"+requestHeaderValue);
            ip = request.getHeader(requestHeaderValue);
        }
        if (isRealityIpAdr(ip)) {
            ip = request.getRemoteAddr();
            log.info("获取的是 : request.getRemoteAddr--" +ip);
        }
        ip = ipAdrIsArray(ip);
        log.info("last get ip is :--------"+ip);
        return ip;
    }
    /**
     * 是否已经有真实的ip地址
     * @param ip ip地址
     * @return boolean
     */
    private boolean isRealityIpAdr(String ip){
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * 当有多个ip代理式以ip1,ip2,ip3...形式，选择第一个非unknown的IP
     * @param ip 判断ip地址
     * @return String
     */
    private String ipAdrIsArray(String ip){
        if(ip.contains(",")){
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (!"unknown".equalsIgnoreCase(s)) {
                    return s;
                }
            }
        }
        return ip;
    }

    public void ipRemove() {
        String ip = getIp();
        if (ipLimit.containsKey(ip)){
            ipLimit.remove(ip);
        }
    }

    public Boolean loginAfter(HttpSession session) {
        String ip = getIp();
        if (ipLimit.containsKey(ip)){
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime localDateTime = ipLimit.get(ip);
            if (now.minusHours(5).compareTo(localDateTime)<0) {
                return true;
            }else if (now.minusHours(5).compareTo(localDateTime)>=0){
                ipLimit.remove(ip);
                session.invalidate();
                SecurityContextHolder.clearContext();
            }
        }
        return false;
    }

    public LoginVO login(LoginParam param, HttpSession session) {
        LoginVO loginVO = new LoginVO();
        String ip = getIp();
        if (ipLimit.containsKey(ip)){
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime localDateTime = ipLimit.get(ip);
            if (now.minusHours(5).compareTo(localDateTime)<0) {
                Admin admin = adminMapper.selectByUsername(param.getUsername());
                if (admin != null && admin.getPassword().equals(param.getPassword())) {
                    loginVO.setId(admin.getId());
                    loginVO.setUsername(admin.getUsername());
                    loginVO.setRole(admin.getRole());
                    User user = userMapper.selectUserByUserAddressAndCurrencyType(admin.getUserAddress(), "erc20");
                    if (ObjectUtil.isNotEmpty(user)){
                    loginVO.setUserId(user.getId());
                    }
                    session.setAttribute(SessionAttribute.USER_ID, admin.getId());
                    session.setAttribute(SessionAttribute.USER_NAME, admin.getUsername());
                    session.setAttribute(SessionAttribute.USER_ROLE, admin.getRole());
                    session.setAttribute(SessionAttribute.USER_ADDRESS, admin.getUserAddress());
                    session.setMaxInactiveInterval(6 * 60 * 60);
                    return loginVO;
                }
            }
        }
    throw new BizException(Code.PASSWORD_IS_WRONG);
    }


    public void loginOut(HttpSession session) {
        String ip = getIp();
        if (ipLimit.containsKey(ip)){
            ipLimit.remove(ip);
        }
        session.invalidate();
        SecurityContextHolder.clearContext();
    }
}
