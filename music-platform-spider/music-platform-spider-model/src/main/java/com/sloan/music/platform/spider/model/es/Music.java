package com.sloan.music.platform.spider.model.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "music",type = "_doc")
public class Music {

    @Id
    private String id;

    private String title;

    private String description;
}
