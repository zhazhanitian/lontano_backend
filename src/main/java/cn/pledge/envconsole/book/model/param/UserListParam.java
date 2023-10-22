package cn.pledge.envconsole.book.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class UserListParam extends PageReq{
    private Integer id;
    private String userAddress;
    private String remark;
    private Boolean hasFlow;


}
