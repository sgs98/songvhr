package com.song.vhr.service;

import com.song.vhr.mapper.DepartmentMapper;
import com.song.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author song
 * @data 2020/2/20
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
    }

    public void deleteDepById(Department department) {
        departmentMapper.deleteDepById(department);
    }

    public List<Department> getAllDepartmentWithOutChildren() {
        return departmentMapper.getAllDepartmentWithOutChildren();
    }
}
