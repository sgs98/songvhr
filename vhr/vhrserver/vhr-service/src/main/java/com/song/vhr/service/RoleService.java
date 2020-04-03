package com.song.vhr.service;

import com.song.vhr.mapper.RoleMapper;
import com.song.vhr.model.Menu;
import com.song.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * @author song
 * @data 2020/2/20
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    public Integer addRole(Role role) {
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
    }
    public int deleteRoleByRid(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
