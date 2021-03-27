package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.disease.domain.Disease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author HavenTong
 * @date 2021/3/24 下午9:58
 */
@Repository
@Mapper
public interface QuestionDiseaseDao {
    @Select("SELECT * FROM disease WHERE disease_id = #{diseaseId}")
    Disease findDiseaseById(@Param("diseaseId") int diseaseId);
}
