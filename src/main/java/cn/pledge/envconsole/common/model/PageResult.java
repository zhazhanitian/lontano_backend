package cn.pledge.envconsole.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 1244
 */
@Data
public class PageResult<T> {

    @ApiModelProperty("总数")
    private long total;

    @ApiModelProperty("当前页条数")
    private long count;

    @ApiModelProperty("列表")
    private List<T> items;

    public long getCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }
}
