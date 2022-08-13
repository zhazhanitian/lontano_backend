package cn.pledge.envconsole.common.filter;


import cn.pledge.envconsole.book.entity.UserSession;
import cn.pledge.envconsole.common.constants.SessionAttribute;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;

/**
 * @author 1244
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SessionAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final HttpSession session = request.getSession();

            Integer userId;
            String userAddress = null;
            String userName;
            String userRole;
            String currencyType;
            UserSession userSession = new UserSession();

            if (session != null) {
                userId = (Integer) session.getAttribute(SessionAttribute.USER_ID);
                userAddress = (String) session.getAttribute(SessionAttribute.USER_ADDRESS);
                userName = (String) session.getAttribute(SessionAttribute.USER_NAME);
                userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
                currencyType = (String) session.getAttribute(SessionAttribute.CURRENCY_TYPE);
                userSession.setUserAddress(userAddress);
                userSession.setUserId(userId);
                userSession.setUsername(userName);
                userSession.setUserRole(userRole);
                userSession.setCurrencyType(currencyType);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userSession, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


//            String sessionId = session.getId();
//            System.out.println(sessionId);
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//            response.setHeader("Access-Control-Allow-Credentials", "true");//若要返回cookie、携带seesion等信息则将此项设置我true
//            response.setHeader("Access-Control-Allow-Methods", "*");//允许跨域的请求方式
//            response.setHeader("Access-Control-Max-Age", "3600"); //预检请求的间隔时间
//            response.setHeader("Access-Control-Allow-Headers", "*");
//            response.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
//            response.setHeader("Access-Control-Expose-Headers", "*");

//            if (session.getId() != null) {
//                sessionId = session.getId();
//
                Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
                for (String header : headers) { // there can be multiple Set-Cookie attributes
                    header =
                            String.format("%s; %s", header, "SameSite=none; Secure=true;");
//                            String.format("%s; %s", header, "SameSite=Lax;");
                    response.setHeader(HttpHeaders.SET_COOKIE, header);
                }
//            }

//            response.setHeader("P3P","CP=CAO PSA OUR");
//            ResponseCookie cookie =
//            ResponseCookie.from("JSESSIONID", sessionId) // key & value
//                .httpOnly(true) // 禁止js读取
//                .secure(true) // 在http下也传输
//                .path("/") // path
//                .maxAge(Duration.ofHours(1)) // 1个小时候过期
//                .sameSite("none") // 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
//                .build();
//
//            // 设置Cookie Header
//            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
    }
}
