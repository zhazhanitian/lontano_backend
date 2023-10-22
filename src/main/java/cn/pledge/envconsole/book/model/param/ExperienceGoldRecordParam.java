package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ExperienceGoldRecordParam extends PageReq{

    @ApiModelProperty(value = "用户地址",required = true)
    private Integer userId;


}
