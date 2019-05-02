package cn.hsy.echo.service;

import cn.hsy.echo.dao.UserDao;
import cn.hsy.echo.exception.TokenExpireException;
import cn.hsy.echo.pojo.*;
import cn.hsy.echo.util.Token;
import cn.hsy.echo.util.WeixinApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class WechatService {
    private final UserDao userDao;
    private final Token tokenBuild;
    private final WeixinApi weixinApi;

    @Autowired
    public WechatService(UserDao userDao, Token tokenBuild, WeixinApi weixinApi) {
        this.userDao = userDao;
        this.tokenBuild = tokenBuild;
        this.weixinApi = weixinApi;
    }

    // 微信小程序登入，输入code获取用户唯一的open_id，通过open_id查找学生编号，如果返回为null，则需要绑定学号
    // 如果返回不为null，则返回学生信息
    public Map<String, Object> login(String code) {
        String openId = weixinApi.getOpenId(code);
        Integer id = userDao.getSId(openId);
        String token = tokenBuild.createToken(openId);
        Map<String, Object> result;
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
        result = successMap(data);
        return result;
    }

    // 绑定学号，如果学号不存在或已被绑定，这返回错误信息，否则修改学生open_id
    public Map<String, Object> updateStudentOpenId(String token, int studentId) {
        Map<String, Object> result;
        if(tokenBuild.varify(token)) {
            Integer flag = userDao.hasExistStudentId(studentId);
            if (flag == null) {
                result = errorMap(-1004, "学号不存在");
            } else {
                String openId = userDao.getOpenId(studentId);
                if(openId == null) {
                    openId = tokenBuild.getOpenId(token);
                    System.out.println(openId);
                    userDao.updateOpenId(openId, studentId);
                    Student student = userDao.getStudent(openId);
                    result = successMap(student);
                } else {
                    result = errorMap(-1003, "学号已被绑定过");
                }
            }
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取首页信息
    public Map<String, Object> listGlobalAnnouncement(String token,int pageNum, int pageSize) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            PageHelper.startPage(pageNum, pageSize);
            List<Information> noticeList = userDao.listGlobalAnnouncement();
            PageInfo<Information> pageInfo = new PageInfo<>(noticeList);
            Map<String, Object> data = new HashMap<>();
            data.put("page", pageInfo);
            result = successMap(data);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取小区公告信息
    public Map<String, Object> listZoneAnnouncement(String token, int pageNum, int pageSize) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Integer id = userDao.getSId(openId);
            Dormitory dormitory = userDao.getDormitory(id);
            PageHelper.startPage(pageNum, pageSize);
            List<Information> noticeList = userDao.listZoneAnnouncement(dormitory.getZone());
            PageInfo<Information> pageInfo = new PageInfo<>(noticeList);
            Map<String, Object> data = new HashMap<>();
            data.put("page", pageInfo);
            result = successMap(data);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取我的信息
    public Map<String, Object> listMyNotice(String token, int pageNum, int pageSize) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Integer id = userDao.getSId(openId);
            Dormitory dormitory = userDao.getDormitory(id);
            PageHelper.startPage(pageNum, pageSize);
            List<Information> noticeList = userDao.listMyNotice(dormitory.getZone(), dormitory.getBuilding(), dormitory.getRoom());
            PageInfo<Information> pageInfo = new PageInfo<>(noticeList);
            Map<String, Object> data = new HashMap<>();
            data.put("page", pageInfo);
            result = successMap(data);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 添加formId
    public Map<String, Object> insertFormId(String token, String formId) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            int id = userDao.getSId(openId);
            userDao.insertFormId(id, formId);
            result = successMap(null);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取水电历史记录
    public Map<String, Object> listHistoryFee(String token, int pageNum, int pageSize) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Integer sId = userDao.getSId(openId);
            Integer dId = userDao.getDid(sId);
            PageHelper.startPage(pageNum, pageSize);
            List<Fee> feeList = userDao.listHistoryFee(dId);
            PageInfo<Fee> pageInfo = new PageInfo<>(feeList);
            Map<String, Object> data = new HashMap<>();
            data.put("page", pageInfo);
            result = successMap(data);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取水电详细信息
//    public Map<String, Object> getFeeDetail(String token, int feeId) {
//        Map<String, Object> result;
//        if (tokenBuild.varify(token)) {
//            Fee fee = userDao.getFeeDetail(feeId);
//            result = successMap(fee);
//        } else {
//            throw new TokenExpireException();
//        }
//        return result;
//    }

    // 修改缴费记录
    public Map<String, Object> updateFeeStatus(String token, int feeId) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            userDao.updateFeeStatus(feeId);
            result = successMap(null);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取维修列表
    public Map<String, Object> listRepair(String token, int pageNum, int pageSize) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Integer id = userDao.getSId(openId);
            System.out.println(id);
            PageHelper.startPage(pageNum, pageSize);
            List<Repair> repairList = userDao.listRepair(id);
            System.out.println("now:" + new Date());
            for(Repair repair : repairList) {
                System.out.println(repair.getTime());
            }
            PageInfo<Repair> pageInfo = new PageInfo<>(repairList);
            Map<String, Object> data = new HashMap<>();
            data.put("page", pageInfo);
            result = successMap(data);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取投诉列表
    public Map<String, Object> listComplaint(String token, int pageNum, int pageSize) {
        Map<String, Object> result;
        if (tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Integer id = userDao.getSId(openId);
            PageHelper.startPage(pageNum, pageSize);
            List<Complaint> complaintList = userDao.listComplaint(id);
            PageInfo<Complaint> pageInfo = new PageInfo<>(complaintList);
            Map<String, Object> data = new HashMap<>();
            data.put("page", pageInfo);
            result = successMap(data);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取问卷调查列表
    public Map<String, Object> listQuestionnaire(String token, int pageNum, int pageSize) {
        Map<String, Object> result;
        if(tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Integer id = userDao.getSId(openId);
            Dormitory dormitory = userDao.getDormitory(id);
            PageHelper.startPage(pageNum, pageSize);
            List<Questionnaire> questionnaireList = userDao.listQuestionnaire(dormitory.getZone());
            PageInfo<Questionnaire> pageInfo = new PageInfo<>(questionnaireList);
            Map<String, Object> data = new HashMap<>();
            data.put("page", pageInfo);
            result = successMap(data);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 获取问卷详细信息
    public Map<String, Object> getQuestionnaireDetail(String token, int id) {
        Map<String, Object> result;
        if(tokenBuild.varify(token)) {
            Questionnaire questionnaire = userDao.getQuestionnaire(id);
            result = successMap(questionnaire);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 批量增加被选中选项的选中次数+1
    public Map<String, Object> updateOptionSelectNumber(String token, List<Integer> list) {
        Map<String, Object> result;
        if(tokenBuild.varify(token)) {
            for(int id : list) {
                userDao.updateOptionSelectNumber(id);
            }
            result = successMap(null);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 上传报修照片及添加报修记录
    public Map<String, Object> insertRepair(String token, Map<String, Object> info) {
        Map<String, Object> result;
        if(tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Student student = userDao.getStudent(openId);
            Integer sId = student.getId();
            Integer dId = userDao.getDid(sId);
            String telephone = (String) info.get("telephone");
            if("".equals(telephone)) {
                telephone = student.getTelephone();
            }
            String content = (String) info.get("content");
            String picture = null;
            MultipartFile file = (MultipartFile) info.get("file");
            if(file != null && !file.isEmpty()) {
                try {
                    file.transferTo(new File("/www/wwwroot/api.echo.ituoniao.net/images/" + file.getOriginalFilename()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                picture = "/www/wwwroot/api.echo.ituoniao.net/images/" + file.getOriginalFilename();
            }
            userDao.insertRepair(dId, sId, telephone, content, picture);
            result = successMap(null);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    // 上传投诉照片及添加投诉记录
    public Map<String, Object> insertComplaint(String token, Map<String, Object> info) {
        Map<String, Object> result;
        if(tokenBuild.varify(token)) {
            String openId = tokenBuild.getOpenId(token);
            Student student = userDao.getStudent(openId);
            Integer sId = student.getId();
            String telephone = (String) info.get("telephone");
            if("".equals(telephone)) {
                telephone = student.getTelephone();
            }
            String content = (String) info.get("content");
            String picture = null;
            MultipartFile file = (MultipartFile) info.get("file");
            if(file != null && !file.isEmpty()) {
                try {
                    file.transferTo(new File("/www/wwwroot/api.echo.ituoniao.net/images/" + file.getOriginalFilename()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                picture = "/www/wwwroot/api.echo.ituoniao.net/images" + file.getOriginalFilename();
            }
            userDao.insertComplaint(sId, telephone, content, picture);
            result = successMap(null);
        } else {
            throw new TokenExpireException();
        }
        return result;
    }

    private Map<String, Object> successMap(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("code", 200);
        result.put("errMsg", "");
        result.put("data", data);
        return result;
    }

    private Map<String, Object> errorMap(int code, String errMsg) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("code", code);
        result.put("errMsg", errMsg);
        result.put("data", null);
        return result;
    }
}
