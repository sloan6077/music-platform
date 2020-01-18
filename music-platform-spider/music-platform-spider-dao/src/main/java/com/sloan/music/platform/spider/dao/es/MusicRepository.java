package com.sloan.music.platform.spider.dao.es;

import com.sloan.music.platform.spider.model.es.MusicSongEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MusicRepository extends ElasticsearchRepository<MusicSongEntity, String> {



}
