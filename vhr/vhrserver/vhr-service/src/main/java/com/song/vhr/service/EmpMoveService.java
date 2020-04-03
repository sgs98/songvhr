package com.song.vhr.service;

import com.song.vhr.mapper.EmployeeremoveMapper;
import com.song.vhr.model.Employeeremove;
import com.song.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author song
 * @data 2020/2/26
 */
@Service
public class EmpMoveService {
    @Autowired
    EmployeeremoveMapper employeeremoveMapper;



    public Integer addEmpMove(Employeeremove employeeremove) {
        employeeremove.setRemovedate(new Date());
        return employeeremoveMapper.insertSelective(employeeremove);
    }

    public Integer updateEmpMove(Employeeremove employeeremove) {
        return employeeremoveMapper.updateByPrimaryKeySelective(employeeremove);
    }

    public Integer deleteEmpMove(Integer id) {
        return employeeremoveMapper.deleteByPrimaryKey(id);
    }

    public List<Employeeremove> getAllEmpMove(String keywords) {
        return employeeremoveMapper.getAllEmpMove(keywords);
    }
}
