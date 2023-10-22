package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ChatDelParam{
    @ApiModelProperty("消息ID")
    private Integer id;
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("是否该用户删除全部消息")
    private Boolean del;

}
