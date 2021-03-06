package cn.hsy.echo.dao;

import cn.hsy.echo.pojo.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    // 通过openId找到学生编号
    @Select("SELECT id FROM student WHERE open_id=#{openId}")
    public Integer getSId(@Param("openId") String openId);

    // 通过学生学号查找openId
    @Select("SELECT open_id FROM student WHERE student_id=#{studentId}")
    public String getOpenId(@Param("studentId") int studentId);

    // 判断学号在不在
    @Select("SELECT 1 FROM student WHERE student_id=#{studentId}")
    public Integer hasExistStudentId(@Param("studentId") int studentId);

    // 通过openId找到学生的详细信息
    public Student getStudent(@Param("openId") String openId);

    // 通过学生编号找到学生的宿舍编号
    @Select("SELECT d_id FROM accommendation WHERE accommendation.s_id=#{id}")
    public int getDid(@Param("id") int id);

    // 通过学生编号找到学生宿舍信息
    @Select("SELECT zone, building, room " +
            "FROM accommendation, dormitory " +
            "WHERE accommendation.s_id=#{id} AND accommendation.d_id=dormitory.id")
    public Dormitory getDormitory(@Param("id") int id);

    // 通过学生学号修改学生的openId
    @Update("UPDATE student SET open_id=#{openId} WHERE student_id=#{studentId}")
    public void updateOpenId(@Param("openId") String openId, @Param("studentId") int studentId);

    // 获取首页公告信息
    @Select("SELECT information.id, title, content, time, name " +
            "FROM information, worker " +
            "WHERE information.zone=0 AND information.w_id=worker.id " +
            "ORDER BY time DESC")
    public List<Information> listGlobalAnnouncement();

    // 获取小区公告信息
    @Select("SELECT information.id, title, content, time, name " +
            "FROM information, worker " +
            "WHERE information.zone=#{zone} AND information.building is null AND information.w_id=worker.id " +
            "ORDER BY time DESC")
    public List<Information> listZoneAnnouncement(@Param("zone") int zone);

    // 获取我的消息
    @Select("SELECT information.id, title, content, time, name " +
            "FROM information, worker " +
            "WHERE information.zone=#{zone} AND information.building=#{building} AND (information.room is null or information.room=#{room}) and information.w_id=worker.id " +
            "ORDER BY time DESC")
    public List<Information> listMyNotice(@Param("zone") int zone, @Param("building") int building, @Param("room") int room);

    // 获取维修列表 以个人为单位
//    @Select("SELECT repair.id, name, content, time, repair.telephone, picture, status " +
//            "FROM student, repair " +
//            "WHERE repair.s_id=#{id} and repair.s_id=student.id " +
//            "ORDER BY time DESC")
//    public List<Repair> listRepair(@Param("id") int id);

    // 获取维修列表 以宿舍为单位
    @Select("SELECT repair.id, name, content, time, update_time, repair.telephone, picture, status " +
            "FROM student, repair " +
            "WHERE repair.d_id=#{id} and repair.s_id=student.id " +
            "ORDER BY update_time DESC")
    public List<Repair> listRepair(@Param("id") int id);

    // 获取一条维修记录
    @Select("SELECT repair.id, d_id, name, content, time, update_time, repair.telephone, picture, status " +
            "FROM student, repair " +
            "WHERE repair.id=#{id} and repair.s_id=student.id")
    public Repair getRepair(@Param("id") int id);

    // 获取维修回复列表
    @Select("SELECT name, content, reply_time, reply_type " +
            "FROM repair_reply, student " +
            "WHERE repair_reply.repair_id=#{id} and repair_reply.reply_id=student.id and repair_reply.reply_type=1 " +
            "ORDER BY reply_time DESC")
    public List<Reply> listRepairApplicantReply(@Param("id") int id);

    // 获取管理员回复列表
    @Select("SELECT content, reply_time, reply_type " +
            "FROM repair_reply " +
            "WHERE repair_reply.repair_id=#{id} and repair_reply.reply_type=0 " +
            "ORDER BY reply_time DESC")
    public List<Reply> listRepairAdminReply(@Param("id") int id);

    // 更新维修时间
    @Update("UPDATE repair SET update_time=now() WHERE id=#{id}")
    public void updateRepairTime(@Param("id") int id);

    // 回复维修
    @Insert("INSERT INTO repair_reply (reply_id, content, reply_type, reply_time, repair_id) VALUES (#{sId}, #{content}, 1, now(), #{id})")
    public void insertRepairReply(@Param("sId") int sId, @Param("content") String content, @Param("id") int id);

    //获取投诉列表
    @Select("SELECT complaint.id, name, content, time, update_time, complaint.telephone, picture, status " +
            "FROM student, complaint " +
            "WHERE complaint.s_id=#{id} and complaint.s_id=student.id " +
            "ORDER BY update_time DESC")
    public List<Complaint> listComplaint(@Param("id") int id);

    // 获取一个投诉信息
    @Select("SELECT complaint.id, s_id, name, content, time, update_time, complaint.telephone, picture, status " +
            "FROM student, complaint " +
            "WHERE complaint.id=#{id} and complaint.s_id=student.id")
    public Complaint getComplaint(@Param("id") int id);

    // 获取投诉回复列表
    @Select("SELECT name, content, reply_time, reply_type " +
            "FROM complaint_reply, student " +
            "WHERE complaint_reply.complaint_id=#{id} and complaint_reply.reply_id = student.id and complaint_reply.reply_type=1 " +
            "ORDER BY reply_time DESC")
    public List<Reply> listComplaintComplainantReply(@Param("id") int id);

    // 获取管理员回复列表
    @Select("SELECT content, reply_time, reply_type " +
            "FROM complaint_reply " +
            "WHERE complaint_reply.complaint_id=#{id} and complaint_reply.reply_type=0 " +
            "ORDER BY reply_time DESC")
    public List<Reply> listComplaintAdminReply(@Param("id") int id);

    // 更新维修时间
    @Update("UPDATE complaint SET update_time=now() WHERE id=#{id}")
    public void updateComplaintTime(@Param("id") int id);

    // 回复维修
    @Insert("INSERT INTO complaint_reply (reply_id, content, reply_type, reply_time, complaint_id) VALUES (#{sId}, #{content}, 1, now(), #{id})")
    public void insertComplaintReply(@Param("sId") int sId, @Param("content") String content, @Param("id") int id);


    // 添加form_id
    @Insert("INSERT INTO push (s_id, form_id) VALUES (#{id}, #{formId})")
    public void insertFormId(@Param("id") int id, @Param("formId") String formId);

    // 获取水电列表
    @Select("SELECT * FROM fee WHERE d_id=#{id} ORDER BY end_time DESC")
    public List<Fee> listHistoryFee(@Param("id") int id);

    // 获取水电详情
    @Select("SELECT * FROM fee WHERE id=#{id}")
    public Fee getFeeDetail(@Param("id") int id);


    // 修改水电状态
    @Update("UPDATE fee SET status=1 WHERE id=#{id}")
    public void updateFeeStatus(@Param("id") int id);

    // 获取问卷调查列表
    @Select("SELECT questionnaire.id, title, start_time, end_time, name " +
            "FROM questionnaire, worker " +
            "WHERE questionnaire.w_id=worker.id AND (zone=0 or zone=#{zone}) AND end_time>now()" +
            "ORDER BY end_time DESC")
    public List<Questionnaire> listQuestionnaire(@Param("zone") int zone);

    // 获取问卷id
    public Questionnaire getQuestionnaire(@Param("id") int id);

    // 获取问题id的问题列表
    public Question getQuestion(@Param("id") int id);

    //获取问题id的选项列表
    public Option getOption(@Param("id") int id);

    // 提交问卷答题情况
    @Update("UPDATE options SET select_number=select_number+1 WHERE id=#{id}")
    public void updateOptionSelectNumber(@Param("id") int id);

    // 添加报修记录
    @Insert("INSERT INTO repair (d_id, s_id, telephone, content, picture, status, time, update_time) VALUES (#{dId}, #{sId}, #{telephone}, #{content}, #{picture}, 0, now(), now())")
    public void insertRepair(@Param("dId") int dId, @Param("sId") int sId, @Param("telephone") String telephone, @Param("content") String content, @Param("picture") String picture);

    // 添加投诉记录
    @Insert("INSERT INTO complaint (s_id, telephone, content, picture, status, time, update_time) VALUES (#{sId}, #{telephone}, #{content}, #{picture}, 0, now(), now())")
    public void insertComplaint(@Param("sId") int sId, @Param("telephone") String telephone, @Param("content") String content, @Param("picture") String picture);
}
