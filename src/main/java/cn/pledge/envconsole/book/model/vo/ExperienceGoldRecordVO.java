package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ExperienceGoldRecordVO {
    @ApiModelProperty("id")
    private Integer id;
    private String userAddress;
    @ApiModelProperty("体验时间")
    private Integer experienceTime;
    @ApiModelProperty("体验金额")
    private Double amount;
    @ApiModelProperty("利率")
    private String profit;
    @ApiModelProperty("创建时间")
    private Long createTime;
    @ApiModelProperty("配置时间")
    private Long configTime;
    @ApiModelProperty("收益")
    private Double income;
    @ApiModelProperty("币种类型")
    private String currencyType;
    @ApiModelProperty("体验金收益开关")
    private Boolean profitSwitch;

    private String remark;

}
