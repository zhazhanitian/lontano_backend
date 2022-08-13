package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class SubordinateUserListByUserIdParam extends PageReq{
    @ApiModelProperty(value = "用户id",required = true)
    private Integer userId;
    private String userAddress;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("是否配置体验金 1 -- 已经配置；0 -- 还未配置")
    private Boolean isConfigureExperienceFee;

}
