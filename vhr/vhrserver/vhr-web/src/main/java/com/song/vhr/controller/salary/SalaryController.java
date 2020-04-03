package com.song.vhr.controller.salary;

import com.song.vhr.model.RespBean;
import com.song.vhr.model.Salary;
import com.song.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author song
 * @data 2020/2/24
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;
    @GetMapping("/")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        if(salaryService.addSalary(salary)==1){
            return  RespBean.ADD_SUCCESS;
        }
        return  RespBean.ADD_ERROR;
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id){
        if(salaryService.deleteSalary(id)==1){
            return  RespBean.DELETE_SUCCESS;
        }
        return  RespBean.DELETE_ERROR;
    }
    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary){
        if(salaryService.updateSalary(salary)==1){
            return  RespBean.UPDATE_SUCCESS;
        }
        return  RespBean.UPDATE_ERROR;
    }
}
