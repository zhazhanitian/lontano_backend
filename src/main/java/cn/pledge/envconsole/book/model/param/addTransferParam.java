package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 89466
 */
@Data
@ToString
public class addTransferParam implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer userId;



    /**
     * 币种类型
     */
    @ApiModelProperty(value="币种类型")
    private String currencyType;

    /**
     * 划转金额
     */
    @ApiModelProperty(value="划转金额")
    private Double amount;

    /**
     * 交易哈希
     */
    @ApiModelProperty(value="交易哈希")
    private String hash;


}
