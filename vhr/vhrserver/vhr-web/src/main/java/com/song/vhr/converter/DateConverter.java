package com.song.vhr.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author song
 * @data 2020/2/23
 */
@Component
public class DateConverter implements Converter<String,Date> {
    SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd");

    @Override
    public Date convert(String source) {
        try {
            sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
