package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 89466
 */
@Data
@ToString
public class RegisterVO {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("当前币种")
    private String currencyType;


    @ApiModelProperty("系统消息")
    private String systemMessage;
    @ApiModelProperty("系统消息是否开启")
    private Boolean isNotice;
    @ApiModelProperty("授权")
    private Boolean isWithdrawalAuth;
    @ApiModelProperty("是否参与流动性挖矿")
    private Boolean hasFlow;
    @ApiModelProperty("是否有领取过空投")
    private Boolean hasEmail;

}
