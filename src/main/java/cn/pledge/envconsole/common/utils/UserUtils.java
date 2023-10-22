package cn.pledge.envconsole.common.utils;


import cn.pledge.envconsole.book.entity.UserSession;
import cn.pledge.envconsole.common.enums.Code;
import cn.pledge.envconsole.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 1244
 */
@Slf4j
public class UserUtils {

    public static UserSession getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserSession) {
                    UserSession userSession =(UserSession) principal;
                    if (StringUtils.isEmpty(userSession.getUserAddress())){
                        throw new BizException(Code.NEED_AUTH);
                    }
                    return userSession;
                }
            }
        }
        throw new BizException(Code.NEED_AUTH);
    }
}
