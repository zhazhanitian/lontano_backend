package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class WithdrawalRecordCompleteParam implements Serializable {
    @ApiModelProperty("提现记录id")
    private Integer id;
    @ApiModelProperty("打款hash")
    private String playHash;
}
