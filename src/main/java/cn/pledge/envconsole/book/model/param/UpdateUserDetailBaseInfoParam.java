package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class UpdateUserDetailBaseInfoParam implements Serializable {
    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("总质押金额-未提现")
    private Double amountTotal;
    @ApiModelProperty("总质押收益-未提取")
    private Double pledgeRewardsTotal;

    @ApiModelProperty("在生效的体验金总数")
    private Double experienceGoldTotal;
    @ApiModelProperty("体验金收益总数")
    private Double experienceGoldRewardsTotal;


}
