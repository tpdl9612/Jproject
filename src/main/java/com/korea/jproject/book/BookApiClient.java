package com.korea.jproject.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookApiClient {
    private final RestTemplate restTemplate;

    private final String CLIENT_ID = "3DnNAoGPuz_2VdT0Ho_b";
    private final String CLIENT_SECRET = "gENTbLUm4V";

    private final String OpenNaverBookUrl_getBooks = "https://openapi.naver.com/v1/search/book?query={keyword}";

    public BooksResponseDto requestBook(String keyword){
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(OpenNaverBookUrl_getBooks, HttpMethod.GET, entity, BooksResponseDto.class, keyword).getBody();
    }
}
