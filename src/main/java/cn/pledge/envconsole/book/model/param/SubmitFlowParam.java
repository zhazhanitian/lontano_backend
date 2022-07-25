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
public class SubmitFlowParam implements Serializable {
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("币种类型")
    private CurrencyType currencyType;
}
