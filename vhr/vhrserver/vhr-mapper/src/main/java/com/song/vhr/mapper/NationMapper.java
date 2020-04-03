package com.song.vhr.mapper;

import com.song.vhr.model.Nation;

import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/21
 */
public interface NationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Nation record);

    int insertSelective(Nation record);

    Nation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Nation record);

    int updateByPrimaryKey(Nation record);

    List<Nation> getAllNations();
}