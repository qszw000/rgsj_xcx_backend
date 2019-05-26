package cn.hsy.echo.service;

import cn.hsy.echo.aspect.annotation.VerifyPageParam;
import cn.hsy.echo.dao.UserDao;
import cn.hsy.echo.enums.ErrorEnum;
import cn.hsy.echo.exception.ParameterIllegalException;
import cn.hsy.echo.pojo.*;
import cn.hsy.echo.util.ParamValidUtil;
import cn.hsy.echo.util.ResultUtil;
import cn.hsy.echo.util.TokenUtil;
import cn.hsy.echo.util.WeChatUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class WeChatService {

    @Autowired
    private UserDao userDao;

    // 微信小程序登入，输入code获取用户唯一的open_id，通过open_id查找学生编号，如果返回为null，则需要绑定学号
    // 如果返回不为null，则返回学生信息
    public Result login(String code) {
        String openId = WeChatUtil.getOpenId(code);
//        String openId = "oBPN75W0V9Ican6r4ebB_4VTr3GI";
        Integer id = userDao.getSId(openId);
        String token = TokenUtil.createToken(openId);
        Map<String, Object> data = new HashMap<>();
        if (id == null) {
            data.put("token", token);
            data.put("hasExist", false);
            data.put("userInformation", null);
        } else {
            Student student = userDao.getStudent(openId);
            data.put("token", token);
            data.put("hasExist", true);
            data.put("userInformation", student);
        }
        return ResultUtil.success(data);
    }

    // 绑定学号，如果学号不存在或已被绑定，这返回错误信息，否则修改学生open_id
    public Result updateStudentOpenId(String token, int studentId) {
        if (!ParamValidUtil.isSutdentId("" + studentId)) {
            throw new ParameterIllegalException(ErrorEnum.STUDENT_ID_ILLEGAL);
        }
        Integer flag = userDao.hasExistStudentId(studentId);
        if (flag == null) {
            throw new ParameterIllegalException(ErrorEnum.STUDENT_ID_NOT_EXIT);
        }
        String openId = userDao.getOpenId(studentId);
        if (openId != null) {
            throw new ParameterIllegalException(ErrorEnum.STUDENT_IS_BANNED);
        }
        openId = TokenUtil.getOpenId(token);
        Integer sId = userDao.getSId(openId);
        if (sId != null) {
            throw new ParameterIllegalException(ErrorEnum.OPENID_IS_BANNED);
        }
        userDao.updateOpenId(openId, studentId);
        Student student = userDao.getStudent(openId);
        return ResultUtil.success(student);
    }

    // 获取首页信息
    @VerifyPageParam
    public Result listGlobalAnnouncement(String token, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Information> noticeList = userDao.listGlobalAnnouncement();
        PageInfo<Information> pageInfo = new PageInfo<>(noticeList);
        Map<String, Object> data = new HashMap<>();
        data.put("page", pageInfo);
        return ResultUtil.success(data);
    }

    // 获取小区公告信息
    @VerifyPageParam
    public Result listZoneAnnouncement(String token, int pageNum, int pageSize) {
        String openId = TokenUtil.getOpenId(token);
        Integer id = userDao.getSId(openId);
        Dormitory dormitory = userDao.getDormitory(id);
        PageHelper.startPage(pageNum, pageSize);
        List<Information> noticeList = userDao.listZoneAnnouncement(dormitory.getZone());
        PageInfo<Information> pageInfo = new PageInfo<>(noticeList);
        Map<String, Object> data = new HashMap<>();
        data.put("page", pageInfo);
        return ResultUtil.success(data);
    }

    // 获取我的信息
    @VerifyPageParam
    public Result listMyNotice(String token, int pageNum, int pageSize) {
        String openId = TokenUtil.getOpenId(token);
        Integer id = userDao.getSId(openId);
        Dormitory dormitory = userDao.getDormitory(id);
        PageHelper.startPage(pageNum, pageSize);
        List<Information> noticeList = userDao.listMyNotice(dormitory.getZone(), dormitory.getBuilding(), dormitory.getRoom());
        PageInfo<Information> pageInfo = new PageInfo<>(noticeList);
        Map<String, Object> data = new HashMap<>();
        data.put("page", pageInfo);
        return ResultUtil.success(data);
    }

    // 添加formId
    public Result insertFormId(String token, String formId) {
        String openId = TokenUtil.getOpenId(token);
        int id = userDao.getSId(openId);
        userDao.insertFormId(id, formId);
        return ResultUtil.success();
    }

    // 获取水电历史记录
    @VerifyPageParam
    public Result listHistoryFee(String token, int pageNum, int pageSize) {
        String openId = TokenUtil.getOpenId(token);
        Integer sId = userDao.getSId(openId);
        Integer dId = userDao.getDid(sId);
        PageHelper.startPage(pageNum, pageSize);
        List<Fee> feeList = userDao.listHistoryFee(dId);
        PageInfo<Fee> pageInfo = new PageInfo<>(feeList);
        Map<String, Object> data = new HashMap<>();
        data.put("page", pageInfo);
        return ResultUtil.success(data);
    }

    // 修改缴费记录
    public Result updateFeeStatus(String token, int feeId) {
        userDao.updateFeeStatus(feeId);
        return ResultUtil.success();
    }

    // 获取维修列表
    @VerifyPageParam
    public Result listRepair(String token, int pageNum, int pageSize) {
        String openId = TokenUtil.getOpenId(token);
        Integer id = userDao.getSId(openId);
        Integer dId = userDao.getDid(id);
        PageHelper.startPage(pageNum, pageSize);
        List<Repair> repairList = userDao.listRepair(dId);
        PageInfo<Repair> pageInfo = new PageInfo<>(repairList);
        Map<String, Object> data = new HashMap<>();
        data.put("page", pageInfo);
        return ResultUtil.success(data);
    }

    // 获取维修回复
    public Result getRepairReply(String token, int id) {
        String openId = TokenUtil.getOpenId(token);
        Integer sId = userDao.getSId(openId);
        Integer dId = userDao.getDid(sId);
        Repair repair = userDao.getRepair(id);
        if (repair.getDId() != dId) {
            throw new ParameterIllegalException(ErrorEnum.REPAIR_ID_ILLEGAL);
        }
        List<Reply> applicantReply = userDao.listRepairApplicantReply(id);
        List<Reply> adminReply = userDao.listRepairAdminReply(id);
        List<Reply> replyList = new ArrayList<>();
        replyList.addAll(applicantReply);
        replyList.addAll(adminReply);
        replyList.sort((arg0, arg1) -> arg1.getReplyTime().compareTo(arg0.getReplyTime()));
        repair.setReplyList(replyList);
        return ResultUtil.success(repair);
    }

    // 维修回复
    @Transactional
    public Result replyRepair(String token, Map<String, Object> info) {
        int id = (Integer) info.get("id");
        String content = (String) info.get("content");
        String openId = TokenUtil.getOpenId(token);
        Integer sId = userDao.getSId(openId);
        Integer dId = userDao.getDid(sId);
        Repair repair = userDao.getRepair(id);
        if (repair.getDId() != dId) {
            throw new ParameterIllegalException(ErrorEnum.REPAIR_ID_ILLEGAL);
        }
        userDao.updateRepairTime(id);
        userDao.insertRepairReply(sId, content, id);
        return ResultUtil.success();
    }

    // 获取投诉列表
    @VerifyPageParam
    public Result listComplaint(String token, int pageNum, int pageSize) {
        String openId = TokenUtil.getOpenId(token);
        Integer id = userDao.getSId(openId);
        PageHelper.startPage(pageNum, pageSize);
        List<Complaint> complaintList = userDao.listComplaint(id);
        PageInfo<Complaint> pageInfo = new PageInfo<>(complaintList);
        Map<String, Object> data = new HashMap<>();
        data.put("page", pageInfo);
        return ResultUtil.success(data);
    }

    // 获取投诉回复
    public Result getComplaintReply(String token, int id) {
        String openId = TokenUtil.getOpenId(token);
        Integer sId = userDao.getSId(openId);
        Complaint complaint = userDao.getComplaint(id);
        if (complaint.getSId() != sId) {
            throw new ParameterIllegalException(ErrorEnum.COMPLAINT_ID_ILLEGAL);
        }
        List<Reply> complainantReply = userDao.listComplaintComplainantReply(id);
        List<Reply> adminReply = userDao.listComplaintAdminReply(id);
        List<Reply> replyList = new ArrayList<>();
        replyList.addAll(complainantReply);
        replyList.addAll(adminReply);
        replyList.sort((arg0, arg1) -> arg1.getReplyTime().compareTo(arg0.getReplyTime()));
        complaint.setReplyList(replyList);
        return ResultUtil.success(complaint);
    }

    // 维修回复
    @Transactional
    public Result replyComplaint(String token, Map<String, Object> info) {
        int id = (Integer) info.get("id");
        String content = (String) info.get("content");
        String openId = TokenUtil.getOpenId(token);
        Integer sId = userDao.getSId(openId);
        Complaint complaint = userDao.getComplaint(id);
        if (complaint.getSId() != sId) {
            throw new ParameterIllegalException(ErrorEnum.COMPLAINT_ID_ILLEGAL);
        }
        userDao.updateComplaintTime(id);
        userDao.insertComplaintReply(sId, content, id);
        return ResultUtil.success();
    }

    // 获取问卷调查列表
    @VerifyPageParam
    public Result listQuestionnaire(String token, int pageNum, int pageSize) {
        String openId = TokenUtil.getOpenId(token);
        Integer id = userDao.getSId(openId);
        Dormitory dormitory = userDao.getDormitory(id);
        PageHelper.startPage(pageNum, pageSize);
        List<Questionnaire> questionnaireList = userDao.listQuestionnaire(dormitory.getZone());
        PageInfo<Questionnaire> pageInfo = new PageInfo<>(questionnaireList);
        Map<String, Object> data = new HashMap<>();
        data.put("page", pageInfo);
        return ResultUtil.success(data);
    }

    // 获取问卷详细信息
    public Result getQuestionnaireDetail(String token, int id) {
        String openId = TokenUtil.getOpenId(token);
        Student student = userDao.getStudent(openId);
        Questionnaire questionnaire = userDao.getQuestionnaire(id);
        if (questionnaire.getZone() != 0 && student.getDormitory().getZone() != questionnaire.getZone()) {
            throw new ParameterIllegalException(ErrorEnum.QUESTIONNAIRE_ID_ILLEGAL);
        }
        return ResultUtil.success(questionnaire);
    }

    // 批量增加被选中选项的选中次数+1
    public Result updateOptionSelectNumber(String token, List<Integer> list) {
        for (int id : list) {
            userDao.updateOptionSelectNumber(id);
        }
        return ResultUtil.success();
    }

    // 上传报修照片及添加报修记录
    public Result insertRepair(String token, Map<String, Object> info) {
        String openId = TokenUtil.getOpenId(token);
        Student student = userDao.getStudent(openId);
        Integer sId = student.getId();
        Integer dId = userDao.getDid(sId);
        String telephone = (String) info.get("telephone");
        if ("".equals(telephone)) {
            telephone = student.getTelephone();
        } else {
            if (!ParamValidUtil.isTelephone(telephone)) {
                telephone = student.getTelephone();
            }
        }
        String content = (String) info.get("content");
        String picture = null;
        MultipartFile file = (MultipartFile) info.get("file");
        if (file != null && !file.isEmpty()) {
            try {
                file.transferTo(new File("/www/wwwroot/api.echo.ituoniao.net/images/" + file.getOriginalFilename()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            picture = "/www/wwwroot/api.echo.ituoniao.net/images/" + file.getOriginalFilename();
        }
        userDao.insertRepair(dId, sId, telephone, content, picture);
        return ResultUtil.success();
    }

    // 上传投诉照片及添加投诉记录
    public Result insertComplaint(String token, Map<String, Object> info) {
        String openId = TokenUtil.getOpenId(token);
        Student student = userDao.getStudent(openId);
        Integer sId = student.getId();
        String telephone = (String) info.get("telephone");
        if ("".equals(telephone)) {
            telephone = student.getTelephone();
        } else {
            if (!ParamValidUtil.isTelephone(telephone)) {
                telephone = student.getTelephone();
            }
        }
        String content = (String) info.get("content");
        String picture = null;
        MultipartFile file = (MultipartFile) info.get("file");
        if (file != null && !file.isEmpty()) {
            try {
                file.transferTo(new File("/www/wwwroot/api.echo.ituoniao.net/images/" + file.getOriginalFilename()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            picture = "/www/wwwroot/api.echo.ituoniao.net/images" + file.getOriginalFilename();
        }
        userDao.insertComplaint(sId, telephone, content, picture);
        return ResultUtil.success();
    }
}
