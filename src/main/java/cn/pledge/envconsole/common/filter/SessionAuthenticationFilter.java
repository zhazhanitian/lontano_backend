package cn.pledge.envconsole.common.filter;


import cn.pledge.envconsole.book.entity.UserSession;
import cn.pledge.envconsole.common.constants.SessionAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jetbrains.annotations.NotNull;
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
            final HttpSession session = request.getSession(false);
            Integer userId;
            String userAddress;
            String userName;
            String userRole;
            UserSession userSession = new UserSession();

            if (session != null) {
                userId = (Integer) session.getAttribute(SessionAttribute.USER_ID);
                userAddress = (String) session.getAttribute(SessionAttribute.USER_ADDRESS);
                userName = (String) session.getAttribute(SessionAttribute.USER_NAME);
                userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
                userSession.setUserAddress(userAddress);
                userSession.setUserId(userId);
                userSession.setUsername(userName);
                userSession.setUserRole(userRole);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userSession, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
    }
}
