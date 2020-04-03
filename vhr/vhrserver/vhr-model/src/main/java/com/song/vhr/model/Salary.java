package com.song.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *
 * @author song
 * @data 2020/2/24
 */
public class Salary {
    private Integer id;

    /**
    * 基本工资
    */
    private Integer basicSalary;

    /**
    * 奖金
    */
    private Integer bonus;

    /**
    * 午餐补助
    */
    private Integer lunchSalary;

    /**
    * 交通补助
    */
    private Integer trafficSalary;

    /**
    * 应发工资
    */
    private Integer allSalary;

    /**
    * 养老金基数
    */
    private Integer pensionBase;

    /**
    * 养老金比率
    */
    private Double pensionPer;

    /**
    * 启用时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    /**
    * 医疗基数
    */
    private Integer medicalBase;

    /**
    * 医疗保险比率
    */
    private Double medicalPer;

    /**
    * 公积金基数
    */
    private Integer accumulationFundBase;

    /**
    * 公积金比率
    */
    private Double accumulationFundPer;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Integer basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getLunchSalary() {
        return lunchSalary;
    }

    public void setLunchSalary(Integer lunchSalary) {
        this.lunchSalary = lunchSalary;
    }

    public Integer getTrafficSalary() {
        return trafficSalary;
    }

    public void setTrafficSalary(Integer trafficSalary) {
        this.trafficSalary = trafficSalary;
    }

    public Integer getAllSalary() {
        return allSalary;
    }

    public void setAllSalary(Integer allSalary) {
        this.allSalary = allSalary;
    }

    public Integer getPensionBase() {
        return pensionBase;
    }

    public void setPensionBase(Integer pensionBase) {
        this.pensionBase = pensionBase;
    }

    public Double getPensionPer() {
        return pensionPer;
    }

    public void setPensionPer(Double pensionPer) {
        this.pensionPer = pensionPer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getMedicalBase() {
        return medicalBase;
    }

    public void setMedicalBase(Integer medicalBase) {
        this.medicalBase = medicalBase;
    }

    public Double getMedicalPer() {
        return medicalPer;
    }

    public void setMedicalPer(Double medicalPer) {
        this.medicalPer = medicalPer;
    }

    public Integer getAccumulationFundBase() {
        return accumulationFundBase;
    }

    public void setAccumulationFundBase(Integer accumulationFundBase) {
        this.accumulationFundBase = accumulationFundBase;
    }

    public Double getAccumulationFundPer() {
        return accumulationFundPer;
    }

    public void setAccumulationFundPer(Double accumulationFundPer) {
        this.accumulationFundPer = accumulationFundPer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}