package com.song.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *
 * @author song
 * @data 2020/2/26
 */
public class Adjustsalary {
    private Integer id;

    private Integer eid;
    private Employee employee;
    private Salary salary;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    /**
    * 调薪日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date asdate;

    /**
    * 调前薪资
    */
    private String beforesalary;

    /**
    * 调后薪资
    */
    private String aftersalary;

    /**
    * 调薪原因
    */
    private String reason;

    /**
    * 备注
    */
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

    public Date getAsdate() {
        return asdate;
    }

    public void setAsdate(Date asdate) {
        this.asdate = asdate;
    }

    public String getBeforesalary() {
        return beforesalary;
    }

    public void setBeforesalary(String beforesalary) {
        this.beforesalary = beforesalary;
    }

    public String getAftersalary() {
        return aftersalary;
    }

    public void setAftersalary(String aftersalary) {
        this.aftersalary = aftersalary;
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