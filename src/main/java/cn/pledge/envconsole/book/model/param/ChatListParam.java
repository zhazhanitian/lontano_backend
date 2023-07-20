package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ChatListParam extends PageReq{
    @ApiModelProperty("管理者/代理ID")
    private Integer id;

}
