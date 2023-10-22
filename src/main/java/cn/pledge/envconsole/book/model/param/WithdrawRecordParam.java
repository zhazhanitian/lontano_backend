package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class WithdrawRecordParam extends PageReq{
    @ApiModelProperty(value = "用户id",required = true)
    private Integer userId;

    private String playHash;
}
