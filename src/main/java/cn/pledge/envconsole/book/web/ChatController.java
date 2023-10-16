package cn.pledge.envconsole.book.web;

import cn.pledge.envconsole.book.model.param.*;
import cn.pledge.envconsole.book.model.vo.ChatDetailVO;
import cn.pledge.envconsole.book.model.vo.ChatListVO;
import cn.pledge.envconsole.book.model.vo.RegisterVO;
import cn.pledge.envconsole.book.service.ChatService;
import cn.pledge.envconsole.book.service.UserService;
import cn.pledge.envconsole.common.interceptor.DecryptMethod;
import cn.pledge.envconsole.common.model.BaseResponse;
import cn.pledge.envconsole.common.model.PageResult;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

//import static org.tron.walletserver.WalletApi.decodeFromBase58Check;

/**
 * @author 89466
 */
@ApiSupport(order = 10)
@Api(tags = "客服聊天")
@RequestMapping("/api/v1/chat")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @ApiOperation(value = "聊天列表 id--为管理端ID")
    @PostMapping("/list")
    @DecryptMethod
    public BaseResponse<PageResult<ChatListVO>> register(@RequestBody ChatListParam param) {
        PageResult<ChatListVO> list  = chatService.list(param);
        return BaseResponse.success(list);
    }
    @ApiOperation(value = "添加聊天记录 ")
    @PostMapping("/add")
    public BaseResponse addContext(@RequestBody AddChatParam param){
        chatService.add(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "聊天详情")
    @PostMapping("/detail")
    public BaseResponse<PageResult<ChatDetailVO>> detail(@RequestBody ChatDetailParam param){
        PageResult<ChatDetailVO> list  = chatService.detail(param);
        return BaseResponse.success(list);
    }
    @ApiOperation(value = "聊天删除")
    @PostMapping ("/del")
    public BaseResponse del(@RequestBody ChatDelParam chatDelParam){
        chatService.del(chatDelParam);
        return BaseResponse.success();
    }

}
