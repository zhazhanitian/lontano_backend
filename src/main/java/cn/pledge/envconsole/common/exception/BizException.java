package cn.pledge.envconsole.common.exception;

import cn.pledge.envconsole.common.enums.Code;
import lombok.Getter;

import java.io.Serializable;

/** @author 1244 */
@Getter
public class BizException extends RuntimeException implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 错误码 {@link Code} */
  private final Code code;

  /** 错误内容 */
  private final String msg;

  public BizException(Code code) {
    this.code = code;
    this.msg = code.getMsg();
  }

  public BizException(String message) {
    this.msg = message;
    this.code = Code.FAIL;
  }
}
