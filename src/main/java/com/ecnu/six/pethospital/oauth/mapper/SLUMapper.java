package com.ecnu.six.pethospital.oauth.mapper;

import com.ecnu.six.pethospital.oauth.entity.SLU;
import com.ecnu.six.pethospital.oauth.entity.SLUExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SLUMapper {
    long countByExample(SLUExample example);

    int deleteByExample(SLUExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SLU record);

    int insertSelective(SLU record);

    List<SLU> selectByExample(SLUExample example);

    SLU selectByPrimaryKey(Integer id);

    SLU selectBySID(@Param("sid") Integer sid);

    int updateByExampleSelective(@Param("record") SLU record, @Param("example") SLUExample example);

    int updateByExample(@Param("record") SLU record, @Param("example") SLUExample example);

    int updateByPrimaryKeySelective(SLU record);

    int updateByPrimaryKey(SLU record);
}
