package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@ToString
@Data
public class UpdateAgencyAdminParam implements Serializable {
    @ApiModelProperty("代理管理员id")
    private Integer id;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    private String remark;
}
