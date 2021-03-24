package com.ecnu.six.pethospital.oauth.mapper;

import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import com.ecnu.six.pethospital.oauth.entity.LocalUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LocalUserMapper {
    long countByExample(LocalUserExample example);

    int deleteByExample(LocalUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LocalUser record);

    int insertSelective(LocalUser record);

    List<LocalUser> selectByExample(LocalUserExample example);

    LocalUser selectByPrimaryKey(Integer id);

    LocalUser selectByStuId(String stuId);

    int updateByExampleSelective(@Param("record") LocalUser record, @Param("example") LocalUserExample example);

    int updateByExample(@Param("record") LocalUser record, @Param("example") LocalUserExample example);

    int updateByPrimaryKeySelective(LocalUser record);

    int updateByPrimaryKey(LocalUser record);
}
