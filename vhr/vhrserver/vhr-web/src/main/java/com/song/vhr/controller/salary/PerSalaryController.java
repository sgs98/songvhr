package com.song.vhr.controller.salary;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.song.vhr.model.Adjustsalary;
import com.song.vhr.model.RespBean;
import com.song.vhr.model.RespPageBean;
import com.song.vhr.service.PerSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author song
 * @data 2020/2/26
 */
@RestController
@RequestMapping("/personnel/salary")
public class PerSalaryController {
    @Autowired
    PerSalaryService perSalaryService;

    @GetMapping("/")
    public RespPageBean getAlladjustsalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, String keywords) {
        PageHelper.startPage(page, size);
        List<Adjustsalary> list= perSalaryService.getAlladjustsalary(keywords);
        PageInfo pageInfo = new PageInfo(list);
        RespPageBean bean = new RespPageBean();
        bean.setData(pageInfo.getList());
        bean.setTotal(pageInfo.getTotal());
        return bean;
    }

    @PostMapping("/")
    public RespBean addadjustsalary(@RequestBody Adjustsalary adjustsalary) {
        System.out.println(adjustsalary);
        if (perSalaryService.AddAdjustsalary(adjustsalary) == 1) {
            return RespBean.ADD_SUCCESS;
        }
        return RespBean.ADD_ERROR;
    }

    @PutMapping("/")
    public RespBean updateadjustsalary(@RequestBody Adjustsalary adjustsalary) {
        if (perSalaryService.updateAdjustsalary(adjustsalary) == 1) {
            return RespBean.UPDATE_SUCCESS;
        }
        return RespBean.UPDATE_ERROR;
    }

    @DeleteMapping("/{id}")
    public RespBean deleteadjustsalary(@PathVariable Integer id) {
        if (perSalaryService.deleteAdjustsalary(id) == 1) {
            return RespBean.DELETE_SUCCESS;
        }
        return RespBean.DELETE_ERROR;
    }
}
