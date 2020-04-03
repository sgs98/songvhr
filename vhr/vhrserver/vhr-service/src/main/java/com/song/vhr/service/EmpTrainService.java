package com.song.vhr.service;

import com.song.vhr.mapper.EmployeetrainMapper;
import com.song.vhr.model.Employee;
import com.song.vhr.model.Employeetrain;
import com.song.vhr.model.RespBean;
import com.song.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author song
 * @data 2020/2/25
 */
@Service
public class EmpTrainService {

    @Autowired
    EmployeetrainMapper employeetrainMapper;
    public RespPageBean getEmployeeTranByPage(Integer page, Integer size,String keywords) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employeetrain> list=employeetrainMapper.getEmployeeTranByPage(page,size,keywords);
        RespPageBean bean=new RespPageBean();
        bean.setData(list);
        bean.setTotal(employeetrainMapper.getTotal(keywords));
        return bean;
    }

    public RespPageBean getAllEmployee(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer size) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> list=employeetrainMapper.getAllEmployee(page,size);
        RespPageBean bean=new RespPageBean();
        bean.setData(list);
        bean.setTotal(employeetrainMapper.getTotalEmp());
        return bean;
    }

    public Integer addEmpTrain(Employeetrain employeetrain) {
        employeetrain.setTraindate(new Date());
        return employeetrainMapper.insertSelective(employeetrain);
    }

    public Integer updateEmpTrain(Employeetrain employeetrain) {
        return employeetrainMapper.updateByPrimaryKeySelective(employeetrain);
    }

    public Integer deleteEmpTrain(Integer id) {
        return employeetrainMapper.deleteByPrimaryKey(id);
    }
}
