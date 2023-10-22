package cn.pledge.envconsole.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * AES对称加密工具类
 */
public class AESUtil {

    // 采用 ECB 模式，算法/模式/填充
    private static final String transformation = "AES/ECB/PKCS5Padding";

    /**
     * 用自定义密码生成 128 位密钥对 str 进行对称加密，加密后的内容采用 Base64 进行 URL 安全的格式编码
     *
     * @param password 加密密码
     * @param str 被加密字符串
     * @return 加密后的字符串，加密出错返回 null
     */
    public static String encode(String password, String str) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(password));
            byte[] bytes = cipher.doFinal(str.getBytes());
            return Base64.encodeBase64URLSafeString(bytes);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 用自定义密码生成 128 位密钥对 str 进行解密
     *
     * @param password 加密密码
     * @param str 被解密字符串
     * @return 解密后的字符串
     */
    public static String decode(String password, String str) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(password));
            return new String(cipher.doFinal(Base64.decodeBase64(str)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据密码生成 128 位密钥
     *
     * @param password 加密密码
     * @return {@link Key}
     */
    public static Key generateKey(String password) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        keyGen.init(128, secureRandom);
        return keyGen.generateKey();
    }
}
