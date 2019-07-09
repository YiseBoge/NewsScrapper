package com.se.NewsScrapper.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    private String title;
    private String description;
    private String date;
    private String imageUrl;
    private String source;
    private String detailLink;

}
