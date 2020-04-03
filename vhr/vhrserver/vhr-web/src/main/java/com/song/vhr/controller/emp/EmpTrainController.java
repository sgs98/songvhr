package com.song.vhr.controller.emp;

import com.song.vhr.model.Employeetrain;
import com.song.vhr.model.RespBean;
import com.song.vhr.model.RespPageBean;
import com.song.vhr.service.EmpTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author song
 * @data 2020/2/25
 */
@RestController
@RequestMapping("/personnel/train")
public class EmpTrainController {

    @Autowired
    EmpTrainService empTrainService;

    @GetMapping("/")
    public RespPageBean getEmployeeTranByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer size,String keywords){
        return empTrainService.getEmployeeTranByPage(page,size,keywords);
    }
    @GetMapping("/emp")
    public RespPageBean getAllEmployee(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer size){
        return empTrainService.getAllEmployee(page,size);
    }
    @PostMapping("/")
    public RespBean addEmpTrain(@RequestBody Employeetrain employeetrain){
        if(empTrainService.addEmpTrain(employeetrain)==1){
            return RespBean.ADD_SUCCESS;
        }
        return RespBean.ADD_ERROR;
    }
    @PutMapping("/")
    public RespBean updateEmpTrain(@RequestBody Employeetrain employeetrain){
        if(empTrainService.updateEmpTrain(employeetrain)==1){
            return RespBean.UPDATE_SUCCESS;
        }
        return RespBean.UPDATE_ERROR;
    }
    @DeleteMapping("/{id}")
    public RespBean deleteEmpTrain(@PathVariable Integer id){
        if(empTrainService.deleteEmpTrain(id)==1){
            return RespBean.DELETE_SUCCESS;
        }
        return RespBean.DELETE_ERROR;
    }
}
