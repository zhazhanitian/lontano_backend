package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class PledgeRecordVO {
    @ApiModelProperty("质押id")
    private Integer id;
    @ApiModelProperty("质押哈希")
    private String pledgeHash;
    @ApiModelProperty("质押时间")
    private Long pledgeCreateTime;
    @ApiModelProperty("质押金额")
    private Double amount;
    @ApiModelProperty("质押状态 [PLEDGING--质押中；APPLY--申请提现；WITHDRAWING--提现中；COMPLETE--已提现]")
    private String status;
    @ApiModelProperty("利率")
    private String profit;
    @ApiModelProperty("周期")
    private String period;
    @ApiModelProperty("收益")
    private Double income;
    @ApiModelProperty("币种类型")
    private String currencyType;
    @ApiModelProperty("是否可以收益 [true--为可以；false--为不可以]")
    private Boolean isReward;

}
