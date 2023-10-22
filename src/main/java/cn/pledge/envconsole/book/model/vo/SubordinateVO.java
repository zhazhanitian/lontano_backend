package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class SubordinateVO {
    @ApiModelProperty("下级代理用户地址")
    private String userAddress;
    @ApiModelProperty("下级代理入驻时间")
    private Long CreateTime;
    @ApiModelProperty("币类型")
    private String currencyType;
//    @ApiModelProperty("是否配置体验金")
//    private Boolean isConfigureExperienceFee;
}
