package com.song.vhr.controller.salary;

import com.song.vhr.model.Employee;
import com.song.vhr.model.RespBean;
import com.song.vhr.model.RespPageBean;
import com.song.vhr.model.Salary;
import com.song.vhr.service.EmployeeService;
import com.song.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author song
 * @data 2020/2/24
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;
    @GetMapping("/")
    public RespPageBean getEmployeePageWithSalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer size){

         return employeeService.getEmployeeByPageWithSalary(page,size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid,Integer sid){
        Integer result = employeeService.updateEmployeeSalary(eid, sid);

        if(result==1||result==2){
            return RespBean.UPDATE_SUCCESS;
        }
        return RespBean.UPDATE_ERROR;
    }
}
