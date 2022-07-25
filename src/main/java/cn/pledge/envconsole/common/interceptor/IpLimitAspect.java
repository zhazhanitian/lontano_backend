package cn.pledge.envconsole.common.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


/**
 * 做接口ip访问限制
 * @author 89466
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class IpLimitAspect {


    public static Set<String> allowIps = new HashSet<>(5);

    /**
     * 一定要有序
     */
    private  LinkedList<String> requestHeader;

    public IpLimitAspect(){
        requestHeader = new LinkedList<String>(){{
            add("x-forwarded-for");
            add("Proxy-Client-IP");
            add("WL-Proxy-Client-IP");
            add("HTTP_CLIENT_IP");
            add("HTTP_X_FORWARDED_FOR");
        }
        };
    }
    @Pointcut("execution(* cn.pledge.envconsole.book.web.ManagementController.*(..)) ")
    public void ipLimitPoint() {
    }

    @Before("ipLimitPoint()")
    public synchronized void ipLimitBefore() {
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
        if (!allowIps.contains(ip)){
//            throw new BizException(ErrorCodeEnum.TEST_API_LIMIT_IP, "该ip地址不允许进入测试接口");
        }
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
}
