package com.song.vhr.mapper;

import com.song.vhr.model.Position;
import com.song.vhr.model.RespBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author song
 * @data 2020/2/19
 */
public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> getAllPositions();

    Integer deletePositionByIds(@Param("ids") Integer[] ids);
}