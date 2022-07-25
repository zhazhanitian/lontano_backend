package cn.pledge.envconsole.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author 1244 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
  private int code;
  private String msg;
  private T data;

  public static <T> BaseResponse<T> success(T data) {
    return new BaseResponse<>(200, "success", data);
  }

  public static BaseResponse<?> success() {
    return new BaseResponse<>(200, "success", null);
  }

  public static BaseResponse<?> error(int code, String msg) {
    return new BaseResponse<>(code, msg, null);
  }
}
