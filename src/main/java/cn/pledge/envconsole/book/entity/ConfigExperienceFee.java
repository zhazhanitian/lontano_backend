package cn.pledge.envconsole.book.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="cn-pledge-envconsole-book-entity-ConfigExperienceFee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigExperienceFee implements Serializable {
    @ApiModelProperty(value="")
    private Integer id;

    /**
    * 当前用户地址
    */
    @ApiModelProperty(value="当前用户地址")
    private String currentAddress;

    /**
    * 当前用户id
    */
    @ApiModelProperty(value="当前用户id")
    private Integer currentId;

    /**
    * 上级地址
    */
    @ApiModelProperty(value="上级地址")
    private String parentAddress;

    /**
    * 上级id
    */
    @ApiModelProperty(value="上级id")
    private Integer parentId;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
    * 是否配置体验金
    */
    @ApiModelProperty(value="是否配置体验金")
    private Boolean isConfigureExperienceFee;

    /**
    * 下级id
    */
    @ApiModelProperty(value="下级id")
    private Integer subordinateId;

    /**
    * 下级地址
    */
    @ApiModelProperty(value="下级地址")
    private String subordinateAddress;
    @TableField(exist = false)
    private String remark;
    @ApiModelProperty("币类型")
    @TableField(exist = false)
    private String currencyType;
    private static final long serialVersionUID = 1L;
}