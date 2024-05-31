package com.korea.jproject.book;

import lombok.Builder;
import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String link;
    private String author;
    private String image;
    private String publisher;
    private String pubdate;

    @Builder
    public BookDto(String title, String link, String author, String image, String publisher, String pubdate){
        this.title = title;
        this.link = link;
        this.author = author;
        this.image = image;
        this.publisher = publisher;
        this.pubdate = pubdate;
    }
}
