package cn.pledge.envconsole.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jerffry
 * @create 2023-07-09-00:31
 * @description
 */
@ApiModel(value = "chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat implements Serializable {
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 聊天内容
     */
    @ApiModelProperty(value = "聊天内容")
    private String context;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 内容类型 0-文字；1-图片
     */
    @ApiModelProperty(value = "内容类型 0-文字；1-图片")
    private Integer contextType;

    /**
     * 来源端 0-客户端；1-管理端
     */
    @ApiModelProperty(value = "来源端 0-客户端；1-管理端")
    private Integer fromType;

    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    private Integer fromId;

    /**
     * 管理端_id
     */
    @ApiModelProperty(value = "管理端_id")
    private Integer toId;

    /**
     * 是否是管理员回复 0-否；1-是
     */
    @ApiModelProperty(value = "是否是管理员回复 0-否；1-是")
    private Integer isAdmin;

    private static final long serialVersionUID = 1L;
}