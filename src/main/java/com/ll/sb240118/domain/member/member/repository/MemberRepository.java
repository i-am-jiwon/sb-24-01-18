package com.ll.sb240118.domain.member.member.repository;

import com.ll.sb240118.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}