package cn.pledge.envconsole.book.model.param;


import cn.pledge.envconsole.book.model.enums.GainInterestType;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 89466
 */
@Data
@ToString
public class GainInterestParam implements Serializable {
    private Integer userId;
    private GainInterestType type;

}
