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
    int addExam(Exam exam);

    @Select("SELECT * FROM exam WHERE start_time >= #{now}")
    @Results({
            @Result(property = "adm", column = "creator_id",
                one = @One(select = "com.ecnu.six.pethospital.oauth.mapper.LocalUserMapper.selectByPrimaryKey")),
            @Result(property = "paper", column = "paper_id",
                one = @One(select = "com.ecnu.six.pethospital.exam.dao.PaperDao.findPaperById")),
            @Result(property = "questionScores", column = "paper_id",
                many = @Many(select = "com.ecnu.six.pethospital.exam.dao.QuestionScoreDao.findPaperAndQuestionById"))
    })
    List<Exam> findAvailableExams(LocalDateTime now);
}
