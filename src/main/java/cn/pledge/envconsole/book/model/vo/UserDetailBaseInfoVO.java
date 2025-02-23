package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class UserDetailBaseInfoVO {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("注册时间")
    private Long createTime;
    @ApiModelProperty("总质押值（质押中的质押本金 +  质押到期未提现的质押本金 + 已提现的质押本金）")
    private Double totalPledge;
    @ApiModelProperty("质押未提取（质押中的质押本金 + 质押到期未提现的质押本金）")
    private Double unwithdrawPledge;
    @ApiModelProperty("质押总收益（未领取 + 已经领取）")
    private Double totalPledgeReward;
    @ApiModelProperty("质押收益（未领取）")
    private Double unreceivedPledgeReward;
    @ApiModelProperty("币类型")
    private String currencyType;
    @ApiModelProperty("当前流动值（即账户余额）")
    private Double flowAmount;
    @ApiModelProperty("流动总收益（未领取 + 已经领取）")
    private Double totalFlowReward;
    @ApiModelProperty("流动收益（未领取）")
    private Double unreceivedFlowReward;

    @ApiModelProperty("体验金总值（生效中的）")
    private Double experienceGoldTotal;
    @ApiModelProperty("体验金总收益（未领取 + 已经领取）")
    private Double experienceGoldRewardsTotal;
    /**
     * 未领取的体验金收益
     */
    @ApiModelProperty(value = "体验金收益（未领取）")
    private Double unreceivedExperienceReward;
    @ApiModelProperty("下级代理数")
    private Integer SubordinateNum;
    @ApiModelProperty("上级账号地址")
    private String parentAddress;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("流动性收益开关[true -- 可以收益；false -- 不计算收益]")
    private Boolean isFlowReward;
    @ApiModelProperty("提现需授权开关[true -- 需要授权；false -- 不需要]")
    private Boolean isWithdrawalAuth;
    @ApiModelProperty("是否通知")
    private Boolean isNotice;
    @ApiModelProperty("系统消息")
    private String systemMessage;
    @ApiModelProperty("是否参与流动性")
    private Boolean hasFlow;
    @ApiModelProperty("是否有领取空投")
    private Boolean hasEmail;
    @ApiModelProperty("邮箱")
    private String email;
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

    @ApiModelProperty(value = "根代理在管理端ID")
    private Integer rootAdminId;
    @ApiModelProperty(value = "自动划转 0-否；1-是")
    private Integer automaticTransfer;
    @ApiModelProperty(value = "自动划转金额")
    private Double transferNum;
}
