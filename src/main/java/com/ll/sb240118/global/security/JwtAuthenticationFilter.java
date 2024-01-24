package com.ll.sb240118.global.security;

import com.ll.sb240118.domain.member.member.service.MemberService;
import com.ll.sb240118.global.rq.Rq.Rq;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final Rq rq;
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String apiKey = rq.getHeader("X-ApiKey", null);

        if (apiKey != null) {
            SecurityUser user = memberService.getUserFromApiKey(apiKey);
            rq.setAuthentication(user);

        }




        filterChain.doFilter(request, response);
    }
}