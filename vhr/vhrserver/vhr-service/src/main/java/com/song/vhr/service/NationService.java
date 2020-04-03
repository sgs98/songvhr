package com.song.vhr.service;

import com.song.vhr.mapper.NationMapper;
import com.song.vhr.model.Nation;
import com.song.vhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author song
 * @data 2020/2/21
 */
@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;


    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }

}
