package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class UpdateUserDetailBaseInfoParam implements Serializable {
    @ApiModelProperty("用户id")
    private Integer id;
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



}
