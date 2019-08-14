package com.sloan.music.platform.spider.service.common;

import lombok.SneakyThrows;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/14
 **/
public class DateTest {


    @Test
    @SneakyThrows
    public void test() {

        String str = "2019-06-05T19:12:45";

        SimpleDateFormat  sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sf.parse(str);
        System.out.println(date);
    }
}
