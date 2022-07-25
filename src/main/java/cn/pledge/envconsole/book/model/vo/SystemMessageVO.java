package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class SystemMessageVO {
    @ApiModelProperty("消息通知是否开启[true--开启，false -- 关闭]")
    private Boolean isNotice;
    @ApiModelProperty("消息通知内容")
    private String systemMessage;
}
