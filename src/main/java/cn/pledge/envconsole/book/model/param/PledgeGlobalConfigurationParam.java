package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author 89466
 */
@Data
@ToString
public class PledgeGlobalConfigurationParam implements Serializable{

    @ApiModelProperty("最低质押数量")
    private Double minimumQuantity;
    @ApiModelProperty("tab名称")
    private String externalLinkName;
    @ApiModelProperty("tab外链")
    private String externalLink;
    @ApiModelProperty("系统消息")
    private String systemMessage;

    /**
     * 空投开关
     */
    @ApiModelProperty(value = "空投开关")
    private Boolean isAirdrop;

    @ApiModelProperty("参与流动挖矿的余额范围及相应的收益率")
    private List<FlowMiningParam> flowMiningList;
    @ApiModelProperty("周期对应的利率列表")
    private List<PeriodParam> periodList;



}
