package cn.pledge.envconsole.book.service;



import cn.pledge.envconsole.book.model.vo.UploadFileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 文件上传 服务类
 * </p>
 *
 * @author xxm
 * @since 2022-02-17
 */
public interface UploadFileService {

    UploadFileDTO uploadFileUrl(String url, String folder, String originalFilename);

    UploadFileDTO uploadFile(MultipartFile file);

    UploadFileDTO uploadFileInputStream(InputStream inputStream, String folder, String originalFilename);
}
