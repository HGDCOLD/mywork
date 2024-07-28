package com.cj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findRoleByUserId(@Param("userId") Long userId);

}
