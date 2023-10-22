package cn.pledge.envconsole.book.model.vo;

import cn.pledge.envconsole.book.model.param.PageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class AgencyAdminVO {
    @ApiModelProperty("代理管理员id")
    private Integer id;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("注册时间")
    private Long createTime;
    private String remark;
    private Integer parentId;

}
