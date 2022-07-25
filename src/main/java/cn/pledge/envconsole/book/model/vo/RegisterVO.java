package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 89466
 */
@Data
@ToString
public class RegisterVO {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("总流动性收益")
    private Double totalFlowReward;
    @ApiModelProperty("流动性收益未领取的")
    private Double unreceivedFlowReward;
    @ApiModelProperty("未提现的本金")
    private Double unwithdrawPledge;
    @ApiModelProperty("未领取的质押收益")
    private Double unreceivedPledgeReward;
    @ApiModelProperty("未领取的体验金收益")
    private Double unreceivedExperienceReward;
    @ApiModelProperty("系统消息")
    private String systemMessage;
    @ApiModelProperty("系统消息是否开启")
    private Boolean isNotice;
    @ApiModelProperty("授权")
    private Boolean isWithdrawalAuth;

}
