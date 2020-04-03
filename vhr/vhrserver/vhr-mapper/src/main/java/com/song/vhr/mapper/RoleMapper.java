package com.song.vhr.mapper;

import com.song.vhr.model.Menu;
import com.song.vhr.model.Role;

import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/19
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getAllRole();

}