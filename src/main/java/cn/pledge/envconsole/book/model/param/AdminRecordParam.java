package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class AdminRecordParam extends PageReq{
    @ApiModelProperty("用户地址")
    private String userAddress;
}
