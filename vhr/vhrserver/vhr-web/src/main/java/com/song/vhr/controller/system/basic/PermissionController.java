package com.song.vhr.controller.system.basic;

import com.song.vhr.model.Menu;
import com.song.vhr.model.RespBean;
import com.song.vhr.model.Role;
import com.song.vhr.service.MenuService;
import com.song.vhr.service.RoleService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author song
 * @data 2020/2/20
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @GetMapping("/")
    public List<Role> getAllRole(){
        return roleService.getAllRole();
    }
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){

        return menuService.getAllMenus();
    }
    @GetMapping("mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        return menuService.getMidsByRid(rid);
    }
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
       if(menuService.updateMenuRole(rid,mids)){
         return RespBean.UPDATE_SUCCESS;
       }
        return RespBean.UPDATE_ERROR;
    }
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role){
        if(roleService.addRole(role)==1){
            return RespBean.ADD_SUCCESS;
        }
        return RespBean.ADD_ERROR;
    }

    @DeleteMapping("/{rid}")
    public RespBean deleteRoleByRid(@PathVariable Integer rid){
        if(roleService.deleteRoleByRid(rid)==1){
            return RespBean.DELETE_SUCCESS;
        }
        return RespBean.DELETE_ERROR;
    }
}
