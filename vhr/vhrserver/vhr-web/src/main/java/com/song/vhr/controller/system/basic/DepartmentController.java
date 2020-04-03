package com.song.vhr.controller.system.basic;

import com.song.vhr.model.Department;
import com.song.vhr.model.RespBean;
import com.song.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author song
 * @data 2020/2/20
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @GetMapping("/")
    public List<Department> getAllDepartment(){
      return departmentService.getAllDepartment();
    }
    @PostMapping("/")
    public RespBean addDep(@RequestBody Department department){
        departmentService.addDep(department);
        if(department.getResult()==1){
            return RespBean.ok("添加成功",department);
        }
        return RespBean.ADD_ERROR;
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id){
        Department department = new Department();
        department.setId(id);
        departmentService.deleteDepById(department);
        if(department.getResult()==-2){
            return RespBean.error("该部门下有子部门，删除失败");
        }else if(department.getResult()==-1){
            return RespBean.error("该部门下有员工，删除失败");
        }else if(department.getResult()==1){
            return RespBean.DELETE_SUCCESS;
        }else{
            return RespBean.DELETE_ERROR;
        }

    }
}
