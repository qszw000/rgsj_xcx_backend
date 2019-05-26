package cn.hsy.echo.controller;

import cn.hsy.echo.pojo.Result;
import cn.hsy.echo.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/xcx")
public class WeChatController {
    @Autowired
    private WeChatService weChatService;

    @PostMapping("/login")
    public Result login(@RequestBody HashMap<String, Object> info) {
        String code = (String) info.get("code");
        return weChatService.login(code);
    }

    @PostMapping("/bind")
    public Result bind(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info) {
        int studentId = (int) info.get("studentID");
        return weChatService.updateStudentOpenId(token, studentId);

    }

    @GetMapping("/getGlobalAnnouncement")
    public Result getGlobalAnnouncement(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listGlobalAnnouncement(token, pageNum, pageSize);
    }

    @GetMapping("/getZoneAnnouncement")
    public Result getZoneAnnouncement(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listZoneAnnouncement(token, pageNum, pageSize);
    }

    @GetMapping("/getMyNotice")
    public Result getMyNotice(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listMyNotice(token, pageNum, pageSize);
    }

    @PostMapping("/submitFormID")
    public Result submitFormId(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info) {
        String formId = (String) info.get("formID");
        return weChatService.insertFormId(token, formId);
    }

    @GetMapping("/getHistoryFee")
    public Result getHistoryFee(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listHistoryFee(token, pageNum, pageSize);
    }

//    @GetMapping("/getFeeDetail")
//    public Map<String, Object> getFeeDetail(@RequestHeader("token") String token, @RequestParam("feeID") int feeId) {
//        return wechatService.getFeeDetail(token, feeId);
//    }

    @PostMapping("/pay")
    public Result pay(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info) {
        int feeId = (int) info.get("feeID");
        return weChatService.updateFeeStatus(token, feeId);
    }

    @GetMapping("/getRepair")
    public Result getRepair(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listRepair(token, pageNum, pageSize);
    }

    @GetMapping("/getRepairReply")
    public Result getRepairReply(@RequestHeader("token") String token, @RequestParam("repairID") int id) {
        return weChatService.getRepairReply(token, id);
    }

    @PostMapping("/replyRepair")
    public Result replyRepair(@RequestHeader("token") String token, @RequestBody Map<String, Object> info) {
        return weChatService.replyRepair(token, info);
    }

    @GetMapping("/getComplaint")
    public Result getComplaint(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listComplaint(token, pageNum, pageSize);
    }

    @PostMapping("/replyComplaint")
    public Result replyComplaint(@RequestHeader("token") String token, @RequestBody Map<String, Object> info) {
        return weChatService.replyComplaint(token, info);
    }

    @GetMapping("/getComplaintReply")
    public Result getComplaintReply(@RequestHeader("token") String token, @RequestParam("complaintID") int id) {
        return weChatService.getComplaintReply(token, id);
    }

    @GetMapping("/getQuestionnaireList")
    public Result getQuestionnaireList(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listQuestionnaire(token, pageNum, pageSize);
    }

    @GetMapping("/getQuestionnaireDetail")
    public Result getQuestionnaireDetail(@RequestHeader("token") String token, @RequestParam("questionID") int id) {
        return weChatService.getQuestionnaireDetail(token, id);
    }

    @PostMapping("/getAnswer")
    public Result getAnswer(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info) {
        List<Integer> optionIdList = (List<Integer>) info.get("optionIDs");
        return weChatService.updateOptionSelectNumber(token, optionIdList);
    }

    @PostMapping("/repairReport")
    public Result repairReport(@RequestHeader String token, @RequestBody HashMap<String, Object> info) {
        return weChatService.insertRepair(token, info);
    }

    @PostMapping("/repairReportWithPicture")
    public Result repairReportWithPicture(@RequestHeader String token, HttpServletRequest request) {
        Map<String, Object> info = getInfo(request);
        return weChatService.insertRepair(token, info);
    }

    @PostMapping("/complaint")
    public Result complaint(@RequestHeader String token, @RequestBody HashMap<String, Object> info) {
        return weChatService.insertComplaint(token, info);
    }

    @PostMapping("/complaintWithPicture")
    public Result complaintWithPicture(@RequestHeader String token, HttpServletRequest request) {
        Map<String, Object> info = getInfo(request);
        return weChatService.insertComplaint(token, info);
    }

    private Map<String, Object> getInfo(HttpServletRequest request) {
        Map<String, Object> info = new HashMap<>();
        System.out.println(request);
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        System.out.println(params);
        MultipartFile file = params.getFile("picture");
        String content = params.getParameter("content");
        String telephone = params.getParameter("telephone");
        info.put("content", content);
        info.put("telephone", telephone);
        info.put("file", file);
        return info;
    }
}
