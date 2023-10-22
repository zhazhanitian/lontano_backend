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
@author jerffry

@create 2023-07-13-15:22  

@description 
*/ 
@ApiModel(value="transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer implements Serializable {
    @ApiModelProperty(value="")
    private Integer id;

    /**
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    private Integer userId;

    /**
    * 管理员id
    */
    @ApiModelProperty(value="管理员id")
    private Integer adminId;

    /**
    * 币种类型
    */
    @ApiModelProperty(value="币种类型")
    private String currencyType;

    /**
    * 划转金额
    */
    @ApiModelProperty(value="划转金额")
    private Double amount;

    /**
    * 交易哈希
    */
    @ApiModelProperty(value="交易哈希")
    private String hash;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
    * 是否自动划转 0-否；1-是
    */
    @ApiModelProperty(value="是否自动划转 0-否；1-是")
    private Integer isAuto;

    private static final long serialVersionUID = 1L;
}