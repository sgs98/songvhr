package com.song.vhr.service;

import com.song.vhr.mapper.EmployeeMapper;
import com.song.vhr.model.Employee;
import com.song.vhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author song
 * @data 2020/2/21
 */
@Service
public class EmployeeService {
    public final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    SimpleDateFormat mouthFormat=new SimpleDateFormat("MM");
    DecimalFormat decimalFormatnew =new DecimalFormat("##.00");
    public RespPageBean getEmployeeByPage(Integer page, Integer size,Employee employee,Date[] begindatescope) {
        if(page!=null&&size!=null){
          page=(page-1)*size;
        }
        List<Employee> data= employeeMapper.getEmployeeByPage(page,size,employee,begindatescope);
        Long total=employeeMapper.getTotal(employee,begindatescope);
        RespPageBean bean=new RespPageBean();
        bean.setTotal(total);
        bean.setData(data);
        return bean;
    }

    public Integer addEmp(Employee employee) {
        Date begincontract = employee.getBegincontract();

        Date endcontract = employee.getEndcontract();
        Double end = Double.parseDouble(yearFormat.format(endcontract));
        Double start = Double.parseDouble(yearFormat.format(begincontract));

        Double mouthEnd = Double.parseDouble(mouthFormat.format(endcontract));
        Double mouthStart = Double.parseDouble(mouthFormat.format(begincontract));

        Double v = (end - start)*12+mouthEnd-mouthStart;
        employee.setContractterm(Double.parseDouble(decimalFormatnew.format(v/12)));


        int result = employeeMapper.insertSelective(employee);
        //组件回填
        if(result==1){
            logger.info(employee.toString());
            Employee emp=employeeMapper.getEmployeeById(employee.getId());
            //发邮件
            rabbitTemplate.convertAndSend("song.mail.welcome",emp);
        }

        return result;
    }

    public Integer maxWordID() {
        return employeeMapper.maxWordID();
    }

    public Integer deleteEmp(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }


    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if(page!=null&&size!=null){
         page=(page-1)*size;
        }
        List<Employee> list=employeeMapper.getEmployeeByPageWithSalary(page,size);
        RespPageBean bean=new RespPageBean();
        bean.setData(list);
        bean.setTotal(employeeMapper.getTotal(null,null));
        return bean;
    }

    public Integer updateEmployeeSalary(Integer eid,Integer sid) {
        return employeeMapper.updateEmployeeSalary(eid,sid);
    }
}
