package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 89466
 */
@Data
@ToString
public class statisticsVO {
    @ApiModelProperty("总链数")
    private Integer totalChain;
    @ApiModelProperty("今日新加链数")
    private Integer newAddChain;
    @ApiModelProperty("质押总金额")
    private Double amountPledge;
    @ApiModelProperty("链户总余额")
    private Double amountBalance;

    @ApiModelProperty("划转总金额")
    private Double transferBalance;
    @ApiModelProperty("15日链新增情况")
    private List<ChainNumVO> chainNumVOS;
    @ApiModelProperty("质押金额")
    private List<PledgeAmountVO> pledgeAmountVOS;


}
