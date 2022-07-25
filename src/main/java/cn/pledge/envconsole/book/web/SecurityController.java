package cn.pledge.envconsole.book.web;

import cn.pledge.envconsole.book.model.param.LoginParam;
import cn.pledge.envconsole.book.model.param.RegisterParam;
import cn.pledge.envconsole.book.model.vo.LoginVO;
import cn.pledge.envconsole.book.service.SecurityService;
import cn.pledge.envconsole.common.interceptor.IpLimitAspect;
import cn.pledge.envconsole.common.model.BaseResponse;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * @author 89466
 */
@ApiSupport(order = 10)
@Api(tags = "后台登入控制安全页面")
@RequestMapping("/api/v1/security")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class SecurityController {

    @Autowired
    private SecurityService securityService;
    @ApiOperation(value = "登入前获取ip")
    @GetMapping("/loginPre")
    public BaseResponse loginPre() {
        securityService.loginPre();
        return BaseResponse.success();
    }
    @ApiOperation(value = "网页被关闭，移除ip")
    @GetMapping("/ipRemove")
    public BaseResponse ipRemove() {
        securityService.ipRemove();
        return BaseResponse.success();
    }
    @ApiOperation(value = "登入后检测ip是否还存在 true --存在；false -- 不存在")
    @GetMapping("/loginAfter")
    public BaseResponse<Boolean> loginAfter() {
        Boolean bool = securityService.loginAfter();
        return BaseResponse.success(bool);
    }
    @ApiOperation(value = "登入")
    @PostMapping("/login")
    public BaseResponse<LoginVO> login(@RequestBody LoginParam param, @ApiIgnore HttpSession session) {
        LoginVO loginVO = securityService.login(param,session);
        return BaseResponse.success(loginVO);
    }

    @ApiOperation(value = "登出")
    @GetMapping("/loginOut")
    public BaseResponse loginOut(@ApiIgnore HttpSession session) {
        securityService.loginOut(session);
        return BaseResponse.success();
    }
}
