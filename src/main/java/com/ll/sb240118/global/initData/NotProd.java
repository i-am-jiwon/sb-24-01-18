package com.ll.sb240118.global.initData;


import com.ll.sb240118.domain.aritcle.article.entity.Article;
import com.ll.sb240118.domain.aritcle.article.service.ArticleService;
import com.ll.sb240118.domain.member.member.entity.Member;
import com.ll.sb240118.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class NotProd {

    @Autowired
    @Lazy
    private NotProd self;

    private final MemberService memberService;
    private final ArticleService articleService;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if(memberService.count() > 0) return;

        Member member1 = memberService.join("admin", "1234", "admin@test.com", "관리자").getData();

        Article article1 = articleService.write(member1, "제목1", "내용1").getData();
    }
}
