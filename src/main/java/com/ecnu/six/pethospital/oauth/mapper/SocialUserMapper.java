package com.ecnu.six.pethospital.oauth.mapper;

import com.ecnu.six.pethospital.oauth.entity.SocialUser;
import com.ecnu.six.pethospital.oauth.entity.SocialUserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SocialUserMapper {
    long countByExample(SocialUserExample example);

    int deleteByExample(SocialUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SocialUser record);

    int insertSelective(SocialUser record);

    List<SocialUser> selectByExample(SocialUserExample example);

    SocialUser selectByUuidAndSource(@Param("uuid") String uuid, @Param("source") String source);

    SocialUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SocialUser record, @Param("example") SocialUserExample example);

    int updateByExample(@Param("record") SocialUser record, @Param("example") SocialUserExample example);

    int updateByPrimaryKeySelective(SocialUser record);

    int updateByPrimaryKey(SocialUser record);
}
