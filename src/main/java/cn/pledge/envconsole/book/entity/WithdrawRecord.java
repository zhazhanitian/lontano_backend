package cn.pledge.envconsole.book.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "cn-pledge-envconsole-book-entity-WithdrawRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRecord implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private Double amount;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applyTime;

    /**
     * 提现状态
     */
    @ApiModelProperty(value = "提现状态")
    private String status;

    /**
     * 打款hash
     */
    @ApiModelProperty(value = "打款hash")
    private String playHash;

    /**
     * 打款时间
     */
    @ApiModelProperty(value = "打款时间")
    private LocalDateTime playTime;

    /**
     * 提现类型
     */
    @ApiModelProperty(value = "提现类型")
    private String withdrewType;

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
     * 质押记录id
     */
    @ApiModelProperty(value = "质押记录id")
    private Integer pledgeRecordId;

    /**
     * 虚拟金额
     */
    @ApiModelProperty(value = "虚拟金额")
    private Double virtualAmount;

    /**
     * 币种类型
     */
    @ApiModelProperty(value = "币种类型")
    private String currencyType;

    private static final long serialVersionUID = 1L;
}