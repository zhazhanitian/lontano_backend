package cn.pledge.envconsole.common.exception;


import cn.pledge.envconsole.common.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;


/** @author 1244 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BizException.class)
  public BaseResponse<?> handleBusinessException(BizException e) {
    log.error("BizException: {}", e.getMsg());
    return BaseResponse.error(e.getCode().getCode(), e.getMsg());
  }
  @ExceptionHandler(Exception.class)
  public BaseResponse<?> handleException(Exception e) {
    log.error("Exception: [{}]", e.getMessage());
    return BaseResponse.error(e.hashCode(),e.getMessage());
  }



  @ExceptionHandler(SQLSyntaxErrorException.class)
  public BaseResponse<?> handleSQLSyntaxErrorException(SQLSyntaxErrorException e) {
    log.error("SQLSyntaxErrorException: [{}]", e.getMessage());
    return BaseResponse.error(e.hashCode(),e.getMessage());
  }
}
