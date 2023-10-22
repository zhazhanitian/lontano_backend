package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@ToString
@Data
public class WithdrawalPrincipalParam implements Serializable {
    @ApiModelProperty("id 质押记录id")
    private Integer id;

}
