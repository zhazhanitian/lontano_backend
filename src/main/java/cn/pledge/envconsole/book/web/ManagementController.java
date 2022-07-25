package cn.pledge.envconsole.book.web;

import cn.pledge.envconsole.book.model.param.*;
import cn.pledge.envconsole.book.model.vo.*;
import cn.pledge.envconsole.book.service.ManagementService;
import cn.pledge.envconsole.common.model.BaseResponse;
import cn.pledge.envconsole.common.model.PageResult;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 89466
 */
@Api(tags = "后台页面")
@ApiSupport(order = 10)
@RequestMapping("/api/v1/management")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ManagementController {

    @Autowired
    private ManagementService managementService;
    @ApiOperation(value = "全局配置修改")
    @PostMapping("/globalConfiguration")
    public BaseResponse globalConfiguration(@RequestBody PledgeGlobalConfigurationParam param) {
        managementService.globalConfiguration(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "获取全局配置")
    @PostMapping("/getGlobalConfiguration")
    public BaseResponse<PledgeGlobalConfigurationVO> getGlobalConfiguration() {
        PledgeGlobalConfigurationVO pledgeGlobalConfigurationVO = managementService.getGlobalConfiguration();
        return BaseResponse.success(pledgeGlobalConfigurationVO);
    }
    @ApiOperation(value = "管理员查看代理管理员列表")
    @PostMapping("/agencyAdminList")
    public BaseResponse<PageResult<AgencyAdminVO>> agencyAdminList(@RequestBody AgencyAdminListParam param) {
        PageResult<AgencyAdminVO> pageResult = managementService.agencyAdminList(param);
        return BaseResponse.success(pageResult);
    }
    @ApiOperation(value = "管理员添加代理管理员")
    @PostMapping("/addAgencyAdmin")
    public BaseResponse addAgencyAdmin(@RequestBody AddAgencyAdminParam param) {
        managementService.addAgencyAdmin(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "管理员修改代理管理员")
    @PostMapping("/updateAgencyAdmin")
    public BaseResponse updateAgencyAdmin(@RequestBody UpdateAgencyAdminParam param) {
        managementService.updateAgencyAdmin(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "管理员删除代理管理员")
    @GetMapping("/delAgencyAdmin")
    public BaseResponse delAgencyAdmin(Integer id) {
        managementService.delAgencyAdmin(id);
        return BaseResponse.success();
    }

    @ApiOperation(value = "用户列表")
    @GetMapping("/userList")
    public BaseResponse<PageResult<UserVO>> userList(UserListParam param) {
        PageResult<UserVO> pageResult = managementService.userList(param);
        return BaseResponse.success(pageResult);
  }
    @ApiOperation(value = "代理下级-用户列表")
    @GetMapping("/SubordinateUserList")
    public BaseResponse<PageResult<SubordinateUserVO>> subordinateUserList(SubordinateUserListParam param) {
        PageResult<SubordinateUserVO> pageResult = managementService.subordinateUserList(param);
        return BaseResponse.success(pageResult);

    }
    @ApiOperation(value = "用户备注修改")
    @PostMapping("/remark")
    public BaseResponse remark(@RequestBody RemarkParam param) {
        managementService.remark(param);
        return BaseResponse.success();
    }

    @ApiOperation(value = "提现记录管理列表")
    @GetMapping("/withdrawRecordList")
    public BaseResponse<PageResult<WithdrawRecordVO>> withdrawRecordList(@ModelAttribute ManagementWithdrawRecordParam param) {
        PageResult<WithdrawRecordVO> pageResult = managementService.withdrawRecordList(param);
        return BaseResponse.success(pageResult);
    }
    @ApiOperation(value = "用户详情基本信息")
    @GetMapping("/userDetailBaseInfo")
    public BaseResponse<UserDetailBaseInfoVO> userDetailBaseInfo(Integer id) {
        UserDetailBaseInfoVO userDetailBaseInfoVO = managementService.userDetailBaseInfo(id);
        return BaseResponse.success(userDetailBaseInfoVO);
    }

    @ApiOperation(value = "配置/新增体验金")
    @PostMapping("/configureExperienceFee")
    public BaseResponse configureExperienceFee(@RequestBody ConfigureExperienceFeeParam param) {
        managementService.configureExperienceFee(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "体验金收益开关")
    @GetMapping("/closeExperienceFee")
    public BaseResponse isExperienceFee(Integer id,Boolean isExperienceFee) {
        managementService.closeExperienceFee(id,isExperienceFee);
        return BaseResponse.success();
    }
//    @ApiOperation(value = "变更提现状态为已经打款")
//    @PostMapping("/withdrawal/complete")
//    public BaseResponse withdrawalComplete(@RequestBody WithdrawalCompleteParam param) {
//        managementService.withdrawalComplete(param);
//        return BaseResponse.success();
//    }


    @ApiOperation(value = "体验金管理后台列表")
    @GetMapping("/experienceGoldRecordList")
    public BaseResponse<PageResult<ExperienceGoldRecordVO>> experienceGoldRecordList(@ModelAttribute ExperienceGoldRecordParam param) {
        PageResult<ExperienceGoldRecordVO> page = managementService.experienceGoldRecordList(param);
        return BaseResponse.success(page);
    }
    @ApiOperation(value = "变更提现记录状态为已经打款")
    @PostMapping("/withdrawal/record/complete")
    public BaseResponse withdrawalRecordComplete(@RequestBody WithdrawalRecordCompleteParam param) {
        managementService.withdrawalRecordComplete(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "质押收益开关  id 质押记录id" )
    @GetMapping("/isPledgeReward")
    public BaseResponse isPledgeReward(Integer id,Boolean isPledgeReward) {
        managementService.isPledgeReward(id,isPledgeReward);
        return BaseResponse.success();
    }

    @ApiOperation(value = "流动收益开关")
    @GetMapping("/isFlowReward")
    public BaseResponse isFlowReward(Integer userId,Boolean isFlowReward) {
        managementService.isFlowReward(userId,isFlowReward);
        return BaseResponse.success();
    }
    @ApiOperation(value = "需要授权开关")
    @GetMapping("/isAuth")
    public BaseResponse isAuth(Integer userId,Boolean isAuth) {
        managementService.isAuth(userId,isAuth);
        return BaseResponse.success();
    }


}
