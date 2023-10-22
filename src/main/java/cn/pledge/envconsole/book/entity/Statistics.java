package cn.pledge.envconsole.book.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "cn-pledge-envconsole-book-entity-Statistics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics implements Serializable {
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户钱包地址
     */
    @ApiModelProperty(value = "用户钱包地址")
    private String userAddress;

    /**
     * 总质押
     */
    @ApiModelProperty(value = "总质押")
    private Double totalPledge;

    /**
     * 未提取的本金
     */
    @ApiModelProperty(value = "未提取的本金")
    private Double unwithdrawPledge;

    /**
     * 总的质押收益
     */
    @ApiModelProperty(value = "总的质押收益")
    private Double totalPledgeReward;

    /**
     * 未领取的质押收益
     */
    @ApiModelProperty(value = "未领取的质押收益")
    private Double unreceivedPledgeReward;

    /**
     * 流动性挖矿总收益
     */
    @ApiModelProperty(value = "流动性挖矿总收益")
    private Double totalFlowReward;

    /**
     * 未领取的流动性收益
     */
    @ApiModelProperty(value = "未领取的流动性收益")
    private Double unreceivedFlowReward;

    /**
     * 总的体验金收益
     */
    @ApiModelProperty(value = "总的体验金收益")
    private Double totalExperienceReward;

    /**
     * 未领取的体验金收益
     */
    @ApiModelProperty(value = "未领取的体验金收益")
    private Double unreceivedExperienceReward;

    /**
     * 虚拟总质押
     */
    @ApiModelProperty(value = "虚拟总质押")
    private Double virtualTotalPledge;

    /**
     * 虚拟未提取本金
     */
    @ApiModelProperty(value = "虚拟未提取本金")
    private Double virtualUnwithdrawPledge;

    /**
     * 虚拟总质押收益
     */
    @ApiModelProperty(value = "虚拟总质押收益")
    private Double virtualTotalPledgeReward;

    /**
     * 虚拟未领取的质押收益
     */
    @ApiModelProperty(value = "虚拟未领取的质押收益")
    private Double virtualUnreceivedPledgeReward;

    /**
     * 虚拟流动金额
     */
    @ApiModelProperty(value = "虚拟流动金额")
    private Double virtualFlowAmount;

    /**
     * 虚拟总流动收益
     */
    @ApiModelProperty(value = "虚拟总流动收益")
    private Double virtualTotalFlowReward;

    /**
     * 虚拟未领取流动性收益
     */
    @ApiModelProperty(value = "虚拟未领取流动性收益")
    private Double virtualUnreceivedFlowReward;

    /**
     * 虚拟在生效的体验金
     */
    @ApiModelProperty(value = "虚拟在生效的体验金")
    private Double virtualExperienceAmount;

    /**
     * 虚拟同体验金收益
     */
    @ApiModelProperty(value = "虚拟同体验金收益")
    private Double virtualTotalExperienceReward;

    /**
     * 虚拟未领取体验金收益
     */
    @ApiModelProperty(value = "虚拟未领取体验金收益")
    private Double virtualUnreceivedExperienceReward;

    /**
     * 币种类型
     */
    @ApiModelProperty(value = "币种类型")
    private String currencyType;

    private static final long serialVersionUID = 1L;
}