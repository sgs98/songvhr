package com.song.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *
 * @author song
 * @data 2020/2/26
 */
public class Employeeremove {
    private Integer id;

    private Integer eid;

    /**
    * 调动后部门
    */
    private String afterdepid;

    /**
    * 调动后职位
    */
    private String afterjobid;

    /**
    * 调动日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date removedate;
    private Department department;
    private JobLevel jobLevel;
    private Employee employee;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
    * 调动原因
    */
    private String reason;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getAfterdepid() {
        return afterdepid;
    }

    public void setAfterdepid(String afterdepid) {
        this.afterdepid = afterdepid;
    }

    public String getAfterjobid() {
        return afterjobid;
    }

    public void setAfterjobid(String afterjobid) {
        this.afterjobid = afterjobid;
    }

    public Date getRemovedate() {
        return removedate;
    }

    public void setRemovedate(Date removedate) {
        this.removedate = removedate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}