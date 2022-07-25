package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class PeriodParam {
    @ApiModelProperty("利率")
    private String profit;
    @ApiModelProperty("周期")
    private String period;

}