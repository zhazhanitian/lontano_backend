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
 * @create 2023-07-12-15:27
 * @description
 */
@ApiModel(value = "flow_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowRecord implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String userAddress;

    /**
     * 账号余额
     */
    @ApiModelProperty(value = "账号余额")
    private Double amount;

    /**
     * 对比次数
     */
    @ApiModelProperty(value = "对比次数")
    private Integer time;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 利率
     */
    @ApiModelProperty(value = "利率")
    private String period;

    /**
     * 币种类型
     */
    @ApiModelProperty(value = "币种类型")
    private String currencyType;

    /**
     * 划转金额
     */
    @ApiModelProperty(value = "划转金额")
    private Double transferNum;

    /**
     * 是否开启自动划转 0-否；1-是
     */
    @ApiModelProperty(value = "是否开启自动划转 0-否；1-是")
    private Integer automaticTransfer;

    private static final long serialVersionUID = 1L;
}