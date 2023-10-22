package cn.pledge.envconsole.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 89466
 */
@Data
@ToString
public class ChatDetailVO {
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 聊天内容
     */
    @ApiModelProperty(value="聊天内容")
    private String context;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Long createTime;

    /**
     * 内容类型 0-文字；1-图片
     */
    @ApiModelProperty(value="内容类型 0-文字；1-图片")
    private Integer contextType;

    /**
     * 来源端 0-客户端；1-管理端
     */
    @ApiModelProperty(value="来源端 0-客户端；1-管理端")
    private Integer fromType;

    /**
     * 客户端id
     */
    @ApiModelProperty(value="客户端id")
    private Integer fromId;

    /**
     * 管理端_id
     */
    @ApiModelProperty(value="管理端_id")
    private Integer toId;
    @ApiModelProperty(value="是否是管理员回复 0-否；1-是")
    private Integer isAdmin;
}
