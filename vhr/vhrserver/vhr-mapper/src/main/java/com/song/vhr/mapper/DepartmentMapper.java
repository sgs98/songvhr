package com.song.vhr.mapper;

import com.song.vhr.model.Department;

import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/20
 */
public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentsByParentId(Integer pid);

    void addDep(Department department);

    void deleteDepById(Department department);

    List<Department> getAllDepartmentWithOutChildren();
}