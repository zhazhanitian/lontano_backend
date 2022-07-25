package cn.pledge.envconsole.book.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "cn-pledge-envconsole-book-entity-ExperienceGoldRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceGoldRecord implements Serializable {
    @ApiModelProperty(value = "")
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
     * 剩余体验时长
     */
    @ApiModelProperty(value = "剩余体验时长")
    private Integer experienceTime;

    /**
     * 体验金
     */
    @ApiModelProperty(value = "体验金")
    private Double amount;

    /**
     * 利率
     */
    @ApiModelProperty(value = "利率")
    private String profit;

    /**
     * 收益
     */
    @ApiModelProperty(value = "收益")
    private Double income;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currencyType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 配置体验金时间
     */
    @ApiModelProperty(value = "配置体验金时间")
    private LocalDateTime configTime;

    /**
     * 体验金收益开关
     */
    @ApiModelProperty(value = "体验金收益开关")
    private Boolean profitSwitch;
    @TableField(exist = false)
    private String remark;
    private static final long serialVersionUID = 1L;
}