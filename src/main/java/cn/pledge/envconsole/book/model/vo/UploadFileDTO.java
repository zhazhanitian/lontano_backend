package cn.pledge.envconsole.book.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "文件上传返回实体类", value = "文件上传返回实体类")
public class UploadFileDTO {


    @ApiModelProperty(name = "文件路径")
    private String key;

    @ApiModelProperty("文件名字")
    private String name;

    @ApiModelProperty("文件公网地址")
    private String url;

    public UploadFileDTO(String key, String name) {
        this.key = key;
        this.name = name;
    }

}
