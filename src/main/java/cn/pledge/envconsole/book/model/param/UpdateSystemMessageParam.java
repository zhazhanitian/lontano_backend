package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 89466
 */
@Data
public class UpdateSystemMessageParam implements Serializable {
    @ApiModelProperty("用户id")
    private Integer id;
    private Boolean isNotice;
    private String systemMessage;
}
