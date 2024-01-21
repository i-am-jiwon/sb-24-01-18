package com.ll.sb240118.domain.member.member.controller;

import com.ll.sb240118.domain.global.rq.Rq.Rq;
import com.ll.sb240118.domain.member.member.dto.MemberDto;
import com.ll.sb240118.domain.member.member.entity.Member;
import com.ll.sb240118.domain.member.member.service.MemberService;
import com.ll.sb240118.global.rsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MembersController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;

    @Getter
    @Setter
    public static class LoginRequestBody {
        private String username;
        private String password;
    }

    @Getter
    public static class LoginResponseBody {
        private final MemberDto item;

        public LoginResponseBody(Member member) {
            item = new MemberDto(member);
        }
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login(
            @RequestBody LoginRequestBody requestBody
    ) {
        RsData<Member> checkRs = memberService.checkUsernameAndPassword(
                requestBody.getUsername(),
                requestBody.getPassword()
        );

        Member member = checkRs.getData();

        return RsData.of(
                "200",
                "로그인 성공",
                new LoginResponseBody(member)
        );
    }
}