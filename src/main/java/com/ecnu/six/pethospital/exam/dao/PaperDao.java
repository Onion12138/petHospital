package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.exam.domain.Paper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午3:15
 */
@Repository
@Mapper
public interface PaperDao {

    @Insert("INSERT INTO paper(paper_name, adm_id, duration, paper_created_time, paper_updated_time) " +
            "VALUES (#{paperName}, #{admId}, #{duration}, #{paperCreatedTime}, #{paperUpdatedTime})")
    @Options(useGeneratedKeys = true, keyProperty = "paperId", keyColumn = "paper_id")
    int addPaper(Paper paper);

    @Update("UPDATE paper SET paper_name = #{paperName}, adm_id = #{admId}, duration = #{duration}, " +
            "paper_updated_time = #{paperUpdatedTime} " +
            "WHERE paper_id = #{paperId}")
    int updatePaper(Paper paper);

    @Update("UPDATE paper SET is_deleted = TRUE WHERE paper_id = #{paperId}")
    int deletePaperById(int paperId);

    @Select("SELECT * FROM paper WHERE is_deleted = FALSE")
    @Results({
            @Result(property = "adm", column = "adm_id",
                one = @One(select = "com.ecnu.six.pethospital.exam.dao.AdmDao.findAdmById"))
    })
    List<Paper> findAll();

}
