package com.ecnu.six.pethospital.exam.dao;

import com.ecnu.six.pethospital.oauth.entity.Adm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author HavenTong
 * @date 2021/3/24 下午11:17
 */
@Mapper
@Repository
public interface AdmDao {
    @Select("SELECT * FROM adm WHERE adm_id = #{admId}")
    Adm findAdmById(@Param("admId") int admId);
}
