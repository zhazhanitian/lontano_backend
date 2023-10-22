package cn.pledge.envconsole.common.utils;

import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * @author lushe
 * @date 2018/10/26下午9:56
 * @Des:
 **/
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 随机生成一个定长字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        //定义一个字符串（A-Z）即24位；
        String str = "QWERTYUIOPASDFGHJKLZXCVBNM";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(24);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    //生成随机数字和字母,
//    public static String getStringRandom(int length) {
//
//        String val = "";
//        Random random = new Random();
//
//        //参数length，表示生成几位随机数
//        for (int i = 0; i < length; i++) {
//
//            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
//            //输出字母还是数字
//            if ("char".equalsIgnoreCase(charOrNum)) {
//                //输出是大写字母还是小写字母
//                int temp = 97;
//                val += (char) (random.nextInt(26) + temp);
//            } else if ("num".equalsIgnoreCase(charOrNum)) {
//                val += String.valueOf(random.nextInt(10));
//            }
//        }
//        return val;
//    }

    /**
     * 身份证、银行卡掩码生成
     *
     * @param original
     * @param frontLen
     * @param endLen
     * @return
     */
    public static String generateMask(String original, int frontLen, int endLen) {
        if (!StringUtils.isEmpty(original)) {
            int len = original.length() - frontLen - endLen;
            StringBuffer xing = new StringBuffer();
            for (int i = 0; i < len; i++) {
                xing.append("*");
            }
            return original.substring(0, frontLen) + xing.toString() + original.substring(original.length() - endLen);
        }
        return null;
    }

}
