package cn.pledge.envconsole.book.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class UserSession {
    private Integer userId;
    private String userAddress;
    private String userRole;
    private String username;
    private String currencyType;

}
