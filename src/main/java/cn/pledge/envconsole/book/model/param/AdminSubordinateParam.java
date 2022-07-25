package cn.pledge.envconsole.book.model.param;

import cn.pledge.envconsole.book.model.enums.ExperienceGoldType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class AdminSubordinateParam extends PageReq{
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("是否配置体验金")
    private ExperienceGoldType type;
}
