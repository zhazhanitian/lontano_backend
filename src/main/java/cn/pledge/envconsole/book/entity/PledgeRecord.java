package cn.pledge.envconsole.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "cn-pledge-envconsole-book-entity-PledgeRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PledgeRecord implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String userAddress;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 质押哈希
     */
    @ApiModelProperty(value = "质押哈希")
    private String pledgeHash;

    /**
     * 质押金额
     */
    @ApiModelProperty(value = "质押金额")
    private Double amount;

    /**
     * 币种类型
     */
    @ApiModelProperty(value = "币种类型")
    private String currencyType;

    /**
     * 质押收益开关
     */
    @ApiModelProperty(value = "质押收益开关")
    private Boolean isReward;

    /**
     * 利率
     */
    @ApiModelProperty(value = "利率")
    private String profit;

    /**
     * 质押周期时长
     */
    @ApiModelProperty(value = "质押周期时长")
    private String period;

    /**
     * 质押结束时间
     */
    @ApiModelProperty(value = "质押结束时间")
    private LocalDateTime stopTime;

    /**
     * 质押状态
     */
    @ApiModelProperty(value = "质押状态")
    private String status;

    /**
     * 收益
     */
    @ApiModelProperty(value = "收益")
    private Double income;

    private static final long serialVersionUID = 1L;
}