package com.song.vhr.service;

import com.song.vhr.mapper.AdjustsalaryMapper;
import com.song.vhr.model.Adjustsalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author song
 * @data 2020/2/26
 */
@Service
public class PerSalaryService {
//Adjustsalary
@Autowired
AdjustsalaryMapper adjustsalaryMapper;


    public List<Adjustsalary> getAlladjustsalary(String keywords) {
        return adjustsalaryMapper.getAlladjustsalary(keywords);
    }

    public Integer AddAdjustsalary(Adjustsalary adjustsalary) {
        adjustsalary.setAsdate(new Date());
        return adjustsalaryMapper.insertSelective(adjustsalary);
    }

    public Integer updateAdjustsalary(Adjustsalary adjustsalary) {
        return adjustsalaryMapper.updateByPrimaryKeySelective(adjustsalary);
    }

    public Integer deleteAdjustsalary(Integer id) {
        return adjustsalaryMapper.deleteByPrimaryKey(id);
    }
}
