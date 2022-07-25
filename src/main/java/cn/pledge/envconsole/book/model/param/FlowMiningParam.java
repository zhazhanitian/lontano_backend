package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 89466
 */
@ToString
@Data

public class FlowMiningParam {
    @ApiModelProperty("利率")
    private String profit;
    @ApiModelProperty("范围小一端")
    private String periodMin;
    @ApiModelProperty("范围大一端")
    private String periodMax;
}