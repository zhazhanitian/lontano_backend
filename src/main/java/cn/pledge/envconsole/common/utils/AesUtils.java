package cn.pledge.envconsole.common.utils;
 
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
 
/**
 * AES加密方式算法工具类
 */
public class AesUtils {
	/**
	 * KEY 随机的后续可更改
	 */
	private static final byte[] key ="f5k0f5w7f8g4er88".getBytes(StandardCharsets.UTF_8);

	/**
	 * 初始化加密(默认的AES加密方式)
	 */
	private static final SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
 
 
	/**
	 * 加密
	 * @param str 加密之前的字符串
	 * @return
	 */
	public static String encryptHex(String str){
		return getAes(key).encryptHex(str);
	}
 
	/**
	 * 解密
	 * @param str 加密后的字符串
	 * @return
	 */
	public static String decryptStr(String str){
		return getAes(key).decryptStr(str);
	}
	@NotNull
	private static AES getAes(byte[] key) {

		//构建
		return new AES(Mode.ECB, Padding.PKCS5Padding, key,(byte[])null);
	}

 
}