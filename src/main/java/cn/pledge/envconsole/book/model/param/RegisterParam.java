package cn.pledge.envconsole.book.model.param;

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

    @ApiModelProperty("注册用户地址")
    @NotBlank
    private String registerUserAddress;

    @ApiModelProperty("上级用户地址")
    private String superiorUserAddress;
}
