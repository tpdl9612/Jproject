package com.korea.jproject.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String loginId, String password, String nickname, String email){
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(passwordEncoder.encode(password));
        member.setNickname(nickname);
        member.setEmail(email);
        member.setRegDate(LocalDateTime.now());
        return memberRepository.save(member);
    }

    public Member getMember(String loginId){
        return memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회원 아이디입니다."));
    }
}
