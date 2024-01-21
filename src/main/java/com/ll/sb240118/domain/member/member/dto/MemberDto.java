package com.ll.sb240118.domain.member.member.dto;

import com.ll.sb240118.domain.member.member.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDto {
    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String username;
    private final String nickname;
    private final String apiKey;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.apiKey = member.getApiKey();
    }
}