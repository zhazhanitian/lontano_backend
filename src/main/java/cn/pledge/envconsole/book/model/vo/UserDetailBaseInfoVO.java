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
    @ApiModelProperty("总质押金额-未提现")
    private Double amountTotal;
    @ApiModelProperty("总质押收益-未提取")
    private Double pledgeRewardsTotal;
    @ApiModelProperty("总流动金额-未提取")
    private Double flowAmountTotal;
    @ApiModelProperty("总流动收益-未提取")
    private Double flowRewardsTotal;
    @ApiModelProperty("下级代理数")
    private Integer SubordinateNum;
    @ApiModelProperty("在生效的体验金总数")
    private Double experienceGoldTotal;
    @ApiModelProperty("体验金收益总数")
    private Double experienceGoldRewardsTotal;
    @ApiModelProperty("上级账号地址")
    private String parentAddress;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("流动性收益开关[true -- 可以收益；false -- 不计算收益]")
    private Boolean isFlowReward;
    @ApiModelProperty("提现需授权开关[true -- 需要授权；false -- 不需要]")
    private Boolean isWithdrawalAuth;

}
