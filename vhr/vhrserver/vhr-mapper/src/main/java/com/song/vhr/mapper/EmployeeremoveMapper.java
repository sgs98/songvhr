package com.song.vhr.mapper;

import com.song.vhr.model.Employeeremove;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/26
 */
public interface EmployeeremoveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeremove record);

    int insertSelective(Employeeremove record);

    Employeeremove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeremove record);

    int updateByPrimaryKey(Employeeremove record);

    List<Employeeremove> getAllEmpMove(@Param("keywords") String keywords);
}