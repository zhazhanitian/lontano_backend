package cn.pledge.envconsole.common.interceptor;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 注解需要加解密参数的方法，实现自动加解密。
 *
 * @author seawish.zheng
 * @date 2019/8/16
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface DecryptMethod { }