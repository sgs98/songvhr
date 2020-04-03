package com.song.vhr.controller.system;

import com.song.vhr.model.Hr;
import com.song.vhr.model.RespBean;
import com.song.vhr.model.Role;
import com.song.vhr.service.HrService;
import com.song.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author song
 * @data 2020/2/21
 */
@RestController
@RequestMapping("system/hr")
public class HrController {

    @Autowired
    HrService hrService;
    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
    }
    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
      if(hrService.updateHr(hr)==1){
          return RespBean.UPDATE_SUCCESS;
      }else{
          return RespBean.UPDATE_ERROR;
      }
    }
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRole();
    }

    @PutMapping("/updateHrRole")
    public RespBean updateHrRole(Integer hrid,Integer[] rids){
        if(hrService.updateHrRole(hrid,rids)){
            return RespBean.UPDATE_SUCCESS;
        }else{
            return RespBean.UPDATE_ERROR;
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id){
        if(hrService.deleteHrById(id)==1){
            return RespBean.DELETE_SUCCESS;
        }else{
            return RespBean.DELETE_ERROR;
        }
    }
}
