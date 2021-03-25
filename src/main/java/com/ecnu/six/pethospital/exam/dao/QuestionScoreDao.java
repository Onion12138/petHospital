package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午3:29
 */
@Repository
@Mapper
public interface QuestionScoreDao {

    @Insert("<script>" +
            "INSERT INTO question_score VALUES " +
            "<foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item.paperId}, #{item.questionId}, #{item.score})" +
            "</foreach>" +
            "</script>")
    void addQuestionScores(List<QuestionScore> questionScores);

    @Delete("DELETE FROM question_score WHERE paper_id = #{paperId}")
    void deleteQuestionScoreByPaperId(int paperId);

    @Select("SELECT * FROM question_score WHERE paper_id = #{paperId}")
    @Results({
            @Result(property = "paper", column = "paper_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.PaperDao.findPaperById")),
            @Result(property = "question", column = "question_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.QuestionDao.findQuestionById"))
    })
    List<QuestionScore> findPaperAndQuestionById(int paperId);
}
