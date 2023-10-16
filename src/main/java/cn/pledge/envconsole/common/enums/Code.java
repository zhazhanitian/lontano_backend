package cn.pledge.envconsole.common.enums;

import lombok.Getter;

/** @author 1244 */
@Getter
public enum Code {
  /** 失败 */
  FAIL(-1, "请求失败"),
  SUCCESS(200, "成功"),
  NEED_AUTH(401, "请登录"),

  USER_NOT_EXIST(1000, "用户不存在"),
  USER_FORBIDDEN(1001, "用户已被禁用"),
  PASSWORD_IS_WRONG(1002, "账户或者密码错误"),
  FLOW_IS_NULL(1004, "流动性挖矿收益配置不能为空"),
  PLEDGE_IS_NULL(1005, "质押收益配置不能为空"),
  BALANCE_IS_ZERO(1006, "余额为0,不能进行流动性挖矿"),
  INTEREST_RATES_NOT_EXIST(1007, "流动性挖矿收益范围匹配不存在"),
  NO_EXPERIENCE_GOLD_RECORD(1008, "无该体验金记录"),
  MINIMUM_QUANTITY_LIMIT(1009, "质押金额不能小于配置的最小质押金额"),
  PLEDGE_PERIOD_IS_NOT_OVER(2003, "质押期还为结束"),
  HAS_USERNAME_ADMIN(1010, "已经有该用户名代理"),
  DEL_USER_IS_ADMIN(1014, "管理/代理/业务员账号不可删除"),
  HAS_FLOW(1011, "已经参与过流动性挖矿"),
  NOT_IS_AIR(1012, "不能参与空投"),
  HAS_AIR(1013, "已经参加空投了"),
  VIRTUAL_PLEDGE_CANNOT_BE_WITHDRAWN(1014, "虚拟质押不能提现本金"),
  NOT_REWARD(2002, "无利息可以提取"),
  HAS_USER_ADDRESS_ADMIN(2003, "已经有该用户钱包地址代理"),
  NOT_ALLOW(1003, "暂无权限");

  Code(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  private final int code;
  private final String msg;
}
