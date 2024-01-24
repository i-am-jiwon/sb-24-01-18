package com.ll.sb240118.domain.member.member.controller;

import com.ll.sb240118.global.rq.Rq.Rq;
import com.ll.sb240118.domain.member.member.dto.MemberDto;
import com.ll.sb240118.domain.member.member.entity.Member;
import com.ll.sb240118.domain.member.member.service.MemberService;
import com.ll.sb240118.global.rsData.RsData;
import com.ll.sb240118.global.util.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        private final String accessToken;

        public LoginResponseBody(Member member, String accessToken) {
            item = new MemberDto(member);
            this.accessToken = accessToken;
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

        Long id = member.getId();
       String accessToken = JwtUtil.encode(
               60*60*24*365,
               Map.of(
                       "id", id.toString(),
                       "username", member.getUsername(),
                       "authorities", member.getAuthoritiesAsStrList()
               )
       );

        return RsData.of(
                "200",
                "로그인 성공",
                new LoginResponseBody(member, accessToken)
        );
    }

}