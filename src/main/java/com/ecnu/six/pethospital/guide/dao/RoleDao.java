package com.ecnu.six.pethospital.guide.dao;

import com.ecnu.six.pethospital.guide.domain.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:59
 */
@Mapper
@Repository
public interface RoleDao {
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role findRoleById(@Param("id") int id);
}
