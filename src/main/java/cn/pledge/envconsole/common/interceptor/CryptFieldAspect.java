package cn.pledge.envconsole.common.interceptor;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.pledge.envconsole.common.utils.AesUtils;
import cn.pledge.envconsole.common.utils.AseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 安全字段加密解密切面
 *
 * @author: zetting
 * @date:2018/12/27
 */
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Aspect
//@Component
public class CryptFieldAspect {
    private static Logger logger = LoggerFactory.getLogger(CryptFieldAspect.class);


    @Pointcut("@annotation(EncryptMethod)")
    public void encryptPointCut() {
    }

//    @Pointcut("@annotation(DecryptMethod)")
@Pointcut("execution(* cn.pledge.envconsole.book.web.*.*(..)) ")
public void decryptPointCut() {
    }

    /**
     * 加密数据环绕处理
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("encryptPointCut()")
    public Object aroundEncrypt(ProceedingJoinPoint joinPoint) throws Throwable {
        Object requestObj = joinPoint.getArgs();
        handleEncrypt(requestObj); // 加密CryptField注解字段
        Object responseObj = joinPoint.proceed(); // 方法执行
        handleDecrypt(responseObj); // 解密CryptField注解字段

        return responseObj;
    }

    /**
     * 解密数据环绕处理
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("decryptPointCut()")
    public Object aroundDecrypt(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求参数数组
        Object[] args = joinPoint.getArgs();
        Object obj = args[0];
        //获取参数信息
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(PostMapping.class)) {
            return joinPoint.proceed();
        }
        // 转换保存参数对象为数据库对象
        Class<?> clazz = obj.getClass();

        Field[] field = obj.getClass().getDeclaredFields();
        if (field.length==0){
            return joinPoint.proceed();
        }
        JSONObject json = (JSONObject)JSONUtil.parse(obj);
        for (int i = 0; i < field.length; i++) {
            field[i].setAccessible(true);
            String plaintextValue = (String) field[i].get(obj);
            String encryptValue = AesUtils.decryptStr(plaintextValue);
           json.put(field[i].getName(),encryptValue);
        }
        args[0] = JSONUtil.toBean(json, clazz);

        return joinPoint.proceed(args);


    }

    /**
     * 处理加密
     *
     * @param requestObj
     */
    private void handleEncrypt(Object requestObj) throws IllegalAccessException {
        if (Objects.isNull(requestObj)) {
            return;
        }
        Field[] fields = requestObj.getClass().getDeclaredFields();

        for (Field field : fields) {
//            boolean hasSecureField = field.isAnnotationPresent(CryptField.class);
//            if (hasSecureField) {
                field.setAccessible(true);
//                ReflectionUtils.makeAccessible(field);

                String plaintextValue = (String) field.get(requestObj);
//                String encryptValue = AseUtil.encrypt(plaintextValue, secretKey);
//                field.set(requestObj, encryptValue);
//            }
        }
    }

    /**
     * 处理解密
     *
     * @param responseObj
     */
    private Object handleDecrypt(Object responseObj) throws IllegalAccessException {

        if (Objects.isNull(responseObj)) {
            return null;
        }
        Field[] fields = responseObj.getClass().getDeclaredFields();
        for (Field field : fields) {

            boolean hasSecureField = field.isAnnotationPresent(CryptField.class);
            if (hasSecureField) {
            field.setAccessible(true);
            String encryptValue = (String) field.get(responseObj);
            String plaintextValue = AesUtils.decryptStr(encryptValue);
            field.set(responseObj, plaintextValue);
        }

        }
        return responseObj;
    }

    public static void main(String[] args) {
//        String encrypt = AesUtils.encryptHex("0");
//        System.out.println(encrypt);
//        String decrypt = AesUtils.decryptStr("3825946bd4064e43633be571082fc9f4ccbdf8b43e48c5435be1b1e9cb33ce84f65ccb7a0257ecbb5509a297b4b9ffe306d32d821234e31ece91d35adc072b86a2ad89a7ebb029522ac9969cf1b200b0ed444211ac3d06d8a52e1fa93729a1a0bdbc2df11f8aa8bf213b924b430576905fbb6e93e7b27e430a71d4e241797a15e9b15d5d4e8ab9b9ad694f0d5b91ca715a02f002a5512304031db7743b77534d27b05fb51244e0169f280a8786c4933514e2dccbb650a4820047cd3895fe70a6");
//        System.out.println(decrypt);
        String encrypt1 = AesUtils.encryptHex("1");
        String encrypt3 = AesUtils.encryptHex("2");
        System.out.println(encrypt1);
        System.out.println(encrypt3);

        String encrypt2 = AesUtils.encryptHex("0");
        System.out.println(encrypt2);
    }
}