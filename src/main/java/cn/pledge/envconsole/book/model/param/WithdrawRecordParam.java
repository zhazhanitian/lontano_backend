package cn.pledge.envconsole.book.model.param;

import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class WithdrawRecordParam extends PageReq{
    private String userAddress;
    private String remark;
    private String playHash;
}
