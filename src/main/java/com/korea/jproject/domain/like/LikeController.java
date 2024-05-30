package com.korea.jproject.domain.like;

import com.korea.jproject.domain.member.Member;
import com.korea.jproject.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/article/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final MemberService memberService;


    @GetMapping("/up/{articleId}")
    public String addLike(@PathVariable ("articleId") Long articleId, Principal principal){
        Member member = memberService.getMember(principal.getName());
        likeService.addLike(articleId, member);
        return "redirect:/article/detail/%d".formatted(articleId);
    }
}
