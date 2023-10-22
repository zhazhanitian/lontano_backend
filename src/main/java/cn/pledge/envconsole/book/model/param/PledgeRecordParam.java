package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class PledgeRecordParam extends PageReq{
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("质押hash")
    private String pledgeHash;
}
