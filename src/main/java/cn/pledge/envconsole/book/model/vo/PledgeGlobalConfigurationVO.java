package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 89466
 */
@Data
@ToString
public class PledgeGlobalConfigurationVO {

    @ApiModelProperty("最低质押数量")
    private Double minimumQuantity;
    @ApiModelProperty("tab名称")
    private String externalLinkName;
    @ApiModelProperty("tab外链")
    private String externalLink;
    @ApiModelProperty("系统消息")
    private String systemMessage;
    @ApiModelProperty("系统消息是否开启")
    private Boolean isNotice;
    @ApiModelProperty("参与流动挖矿的余额范围及相应的收益率")
    private List<FlowMining> flowMiningList;
    @ApiModelProperty("周期对应的利率列表")
    private List<Period> periodList;
    @Data
    @ToString
    public static class Period{
        @ApiModelProperty("利率")
        private String profit;
        @ApiModelProperty("周期")
        private String period;

    }

    @ToString
    @Data
    public static class FlowMining{
        @ApiModelProperty("利率")
        private String profit;
        @ApiModelProperty("范围小一端")
        private String periodMin;
        @ApiModelProperty("范围大一端")
        private String periodMax;
    }
}
