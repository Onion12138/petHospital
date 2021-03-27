package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.exam.domain.ExamScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
}
