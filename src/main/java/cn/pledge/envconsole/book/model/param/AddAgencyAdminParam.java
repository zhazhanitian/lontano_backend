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
public class AddAgencyAdminParam implements Serializable {
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    private String remark;
}
