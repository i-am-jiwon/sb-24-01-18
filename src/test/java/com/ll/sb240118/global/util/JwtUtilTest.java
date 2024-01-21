package com.ll.sb240118.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTest {
    @Test
    @DisplayName("t1")
    void t1(){
        Map<String, String> data = Map.of("name", "홍길동", "age", "18");
        String jwtToken = JwtUtil.encode(data);

        System.out.println(jwtToken);

        assertThat(jwtToken).isNotNull();
    }
}
