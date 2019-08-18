package com.sloan.music.platform.spider.controller;

import com.sloan.music.platform.spider.service.music163.HomePageSpiderEngine;
import com.sloan.music.platform.spider.service.music163.PlayListSpiderEngine;
import com.sloan.music.platform.spider.service.music163.SongSpiderEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/13
 **/
@RestController
@RequestMapping("/spider")
@Slf4j
public class SpiderController {


    @Resource
    private HomePageSpiderEngine homePageSpiderEngine;

    @Resource
    private PlayListSpiderEngine playListSpiderEngine;

    @Resource
    private SongSpiderEngine songSpiderEngine;

    @RequestMapping(value = "/homepage/playlist", method = RequestMethod.GET)
    public void spiderHomePagePlayList() {

        homePageSpiderEngine.start();
    }

    @RequestMapping(value = "/playlist", method = RequestMethod.GET)
    public void spiderPlayList(@RequestParam String id) {

        playListSpiderEngine.test(id);
    }

    @RequestMapping(value = "/song", method = RequestMethod.GET)
    public void spiderSong(@RequestParam String id) {

        songSpiderEngine.test(id);
    }

}
