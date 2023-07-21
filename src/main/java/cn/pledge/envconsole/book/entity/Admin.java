package cn.pledge.envconsole.book.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jerffry
 * @create 2023-07-20-23:08
 * @description
 */
@ApiModel(value = "`admin`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 管理角色
     */
    @ApiModelProperty(value = "管理角色")
    private String role;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 用户钱包地址
     */
    @ApiModelProperty(value = "用户钱包地址")
    private String userAddress;

    /**
     * 管理员备注
     */
    @ApiModelProperty(value = "管理员备注")
    private String remark;

    /**
     * 业务员的父id
     */
    @ApiModelProperty(value = "业务员的父id")
    private Integer parentId;

    private static final long serialVersionUID = 1L;
}