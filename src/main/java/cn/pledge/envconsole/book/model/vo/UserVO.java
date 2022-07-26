package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@ToString
@Data
public class UserVO {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("用户备注")
    private String remark;

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
    @ApiModelProperty("创建时间")
    private Long createTime;
    @ApiModelProperty("是否参与流动性")
    private Boolean hasFlow;


}
