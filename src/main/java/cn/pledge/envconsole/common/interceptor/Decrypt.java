package cn.pledge.envconsole.common.interceptor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Documented
public @interface Decrypt {
}