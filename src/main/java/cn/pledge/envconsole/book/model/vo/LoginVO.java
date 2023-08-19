package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class LoginVO {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户名称")
    private String username;
    @ApiModelProperty("管理员角色 [admin -- 系统管理员；agency -- 代理管理员 ；--employee -- 业务员]")
    private String role;
    @ApiModelProperty("管理表对呀用户表id")
    private Integer userId;
}
