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

@ApiModel(value = "cn-pledge-envconsole-book-entity-FlowRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowRecord implements Serializable {
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
     * 账号余额
     */
    @ApiModelProperty(value = "账号余额")
    private Double amount;

    /**
     * 对比次数
     */
    @ApiModelProperty(value = "对比次数")
    private Integer time;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 利率
     */
    @ApiModelProperty(value = "利率")
    private String period;

    /**
     * 币种类型
     */
    @ApiModelProperty(value = "币种类型")
    private String currencyType;

    private static final long serialVersionUID = 1L;
}