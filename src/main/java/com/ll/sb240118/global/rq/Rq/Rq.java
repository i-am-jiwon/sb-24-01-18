package com.ll.sb240118.global.rq.Rq;

import com.ll.sb240118.domain.member.member.entity.Member;
import com.ll.sb240118.global.security.SecurityUser;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManager entityManager;
    private Member member;
    private SecurityUser securityUser;


    public boolean isAdmin() {
        if (isLogout()) return false;

        return getSecurityUser()
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isLogin() {
        return getSecurityUser() != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public SecurityUser getSecurityUser() {
        if (securityUser == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) return null;
            Object principal = authentication.getPrincipal();
            if (principal == null) return null;
            securityUser = (SecurityUser) principal;
        }
        return securityUser;
    }

    public Member getMember() {
        if (isLogout()) return null;

        if (member == null) {
            member = entityManager.find(Member.class, getSecurityUser().getId());
        }
        return member;
    }


    public String getHeader(String name, String defaultValue) {
        String value = request.getHeader(name);

        if (value == null) {
            return defaultValue;
        }
        return value;

    }

    public void setAuthentication(SecurityUser user) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
