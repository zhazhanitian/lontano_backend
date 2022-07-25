package cn.pledge.envconsole.book.model.param;

import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ExperienceGoldRecordParam extends PageReq{
    private String userAddress;
    private String remark;

}
