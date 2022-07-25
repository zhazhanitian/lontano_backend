package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class LoginParam implements Serializable {
    @ApiModelProperty("用户账号")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
}
