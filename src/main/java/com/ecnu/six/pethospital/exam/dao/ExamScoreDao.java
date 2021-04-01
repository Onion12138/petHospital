package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.exam.domain.ExamScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:52
 */
@Repository
@Mapper
public interface ExamScoreDao {
    @Insert("INSERT INTO exam_score VALUES (#{examId}, #{usrId}, #{score})")
    void saveScore(ExamScore examScore);

    @Select("SELECT score FROM exam_score WHERE exam_id = #{examId} AND usr_id = #{usrId}")
    Integer findByExamIdAndUsrId(@Param("examId")int examId, @Param("usrId")int usrId);
}
