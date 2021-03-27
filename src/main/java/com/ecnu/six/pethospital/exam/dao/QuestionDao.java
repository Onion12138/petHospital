package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.exam.domain.Question;
import com.ecnu.six.pethospital.exam.dto.QuestionRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/24 下午8:07
 */
@Repository
@Mapper
public interface QuestionDao {

    @Insert("INSERT INTO question(adm_id, disease_id, stem, choice, answer, question_created_time, question_updated_time) " +
            "VALUES (#{admId}, #{diseaseId}, #{stem}, #{choice}, #{answer}, #{questionCreatedTime}, #{questionUpdatedTime})")
    int addQuestion(Question question);

    @Update("UPDATE question SET is_deleted = TRUE WHERE question_id = #{questionId}")
    int deleteQuestionById(@Param("questionId") int questionId);


    @Select("SELECT * FROM question WHERE is_deleted = FALSE AND disease_id = #{diseaseId}")
    @Results({
            @Result(property = "disease", column = "disease_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.QuestionDiseaseDao.findDiseaseById")),
            @Result(property = "adm", column = "adm_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.AdmDao.findAdmById"))
    })
    List<Question> findQuestionByDiseaseId(@Param("diseaseId") int diseaseId);


    @Select("SELECT * FROM question WHERE is_deleted = FALSE AND (stem LIKE #{keyword} OR choice LIKE #{keyword})")
    @Results({
            @Result(property = "disease", column = "disease_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.QuestionDiseaseDao.findDiseaseById")),
            @Result(property = "adm", column = "adm_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.AdmDao.findAdmById"))
    })
    List<Question> findQuestionByKeyword(@Param("keyword") String keyword);


    @Select("SELECT * FROM question WHERE question_id = #{questionId}")
    @Results({
            @Result(property = "disease", column = "disease_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.QuestionDiseaseDao.findDiseaseById")),
            @Result(property = "adm", column = "adm_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.AdmDao.findAdmById"))
    })
    Question findQuestionById(@Param("questionId") int questionId);


    @Select("SELECT * FROM question WHERE is_deleted = FALSE")
    @Results({
            @Result(property = "disease", column = "disease_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.QuestionDiseaseDao.findDiseaseById")),
            @Result(property = "adm", column = "adm_id",
                    one = @One(select = "com.ecnu.six.pethospital.exam.dao.AdmDao.findAdmById"))
    })
    List<Question> findAll();

    @Update("UPDATE question SET adm_id = #{admId}, disease_id = #{diseaseId}, stem = #{stem}, " +
            "choice = #{choice}, answer = #{answer}, question_updated_time = #{questionUpdatedTime} " +
            "WHERE question_id = #{questionId}")
    int updateQuestion(Question question);
}
