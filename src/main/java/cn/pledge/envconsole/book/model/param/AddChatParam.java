package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class AddChatParam implements Serializable {
    @ApiModelProperty("管理者/代理ID")
    private Integer toId;
    @ApiModelProperty("用户ID")
    private Integer fromId;
    @NotBlank(message = "不能为空字符")
    @NotNull
    @ApiModelProperty("内容")
    private String context;
    @ApiModelProperty("内容类型 0-文字；1-图片")
    private Integer contextType;
    @ApiModelProperty("来源类型 0-客户；1-管理端")
    private Integer fromType;
}
