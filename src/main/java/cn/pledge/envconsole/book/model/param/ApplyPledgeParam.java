package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ApplyPledgeParam {
    @ApiModelProperty("用户地址")
    private String userAddress;
    @ApiModelProperty("合约地址")
    private String contractAddress;
    @ApiModelProperty("签名")
    private String signature;
}
