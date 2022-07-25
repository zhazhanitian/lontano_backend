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
public class ConfigureExperienceFeeParam implements Serializable {


    @ApiModelProperty("代理下级-用户列表id")
    private Integer id;
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("体验时间")
    private Integer experienceTime;
    @ApiModelProperty("体验金额")
    private Double amount;
    @ApiModelProperty("利率")
    private String profit;
    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currencyType;
}
