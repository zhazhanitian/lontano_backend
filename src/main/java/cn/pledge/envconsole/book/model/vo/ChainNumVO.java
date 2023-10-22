package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ChainNumVO {
    @ApiModelProperty("数量")
    private Integer num;
    @ApiModelProperty("日期")
    private Long createTime;

}
