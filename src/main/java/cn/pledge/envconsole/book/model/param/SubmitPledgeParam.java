package cn.pledge.envconsole.book.model.param;

import cn.pledge.envconsole.book.model.enums.CurrencyType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class SubmitPledgeParam implements Serializable {
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("质押哈希")
    private String pledgeHash;
    @ApiModelProperty("质押金额")
    private Double amount;

    @ApiModelProperty(value = "利率")
    private String profit;
    @ApiModelProperty(value = "质押周期时长")
    private String period;

    @ApiModelProperty("币种类型")
    private CurrencyType currencyType;
}
