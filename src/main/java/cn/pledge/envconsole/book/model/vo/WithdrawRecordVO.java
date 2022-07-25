package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class WithdrawRecordVO {
    private Integer id;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("提现金额")
    private Double amount;
    @ApiModelProperty("提现类型[flow--流动 pledge--质押 experience--体验金 pledgePrincipal--质押本金]")
    private String withdrewType;
    @ApiModelProperty("申请时间")
    private Long applyTime;
    @ApiModelProperty("提现状态 [WITHDRAWING--提现中；COMPLETE--已提现]")
    private String status;
    @ApiModelProperty("打款哈希")
    private String playHash;
    @ApiModelProperty("打款时间")
    private Long playTime;
    @ApiModelProperty("备注")
    private String remark;
}
