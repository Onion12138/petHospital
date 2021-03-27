package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.exam.domain.Exam;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:07
 */
@Repository
@Mapper
public interface ExamDao {

    @Insert("INSERT INTO exam(paper_id, exam_name, creator_id, start_time) " +
            "VALUES (#{paperId}, #{examName}, #{creatorId}, #{startTime})")
    void addExam(Exam exam);

    @Select("SELECT * FROM exam WHERE start_time >= #{now}")
    @Results({
            @Result(property = "adm", column = "creator_id",
                one = @One(select = "com.ecnu.six.pethospital.exam.dao.AdmDao.findAdmById")),
            @Result(property = "paper", column = "paper_id",
                one = @One(select = "com.ecnu.six.pethospital.exam.dao.PaperDao.findPaperById"))
    })
    List<Exam> findAvailableExams(LocalDateTime now);
}
