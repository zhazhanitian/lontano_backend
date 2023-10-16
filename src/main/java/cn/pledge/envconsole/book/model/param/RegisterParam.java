package cn.pledge.envconsole.book.model.param;

import cn.pledge.envconsole.common.interceptor.CryptField;
import cn.pledge.envconsole.common.interceptor.EncryptField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class RegisterParam  implements Serializable{

    @ApiModelProperty(value = "注册用户地址" ,required = true)
    @NotBlank
    @CryptField
    private String registerUserAddress;

    @ApiModelProperty("上级用户地址")
    @CryptField
    private String superiorUserAddress;

    @ApiModelProperty(value = "当前币种类型",required = true)
    @CryptField
    private String currencyType;
}
