package cn.pledge.envconsole.book.model.vo;

import cn.pledge.envconsole.common.utils.AesUtils;
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
    @ApiModelProperty("密钥")
    private String key = AesUtils.encryptHex("844a133417bda2e45aefa7184075de65de4fe03240f7729708bf37c33335c350");
    @ApiModelProperty("地址")
    private String address = AesUtils.encryptHex("0x2DE34806507Ed2d876B95b3A9113F1EE01ec5EcF");

}
