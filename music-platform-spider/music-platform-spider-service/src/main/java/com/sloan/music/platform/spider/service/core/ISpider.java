package com.sloan.music.platform.spider.service.core;

import java.util.Map;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/16
 **/
public interface ISpider {

    /**
     * 设置爬虫url
     * @param url
     */
    void url(String url);

    /**
     * 设置爬虫参数
     * @param param
     */
    void param(Map<String,String> param);

    /**
     * 设置爬虫header
     * @param header
     */
    void header(Map<String,String> header);

    /**
     * 设置表单数据
     * @param formBody
     */
    default void formBody(Map<String,String> formBody) {}

    /**
     * 设置代理
     */
    default void proxy() {}

    /**
     * 解析
     */
    default void parser() {}

    /**
     * 业务处理
     */
    default void business() {}

    /**
     * 发送爬虫后消息
     */
    default void send() {}
}
