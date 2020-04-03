package com.song.vhr.mapper;

import com.song.vhr.model.Employee;
import com.song.vhr.model.Employeetrain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author song
 * @data 2020/2/25
 */
public interface EmployeetrainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeetrain record);

    int insertSelective(Employeetrain record);

    Employeetrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeetrain record);

    int updateByPrimaryKey(Employeetrain record);

    List<Employeetrain> getEmployeeTranByPage(@Param("page") Integer page, @Param("size") Integer size,@Param("keywords") String keywords);

    Long getTotal(@Param("keywords")String keywords);

    List<Employee> getAllEmployee(@Param("page") Integer page, @Param("size") Integer size);

    Long getTotalEmp();
}