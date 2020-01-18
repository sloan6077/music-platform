package com.sloan.music.platform.spider.controller;

import com.sloan.music.platform.spider.dao.es.MusicRepository;
import com.sloan.music.platform.spider.model.es.MusicSongEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/es")
@Slf4j
public class ESController {


    @Resource
    private MusicRepository musicRepository;


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean save(@RequestBody MusicSongEntity music) {

        musicRepository.save(music);

        return true;
    }
}
