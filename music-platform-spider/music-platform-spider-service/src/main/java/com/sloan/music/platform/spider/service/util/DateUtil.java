package com.sloan.music.platform.spider.service.util;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/14
 **/
public final class DateUtil {


    @SneakyThrows
    public static Date parse(String dateStr) {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sf.parse(dateStr);
    }
}
