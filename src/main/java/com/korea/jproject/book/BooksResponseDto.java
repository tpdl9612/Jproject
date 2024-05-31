package com.korea.jproject.book;

import lombok.Data;
import java.util.List;

@Data
public class BooksResponseDto {
//    private int display;
    private List<BookDto> items;
}
