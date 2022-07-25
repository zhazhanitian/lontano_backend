package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 1244
 */
@Data
public class PageReq implements Serializable {
    @ApiModelProperty("页码")
    private int page = 1;
    @ApiModelProperty("每页数量")
    private int size = 10;
}
