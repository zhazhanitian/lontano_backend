package cn.pledge.envconsole.common.interceptor;

import lombok.Data;

@Data
public class EncryptedReq<T> {
    /** 签名 */

    /** 加密请求数据 */

    private String encryptedData;
    /** 原始请求数据（解密后回填到对象） */
    private T data;

}
