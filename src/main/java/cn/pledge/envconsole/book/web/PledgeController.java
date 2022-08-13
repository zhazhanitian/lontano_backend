package cn.pledge.envconsole.book.web;


import cn.pledge.envconsole.book.model.param.*;
import cn.pledge.envconsole.book.model.vo.*;
import cn.pledge.envconsole.book.service.PledgeService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.pledge.envconsole.common.model.*;


/**
 * @author 89466
 */
@Api(tags = "H5页面")
@ApiSupport(order = 20)
@RequestMapping("/api/v1/pledge")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class PledgeController {

    @Autowired
    private PledgeService pledgeService;
    @ApiOperation(value = "申请质押配置")
    @GetMapping("/applyPledge")
    public BaseResponse<ApplyPledgeVO> applyPledge() {
        ApplyPledgeVO applyPledge = pledgeService.applyPledge();
        return BaseResponse.success(applyPledge);
    }
    @ApiOperation(value = "提交质押")
    @PostMapping("/submitPledge")
    public BaseResponse submitPledge(@RequestBody SubmitPledgeParam param) {
        pledgeService.submitPledge(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "提交流动性 ")
    @PostMapping("/submitFlow")
    public BaseResponse submitFlow(@RequestBody SubmitFlowParam param) {
        pledgeService.submitFlow(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "质押记录列表")
    @GetMapping("/pledgeRecordList")
    public BaseResponse<PageResult<PledgeRecordVO>> pledgeRecordList(@ModelAttribute PledgeRecordParam param) {
        PageResult<PledgeRecordVO> page = pledgeService.pledgeRecordList(param);
        return BaseResponse.success(page);
    }
    @ApiOperation(value = "体验金记录列表")
    @GetMapping("/experienceGoldRecordList")
    public BaseResponse<PageResult<ExperienceGoldRecordVO>> experienceGoldRecordList(@ModelAttribute ExperienceGoldRecordParam param) {
        PageResult<ExperienceGoldRecordVO> page = pledgeService.experienceGoldRecordList(param);
        return BaseResponse.success(page);
    }
    @ApiOperation(value = "提现记录列表 - 传入userAddress 查看userAddress 本身的提现记录")
    @GetMapping("/withdrawRecordList")
    public BaseResponse<PageResult<WithdrawRecordVO>> withdrawRecordList(@ModelAttribute WithdrawRecordParam param) {
        PageResult<WithdrawRecordVO> pageResult = pledgeService.withdrawRecordList(param);
        return BaseResponse.success(pageResult);
    }

    @ApiOperation(value = "收取利息 无可提利息 code = 2002")
    @PostMapping("/gain/interest")
    public BaseResponse gainInterest(@RequestBody GainInterestParam param) {
        pledgeService.gainInterest(param);
      return BaseResponse.success();
    }

    @ApiOperation(value = "提现本金申请 质押期还未结束 code = 2003")
    @PostMapping("/withdrawal/principal")
    public BaseResponse withdrawalPrincipal(@RequestBody WithdrawalPrincipalParam param) {
        pledgeService.withdrawalPrincipal(param);
        return BaseResponse.success();
    }
    @ApiOperation(value = "从下级中收获金额")
    @GetMapping("/subordinateRewards")
    public BaseResponse<Double> subordinateRewards() {
        Double amount = pledgeService.subordinateRewards();
        return BaseResponse.success(amount);

    }
    @ApiOperation(value = "下属列表")
    @GetMapping("/subordinateList")
    public BaseResponse<PageResult<SubordinateVO>> subordinateList(@ModelAttribute SubordinateParam param) {
        PageResult<SubordinateVO> page = pledgeService.subordinateList(param);
        return BaseResponse.success(page);
    }

    @ApiOperation(value = "添加空投")
    @GetMapping("/isAir")
    public BaseResponse isAir(String email) {
        pledgeService.isAir(email);
        return BaseResponse.success();
    }

    @ApiOperation(value = "系统消息")
    @GetMapping("/systemMessage")
    public BaseResponse<SystemMessageVO> systemMessage() {
        SystemMessageVO systemMessageVO = pledgeService.systemMessage();
        return BaseResponse.success(systemMessageVO);
    }

}
