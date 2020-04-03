package com.song.vhr.utils;

import com.song.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author song
 * @data 2020/2/21
 */
public class HrUtils {
    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
