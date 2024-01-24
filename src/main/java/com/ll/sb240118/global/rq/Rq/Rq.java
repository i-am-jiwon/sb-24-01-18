package com.ll.sb240118.global.rq.Rq;

import com.ll.sb240118.domain.member.member.entity.Member;
import com.ll.sb240118.domain.member.member.service.MemberService;
import com.ll.sb240118.global.security.SecurityUser;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final MemberService memberService;
    private Member member;
    private final EntityManager entityManager;

    public Member getMember() {
        if (member == null) {
            SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            long memberId = user.getId();

            member = entityManager.getReference(Member.class, memberId);
        }

        return member;
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }
}
