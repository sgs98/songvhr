package com.song.vhr.controller.emp;

import com.song.vhr.model.*;
import com.song.vhr.service.*;
import com.song.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author song
 * @data 2020/2/21
 */
@RestController
@RequestMapping("/emp/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    NationService nationService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;

    @Autowired
    JobLevelService jobLevelService;
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer size, Employee employee, Date[] begindatescope){
        return employeeService.getEmployeeByPage(page,size,employee,begindatescope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if(employeeService.addEmp(employee)==1){

            return RespBean.ADD_SUCCESS;
        }
        return RespBean.ADD_ERROR;
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }
    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/jobLevels")
    public List<JobLevel> getAllJobLevel(){
        return jobLevelService.getAllJobLevels();
    }
    @GetMapping("/position")
    public List<Position> getAllPosition(){
        return positionService.getAllPositions();
    }

    @GetMapping("/maxWordID")
    public RespBean maxWordID(){

        return RespBean.ok("",String.format("%08d",employeeService.maxWordID()+1));
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id){
        if(employeeService.deleteEmp(id)==1){
            return RespBean.DELETE_SUCCESS;
        }
        return RespBean.DELETE_ERROR;
    }
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateEmp(employee)==1){
            return RespBean.UPDATE_SUCCESS;
        }
        return RespBean.UPDATE_ERROR;
    }

    /**
     * 导出数据
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        List<Employee> list= (List<Employee>) employeeService.getEmployeeByPage(null,null,null,null).getData();

        return POIUtils.employee2Excel(list);
    }

    /**
     * 导入数据
     * @return
     */
    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        List<Employee> list = POIUtils.excel2Employee(file, nationService.getAllNations(), politicsstatusService.getAllPoliticsstatus(), departmentService.getAllDepartmentWithOutChildren(), positionService.getAllPositions(), jobLevelService.getAllJobLevels());
        if (employeeService.addEmps(list) == list.size()) {
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }
}