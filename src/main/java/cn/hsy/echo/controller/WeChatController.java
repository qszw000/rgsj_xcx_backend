package cn.hsy.echo.controller;

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
    private final WeChatService weChatService;

    @Autowired
    public WeChatController(WeChatService weChatService) {
        this.weChatService = weChatService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody HashMap<String, Object> info) {
        String code = (String)info.get("code");
        return weChatService.login(code);
    }

    @PostMapping("/bind")
    public Map<String, Object> bind(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info){
        int studentId = (int)info.get("studentID");
        return weChatService.updateStudentOpenId(token, studentId);

    }

    @GetMapping("/getGlobalAnnouncement")
    public Map<String, Object> getGlobalAnnouncement(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listGlobalAnnouncement(token, pageNum, pageSize);
    }

    @GetMapping("/getZoneAnnouncement")
    public Map<String, Object> getZoneAnnouncement(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listZoneAnnouncement(token, pageNum, pageSize);
    }

    @GetMapping("/getMyNotice")
    public Map<String, Object> getMyNotice(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listMyNotice(token, pageNum, pageSize);
    }

    @PostMapping("/submitFormID")
    public Map<String, Object> submitFormId(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info) {
        String formId = (String) info.get("formID");
        return weChatService.insertFormId(token, formId);
    }

    @GetMapping("/getHistoryFee")
    public Map<String, Object> getHistoryFee(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listHistoryFee(token, pageNum, pageSize);
    }

//    @GetMapping("/getFeeDetail")
//    public Map<String, Object> getFeeDetail(@RequestHeader("token") String token, @RequestParam("feeID") int feeId) {
//        return wechatService.getFeeDetail(token, feeId);
//    }

    @PostMapping("/pay")
    public Map<String, Object> pay(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info) {
        int feeId = (int) info.get("feeID");
        return weChatService.updateFeeStatus(token, feeId);
    }

    @GetMapping("/getRepair")
    public Map<String, Object> getRepair(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listRepair(token, pageNum, pageSize);
    }

    @GetMapping("/getComplaint")
    public Map<String, Object> getComplaint(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listComplaint(token, pageNum, pageSize);
    }

    @GetMapping("/getQuestionnaireList")
    public Map<String, Object> getQuestionnaireList(@RequestHeader("token") String token, @RequestParam("page") int pageNum, @RequestParam("count") int pageSize) {
        return weChatService.listQuestionnaire(token, pageNum, pageSize);
    }

    @GetMapping("/getQuestionnaireDetail")
    public Map<String, Object> getQuestionnaireDetail(@RequestHeader("token") String token, @RequestParam("questionID") int id) {
        return  weChatService.getQuestionnaireDetail(token, id);
    }

    @PostMapping("/getAnswer")
    public Map<String, Object> getAnswer(@RequestHeader("token") String token, @RequestBody HashMap<String, Object> info) {
        List<Integer> optionIdList = (List<Integer>) info.get("optionIDs");
        return weChatService.updateOptionSelectNumber(token, optionIdList);
    }

    @PostMapping("/repairReport")
    public Map<String, Object>  repairReport(@RequestHeader String token, @RequestBody HashMap<String, Object> info) {
        return weChatService.insertRepair(token, info);
    }

    @PostMapping("/repairReportWithPicture")
    public Map<String, Object> repaireReportWithPicture(@RequestHeader String token, HttpServletRequest request) {
        Map<String, Object> info = getInfo(request);
        return weChatService.insertRepair(token, info);
    }

    @PostMapping("/complaint")
    public Map<String, Object>  complaint(@RequestHeader String token, @RequestBody HashMap<String, Object> info) {
        return weChatService.insertComplaint(token, info);
    }

    @PostMapping("/complaintWithPicture")
    public Map<String, Object> complaintWithPicture(@RequestHeader String token, HttpServletRequest request) {
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
