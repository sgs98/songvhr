package com.song.vhr.mapper;

import com.song.vhr.model.Empsalary;

/**
 *
 * @author song
 * @data 2020/2/24
 */
public interface EmpsalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Empsalary record);

    int insertSelective(Empsalary record);

    Empsalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Empsalary record);

    int updateByPrimaryKey(Empsalary record);
}