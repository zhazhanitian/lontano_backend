package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class WithdrawalCompleteParam {
    @ApiModelProperty("质押记录id")
    private Integer id;
    @ApiModelProperty("打款hash")
    private String playHash;
}
