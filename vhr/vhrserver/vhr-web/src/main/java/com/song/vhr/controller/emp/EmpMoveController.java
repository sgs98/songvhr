package com.song.vhr.controller.emp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.song.vhr.model.Employeeremove;
import com.song.vhr.model.RespBean;
import com.song.vhr.model.RespPageBean;
import com.song.vhr.service.EmpMoveService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author song
 * @data 2020/2/26
 */
@RestController
@RequestMapping("/personnel/remove")
public class EmpMoveController {

    @Autowired
    EmpMoveService empMoveService;

    @GetMapping("/")
    public RespPageBean getAllEmpMove(@RequestParam("page") Integer page,@RequestParam("size") Integer size,String keywords) {
        PageHelper.startPage(page, size);
        List<Employeeremove> list= empMoveService.getAllEmpMove(keywords);
        PageInfo pageInfo = new PageInfo(list);
        RespPageBean bean = new RespPageBean();
        bean.setData(pageInfo.getList());
        bean.setTotal(pageInfo.getTotal());
        return bean;
    }

    @PostMapping("/")
    public RespBean addEmpMove(@RequestBody Employeeremove employeeremove) {
        if (empMoveService.addEmpMove(employeeremove) == 1) {
            return RespBean.ADD_SUCCESS;
        }
        return RespBean.ADD_ERROR;
    }

    @PutMapping("/")
    public RespBean updateEmpMove(@RequestBody Employeeremove employeeremove) {
        if (empMoveService.updateEmpMove(employeeremove) == 1) {
            return RespBean.UPDATE_SUCCESS;
        }
        return RespBean.UPDATE_ERROR;
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpMove(@PathVariable Integer id) {
        if (empMoveService.deleteEmpMove(id) == 1) {
            return RespBean.DELETE_SUCCESS;
        }
        return RespBean.DELETE_ERROR;
    }
}
