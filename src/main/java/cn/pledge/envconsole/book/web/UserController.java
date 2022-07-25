package cn.pledge.envconsole.book.web;

import cn.pledge.envconsole.book.model.param.RegisterParam;
import cn.pledge.envconsole.book.model.vo.RegisterVO;
import cn.pledge.envconsole.book.service.UserService;
import cn.pledge.envconsole.common.model.BaseResponse;
import cn.pledge.envconsole.common.utils.EthUtils;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author 89466
 */
@ApiSupport(order = 10)
@Api(tags = "H5用户注册页面")
@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    @Autowired
    private  UserService userService;
    @ApiOperation(value = "注册/登入")
    @PostMapping("/register")
    public BaseResponse<RegisterVO> register(@RequestBody RegisterParam param,@ApiIgnore HttpSession session) {
        RegisterVO register = userService.register(param,session);
        return BaseResponse.success(register);
    }

//  public static void main(String[] args) {
//   //   {"address":"0xe81128942ed67a3b453576cad44fa9fb7f0b2098","privateKey":"8ca3edaabc0567d9555ade455bab24a27bea6ee0524e96ffac9a3cfc2b841214"}
//
//       String address = "0xcD41EA464009B9751adCb1Ab81ad014559A57ABB";
//      //rinkeby上面的测试币 erc20-usdt同款
//      String contract="0xdac17f958d2ee523a2206206994597c13d831ec7";
//
//      Web3j web3j = null;
//      {
//          try{
//              //如果这个地址不知道怎么获取 可以参考  https://blog.csdn.net/sail331x/article/details/115395131
//              web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/77c83ab9cfd746918a9b188a7692fa00"));
//          }catch (Throwable t){
//              t.printStackTrace();
//          }
//      }
//      BigInteger bigInteger = EthUtils.balanceOfErc20(web3j, contract, address);
//      System.out.println(bigInteger);
//  }
}
