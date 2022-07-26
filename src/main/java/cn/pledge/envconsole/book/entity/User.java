package cn.pledge.envconsole.book.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "cn-pledge-envconsole-book-entity-User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 用户钱包地址
     */
    @ApiModelProperty(value = "用户钱包地址")
    private String userAddress;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 上级地址
     */
    @ApiModelProperty(value = "上级地址")
    private String superiorUserAddress;

    /**
     * 流动性收益开关
     */
    @ApiModelProperty(value = "流动性收益开关")
    private Boolean isFlowReward;

    /**
     * 提现需授权开关
     */
    @ApiModelProperty(value = "提现需授权开关")
    private Boolean isWithdrawalAuth;

    /**
     * 上级id
     */
    @ApiModelProperty(value = "上级id")
    private Integer superiorId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 根代理id
     */
    @ApiModelProperty(value = "根代理id")
    private Integer rootId;

    /**
     * 根代理地址
     */
    @ApiModelProperty(value = "根代理地址")
    private String rootAddress;

    /**
     * 是否参与流动性挖矿
     */
    @ApiModelProperty(value = "是否参与流动性挖矿")
    private Boolean hasFlow;

    /**
     * 系统消息
     */
    @ApiModelProperty(value = "系统消息")
    private String systemMessage;

    /**
     * 是否通知
     */
    @ApiModelProperty(value = "是否通知")
    private Boolean isNotice;

    private static final long serialVersionUID = 1L;
}