package com.korea.jproject.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookApiClient bookApiClient;
    @GetMapping("/book/list/")
    public String list(@RequestParam("keyword")String keyword, Model model){
        if (keyword == null || keyword.isEmpty()) {
            return "redirect:/"; // 검색어가 없을 경우 메인 페이지로 리다이렉트
        }
        BooksResponseDto responseDto = bookApiClient.requestBook(keyword);

        List<BookDto> books = responseDto.getItems();
        model.addAttribute("books", books);

        return "list"; // 템플릿 경로 명확히 지정
    }



//    @GetMapping("/book/list")
//    public String list(String text, Model model){
//        String clientId = "3DnNAoGPuz_2VdT0Ho_b";
//        String clientSecret = "gENTbLUm4V";
//
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://openapi.naver.com")
//                .path("/v1/search/book.json")
//                .queryParam("query", text)
//                .queryParam("display", 10)
//                .queryParam("start", 1)
//                .queryParam("sort", "sim")
//                .encode()
//                .build()
//                .toUri();
//
//        RequestEntity<Void> req = RequestEntity
//                .get(uri)
//                .header("X-Naver-Client-Id", clientId)
//                .header("X-Naver-Client-Secret", clientSecret)
//                .build();
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
//
//        ObjectMapper om = new ObjectMapper();
//        NaverResultDto resultVO = null;
//
//        try {
//            resultVO = om.readValue(resp.getBody(), NaverResultDto.class);
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        List<BookDto> books = resultVO.getItems();
//        model.addAttribute("books", books);
//
//        return "/book/list";
//
//    }
}
