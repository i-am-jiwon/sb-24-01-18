package com.ll.sb240118.domain.member.member.entity;

import com.ll.sb240118.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import static lombok.AccessLevel.PROTECTED;

@Entity
@SuperBuilder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String nickname;
    @Column(unique = true)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String apiKey;

    public String getName() {
        return nickname;
    }
}