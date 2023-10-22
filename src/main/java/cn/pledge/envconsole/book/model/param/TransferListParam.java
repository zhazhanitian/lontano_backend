package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class TransferListParam extends PageReq {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("0-手动/1-自动")
    private Integer isAuto;
    @ApiModelProperty("哈希")
    private String hash;

}
