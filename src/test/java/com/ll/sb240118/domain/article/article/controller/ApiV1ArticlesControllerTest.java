package com.ll.sb240118.domain.article.article.controller;


import com.ll.sb240118.domain.aritcle.article.controller.ApiV1ArticlesController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ApiV1ArticlesControllerTest {
    @Autowired
    private MockMvc mvc;

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
                .andExpect(jsonPath("$.data.items[0].id", is(10)))
                .andExpect(jsonPath("$.data.items[0].createDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.items[0].modifyDate", matchesPattern(DATE_PATTERN)))
                .andExpect(jsonPath("$.data.items[0].authorId", is(3)))
                .andExpect(jsonPath("$.data.items[0].authorName", is("user2")))
                .andExpect(jsonPath("$.data.items[0].title", is("제목10")))
                .andExpect(jsonPath("$.data.items[0].body", is("내용10")));
    }
}
