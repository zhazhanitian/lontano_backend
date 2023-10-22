package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class PledgeAmountVO {
    @ApiModelProperty("质押金额")
    private Double amount;
    @ApiModelProperty("日期")
    private Long createTime;

}
