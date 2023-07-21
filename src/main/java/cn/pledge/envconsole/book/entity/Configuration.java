package cn.pledge.envconsole.book.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jerffry
 * @create 2023-07-20-22:11
 * @description
 */
@ApiModel(value = "configuration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration implements Serializable {
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 最低质押数量
     */
    @ApiModelProperty(value = "最低质押数量")
    private Double minimumQuantity;

    /**
     * tab名称
     */
    @ApiModelProperty(value = "tab名称")
    private String externalLinkName;

    /**
     * tab外链
     */
    @ApiModelProperty(value = "tab外链")
    private String externalLink;

    /**
     * 系统消息
     */
    @ApiModelProperty(value = "系统消息")
    private String systemMessage;

    /**
     * 系统消息是否开启
     */
    @ApiModelProperty(value = "系统消息是否开启")
    private Boolean isNotice;

    /**
     * 参与流动性挖矿的余额范围及相应的收益率
     */
    @ApiModelProperty(value = "参与流动性挖矿的余额范围及相应的收益率")
    private String flowMiningList;

    /**
     * 质押周期对应的利率列表
     */
    @ApiModelProperty(value = "质押周期对应的利率列表")
    private String periodList;

    /**
     * 空投开关
     */
    @ApiModelProperty(value = "空投开关")
    private Boolean isAirdrop;

    /**
     * 币种类型
     */
    @ApiModelProperty(value = "币种类型")
    private String currencyType;

    /**
     * 转化倍数
     */
    @ApiModelProperty(value = "转化倍数")
    private Double rate;

    /**
     * 域名
     */
    @ApiModelProperty(value = "域名")
    private String domain;

    private static final long serialVersionUID = 1L;
}