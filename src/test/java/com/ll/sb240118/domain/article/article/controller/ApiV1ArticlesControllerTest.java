package com.ll.sb240118.domain.article.article.controller;


import com.ll.sb240118.domain.aritcle.article.controller.ApiV1ArticlesController;
import com.ll.sb240118.domain.aritcle.article.entity.Article;
import com.ll.sb240118.domain.aritcle.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ApiV1ArticlesControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ArticleService articleService;

    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.?\\d{0,7}";

    @Test
    @DisplayName("GET /api/v1/articles")
    void t1() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/api/v1/articles"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1ArticlesController.class))
                .andExpect(handler().methodName("getArticles"))
                .andExpect(jsonPath("$.data.items[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.items[0].createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.items[0].modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.items[0].authorId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.items[0].authorName", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].title", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].body", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/v1/articles/1")
    void t2() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/api/v1/articles/1"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1ArticlesController.class))
                .andExpect(handler().methodName("getArticle"))
                .andExpect(jsonPath("$.data.item.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.item.createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.authorId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.item.authorName", notNullValue()))
                .andExpect(jsonPath("$.data.item.title", notNullValue()))
                .andExpect(jsonPath("$.data.item.body", notNullValue()));
    }

    @Test
    @DisplayName("DELETE /api/v1/articles/1")
    void t3() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(delete("/api/v1/articles/1"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1ArticlesController.class))
                .andExpect(handler().methodName("removeArticle"))
                .andExpect(jsonPath("$.data.item.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.item.createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.authorId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.item.authorName", notNullValue()))
                .andExpect(jsonPath("$.data.item.title", notNullValue()))
                .andExpect(jsonPath("$.data.item.body", notNullValue()));

        Article article1 = articleService.findById(1L).orElse(null);

        assertThat(article1).isNull();
    }

    @Test
    @DisplayName("PUT /api/v1/articles/1")
    void t4() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        put("/api/v1/articles/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "제목1-수정",
                                            "body": "내용1-수정"
                                        }
                                        """)
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1ArticlesController.class))
                .andExpect(handler().methodName("modifyArticle"))
                .andExpect(jsonPath("$.data.item.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.item.createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.item.authorId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.item.authorName", notNullValue()))
                .andExpect(jsonPath("$.data.item.title", is("제목1-수정")))
                .andExpect(jsonPath("$.data.item.body", is("내용1-수정")));

    }
}
