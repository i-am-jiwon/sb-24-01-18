package com.ll.sb240118.domain.member.member.controller;

import com.ll.sb240118.domain.member.member.dto.MemberDto;
import com.ll.sb240118.domain.member.member.entity.Member;
import com.ll.sb240118.domain.member.member.service.MemberService;
import com.ll.sb240118.global.rq.Rq.Rq;
import com.ll.sb240118.global.rsData.RsData;
import com.ll.sb240118.global.util.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
        private final String refreshToken;

        public LoginResponseBody(Member member, String accessToken, String refreshToken) {
            item = new MemberDto(member);
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
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
                60 * 10,
                Map.of(
                        "id", id.toString(),
                        "username", member.getUsername(),
                        "authorities", member.getAuthoritiesAsStrList()
                )
        );

        String refreshToken = JwtUtil.encode(
                60 * 60 * 24* 365,
                Map.of(
                        "id", id.toString(),
                        "username", member.getUsername()
                )
        );

        memberService.setRefreshToken(member, refreshToken);

        return RsData.of(
                "200",
                "로그인 성공",
                new LoginResponseBody(member, accessToken, refreshToken)
        );
    }

    @Getter
    @Setter
    public static class RefreshAccessTokenReqestBody{
        private String refreshToken;

    }
    @Getter
    public static class RefreshAccessTokenResponseBody{
        private final String accessToken;

        public RefreshAccessTokenResponseBody(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    @PostMapping("/refreshAccessToken")
    public RsData<RefreshAccessTokenResponseBody> refresh(
            @RequestBody RefreshAccessTokenReqestBody requestBody
    ) {
        String refreshToken = requestBody.getRefreshToken();

        Member member = memberService.findByRefreshToken(refreshToken).get();

        Long id = member.getId();
        String accessToken = JwtUtil.encode(
                60 * 10,
                Map.of(
                        "id", id.toString(),
                        "username", member.getUsername(),
                        "authorities", member.getAuthoritiesAsStrList()
                )
        );

        return RsData.of(
                "200",
                "엑세스 토큰 재발급 성공",
                new RefreshAccessTokenResponseBody(accessToken)
        );
    }

}