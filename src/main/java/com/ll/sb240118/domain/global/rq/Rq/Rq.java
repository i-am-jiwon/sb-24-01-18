package com.ll.sb240118.domain.global.rq.Rq;

import com.ll.sb240118.domain.member.member.entity.Member;
import com.ll.sb240118.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    public Member getMember(){
        if(member == null){
            member = memberService.findById(1L).get();
        }

        return member;
    }
}
