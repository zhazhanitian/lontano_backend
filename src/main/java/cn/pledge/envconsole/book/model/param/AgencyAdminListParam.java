package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class AgencyAdminListParam extends PageReq {
    @ApiModelProperty("用户地址")
    private String userAddress;
    private String remark;
    @ApiModelProperty("查看的是代理还是业务员 agency ，employee")
    private String role;
    @ApiModelProperty("管理员id")
    private Integer adminId;

}
