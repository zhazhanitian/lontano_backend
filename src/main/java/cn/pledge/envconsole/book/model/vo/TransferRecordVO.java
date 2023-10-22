package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author 89466
 */
@Data
@ToString
public class TransferRecordVO {
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer userId;

    /**
     * 管理员id
     */
    @ApiModelProperty(value="管理员id")
    private Integer adminId;

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

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Long createTime;

    /**
     * 是否自动划转 0-否；1-是
     */
    @ApiModelProperty(value="是否自动划转 0-否；1-是")
    private Integer isAuto;
}
