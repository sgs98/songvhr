package com.song.vhr.controller.system.basic;

import com.song.vhr.model.JobLevel;
import com.song.vhr.model.RespBean;
import com.song.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author song
 * @data 2020/2/19
 */
@RestController
@RequestMapping("/system/basic/job")
public class JobLevelController {
    @Autowired
    JobLevelService jobLevelService;

    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
     return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevels(@RequestBody JobLevel jobLevel){
        if(jobLevelService.addJobLevels(jobLevel)==1){
            return RespBean.ADD_SUCCESS;
        }
        return RespBean.ADD_ERROR;
    }
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
        if(jobLevelService.deleteJobLevelById(id)==1){
            return RespBean.DELETE_SUCCESS;
        }
        return RespBean.DELETE_ERROR;
    }

    @PutMapping("/")
    public RespBean updateJobLevels(@RequestBody JobLevel jobLevel){
        if(jobLevelService.updateJobLevels(jobLevel)==1){
            return RespBean.UPDATE_SUCCESS;
        }
        return RespBean.UPDATE_ERROR;
    }
    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids){
        if(jobLevelService.deleteJobLevelByIds(ids)==ids.length){
            return RespBean.DELETE_SUCCESS;
        }
        return RespBean.DELETE_ERROR;
    }
}
