package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class SubordinateUserVO {
    @ApiModelProperty
    private Integer id;
    @ApiModelProperty("上级账号地址")
    private String parentAddress;
    @ApiModelProperty("上级账号id")
    private Integer parentId;
    @ApiModelProperty("当前账号地址")
    private String currentAddress;
    @ApiModelProperty("当前账号id")
    private Integer currentId;
    @ApiModelProperty("下级账号地址")
    private String subordinateAddress;
    @ApiModelProperty("下级账号id")
    private Integer subordinateId;
    @ApiModelProperty("当前账号创建时间")
    private Long createTime;
    @ApiModelProperty("是否配置体验金 true -- 已经配置；false -- 还为配置")
    private Boolean isConfigureExperienceFee;
    @ApiModelProperty("备注")
    private String remark;

}
