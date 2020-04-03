package com.song.vhr.mapper;

import com.song.vhr.model.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/21
 */
public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("employee") Employee employee, @Param("begindatescope") Date[] begindatescope);

    Long getTotal(@Param("employee")Employee employee, @Param("begindatescope") Date[] begindatescope);

    Integer maxWordID();

    Integer addEmps(@Param("list") List<Employee> list);

    Employee getEmployeeById(Integer id);

    List<Employee> getEmployeeByPageWithSalary(@Param("page") Integer page, @Param("size") Integer size);

    Integer updateEmployeeSalary(@Param("eid")Integer eid,@Param("sid")Integer sid);
}