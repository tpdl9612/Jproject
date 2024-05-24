package com.korea.jproject.domain.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Getter
    @Setter
    class MemberForm{
        @NotEmpty(message = "아이디를 입력해주세요")
        private String loginId;
        @NotEmpty(message = "비밀번호를 입력해주세요")
        private String password1;
        @NotEmpty(message = "비밀번호 확인을 입력해주세요")
        private String password2;
        @NotEmpty(message = "닉네임을 입력해주세요")
        private String nickname;
        @NotEmpty(message = "이메일을 입력해주세요")
        private String email;
        private String errorField;
    }

    @GetMapping("/signup")
    public String signup(MemberForm memberForm){
        return "signup_form";
    }
    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup_form";
        }

        if(!memberForm.getPassword1().equals(memberForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        try{
            memberService.create(memberForm.getLoginId(), memberForm.getPassword1(),
                    memberForm.getNickname(), memberForm.getEmail());
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.rejectValue("errorField", "signupFailed",
                    "이미 존재하는 회원 아이디입니다.");
            return "signup_form";
        }catch(Exception e){
            e.printStackTrace();
            bindingResult.rejectValue("errorField", "signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/article/list";
    }

    @GetMapping("/login")
    public String login(){
        return "login_form";
    }
}
