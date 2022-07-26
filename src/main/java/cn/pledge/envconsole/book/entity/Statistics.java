package cn.pledge.envconsole.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "cn-pledge-envconsole-book-entity-Statistics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics implements Serializable {
    @ApiModelProperty(value = "")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户钱包地址
     */
    @ApiModelProperty(value = "用户钱包地址")
    private String userAddress;

    /**
     * 流动性挖矿总收益
     */
    @ApiModelProperty(value = "流动性挖矿总收益")
    private Double totalFlowReward;

    /**
     * 未领取的流动性收益
     */
    @ApiModelProperty(value = "未领取的流动性收益")
    private Double unreceivedFlowReward;

    /**
     * 总的体验金收益
     */
    @ApiModelProperty(value = "总的体验金收益")
    private Double totalExperienceReward;

    /**
     * 未领取的体验金收益
     */
    @ApiModelProperty(value = "未领取的体验金收益")
    private Double unreceivedExperienceReward;

    /**
     * 总的质押收益
     */
    @ApiModelProperty(value = "总的质押收益")
    private Double totalPledgeReward;

    /**
     * 未领取的质押收益
     */
    @ApiModelProperty(value = "未领取的质押收益")
    private Double unreceivedPledgeReward;

    /**
     * 未提取的本金
     */
    @ApiModelProperty(value = "未提取的本金")
    private Double unwithdrawPledge;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    private static final long serialVersionUID = 1L;
}