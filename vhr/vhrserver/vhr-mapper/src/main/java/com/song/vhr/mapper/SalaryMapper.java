package com.song.vhr.mapper;

import com.song.vhr.model.Salary;

import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/24
 */
public interface SalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Salary record);

    int insertSelective(Salary record);

    Salary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Salary record);

    int updateByPrimaryKey(Salary record);

    List<Salary> getAllSalaries();
}