package cn.pledge.envconsole.book.web;


import cn.pledge.envconsole.book.model.vo.UploadFileDTO;
import cn.pledge.envconsole.book.service.UploadFileService;
import cn.pledge.envconsole.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 * 文件上传 前端控制器
 * </p>
 *
 * @author xxm
 * @since 2022-02-13
 */
@Slf4j
@RestController
@RequestMapping("api/upload")
//@CheckLogin
@CrossOrigin("*")
@Api(value = "文件上传下载接口", tags = "文件上传下载接口")
public class UploadFileController {

    @Autowired
    private UploadFileService uploadFileService;

    @Value("${sys.file.uploadPath}")
    private String filePathPrefix;

//    /**
//     * 上传文件url
//     */
//    @PostMapping(value = "/file/uploadFileUrl")
////    @ApiOperation(value = "上传文件url",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseVO uploadFileUrl(@RequestParam String url,
//                                    @RequestParam(required = false) String folder,
//                                    @RequestParam(required = true) String originalFilename) {
//        UploadFileDTO uploadFileDTO = uploadFileService.uploadFileUrl(url,folder, originalFilename);
//        return ResponseVO.success().putData(uploadFileDTO);
//    }
    /**
     * 上传文件
     */
    @PostMapping(value = "/file/uploadFile")
    @ApiOperation(value = "上传文件",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse uploadFile(MultipartFile file) {
        UploadFileDTO uploadFileDTO = uploadFileService.uploadFile(file);
        return BaseResponse.success(uploadFileDTO);
    }

    @GetMapping(value = "/static/{folder}/{fileName}")
    @ApiOperation(value="文件下载")
    public void downloadFile(@PathVariable String folder, @PathVariable String fileName, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String filePath = filePathPrefix + File.separator + folder + File.separator+ fileName;
            File file = new File(filePath);
            if (!file.exists()) {
                response.setStatus(404);
                log.warn("文件[" + fileName + "]不存在..");
            }
            // 设置强制下载不打开
//            response.setContentType("application/force-download");
//            if (filePath.endsWith("jpg")){
//                response.setContentType("image/jpg");
//            }
//            if (filePath.endsWith("png")){
//                response.setContentType("image/png");
//            }

//            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        }catch (IOException e){
            log.error("文件下载失败" + e.getMessage());
            response.setStatus(404);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }





//    /**
//     * 上传文件InputStream
//     */
//    @PostMapping(value = "/file/uploadFileInputStream")
////    @ApiOperation(value = "上传文件InputStream",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseVO uploadFileInputStream(
//                                 @RequestParam InputStream inputStream,
//                                 @RequestParam(required = false) String folder,
//                                 @RequestParam(required = true) String originalFilename) {
//        UploadFileDTO uploadFileDTO = uploadFileService.uploadFileInputStream(inputStream,folder, originalFilename);
//        return ResponseVO.success().putData(uploadFileDTO);
//    }
}
