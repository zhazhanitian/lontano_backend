package cn.pledge.envconsole.book.service;


import cn.hutool.core.date.DateUtil;

import cn.pledge.envconsole.book.model.vo.UploadFileDTO;
import cn.pledge.envconsole.common.exception.BizException;
import cn.pledge.envconsole.common.utils.UUIDUtils;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;


/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author xxm
 * @since 2022-02-17
 */
@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {

    @Value("${sys.file.uploadPath}")
    private String filePathPrefix;

    @Value("${sys.file.fileUrl}")
    private String fileUrl;

    @SneakyThrows
    @Override
    public UploadFileDTO uploadFileUrl(String url, String folder, String originalFilename) {
        @Cleanup InputStream inputStream = new URL(url).openConnection().getInputStream();
        return uploadFileInputStream(inputStream, folder, originalFilename);
    }
    @SneakyThrows
    @Override
    public UploadFileDTO uploadFile(MultipartFile file)  {
        return uploadFile2Local(file);
//        @Cleanup InputStream inputStream = file.getInputStream();
//        return uploadFileInputStream(inputStream, folder, originalFilename);
    }

    @Override
    public UploadFileDTO uploadFileInputStream(InputStream inputStream, String folder, String originalFilename) {
    // FIXME
    //        String ossKey = ossService.upload(inputStream, folder, originalFilename);
    //        if (!StringUtils.isEmpty(ossKey) && !StringUtils.isEmpty(originalFilename)) {
    //            UploadFileDTO uploadFile = new UploadFileDTO(ossKey, originalFilename);
    //            uploadFile.setUrl(ossService.getUrl(uploadFile.getKey()));
    //            return uploadFile;
    //        }
    throw new BizException( "上传失败");
    }


    public UploadFileDTO uploadFile2Local(MultipartFile file) {
        String folderName = DateUtil.format(new Date(),"yyyyMMdd");
        File folder = new File(filePathPrefix + folderName);
        if(!folder.exists()){
            folder.mkdir();
        }
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String randomName = UUIDUtils.getUUID();
        if(StringUtils.isNotEmpty(extension)){
            randomName += "." + extension;
        }
        File localFile = new File(folder.getPath() + File.separator + randomName);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {

            throw new BizException("上传失败");
        }
        String key = folderName + "/" + randomName;
        UploadFileDTO uploadFileDTO = new UploadFileDTO(key,originalFilename);
        uploadFileDTO.setUrl(fileUrl + key);
        return uploadFileDTO;
    }
}
