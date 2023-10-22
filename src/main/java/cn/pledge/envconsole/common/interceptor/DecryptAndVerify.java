package cn.pledge.envconsole.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DecryptAndVerify {
    /** 解密后的参数类型 */
    Class<?> decryptedClass();
}
