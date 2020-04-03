package com.song.vhr.mapper;

import com.song.vhr.model.Adjustsalary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/26
 */
public interface AdjustsalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Adjustsalary record);

    int insertSelective(Adjustsalary record);

    Adjustsalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Adjustsalary record);

    int updateByPrimaryKey(Adjustsalary record);

    List<Adjustsalary> getAlladjustsalary(@Param("keywords") String keywords);
}