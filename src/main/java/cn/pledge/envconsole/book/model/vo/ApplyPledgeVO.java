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
public class ApplyPledgeVO {

    @ApiModelProperty("最低质押数量")
    private Double minimumQuantity;
    @ApiModelProperty("周期对应的利率列表")
    private List<Period> periodList;
    @Data
    @ToString
    public static class Period{
        @ApiModelProperty("利率")
        private String profit;
        @ApiModelProperty("周期")
        private String period;
    }

}
