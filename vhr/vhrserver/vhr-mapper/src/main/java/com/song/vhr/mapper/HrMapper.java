package com.song.vhr.mapper;

import com.song.vhr.model.Hr;
import com.song.vhr.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author song
 * @data 2020/2/17
 */
public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    Hr loadUserByUsername(String username);

    List<Role> getHrRolesById(Integer id);

    List<Hr> getAllHrs(@Param("hrid") Integer hrid,@Param("keywords") String keywords);

    Integer addRole(@Param("hrid") Integer hrid, @Param("rids")Integer[] rids);

    List<Hr> getAllHrsExceptCurrentHr(Integer id);
}