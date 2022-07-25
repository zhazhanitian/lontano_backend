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
public class RemarkParam implements Serializable {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("备注")
    private String remark;
}
