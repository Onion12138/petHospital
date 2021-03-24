package com.ecnu.six.pethospital.oauth.mapper;

import com.ecnu.six.pethospital.oauth.entity.Adm;
import com.ecnu.six.pethospital.oauth.entity.AdmExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdmMapper {
    long countByExample(AdmExample example);

    int deleteByExample(AdmExample example);

    int deleteByPrimaryKey(Integer admId);

    int insert(Adm record);

    int insertSelective(Adm record);

    List<Adm> selectByExample(AdmExample example);

    Adm selectByPrimaryKey(Integer admId);

    int updateByExampleSelective(@Param("record") Adm record, @Param("example") AdmExample example);

    int updateByExample(@Param("record") Adm record, @Param("example") AdmExample example);

    int updateByPrimaryKeySelective(Adm record);

    int updateByPrimaryKey(Adm record);
}
