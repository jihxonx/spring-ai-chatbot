package org.example.aistudy.advisor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AdvisorTest {
    ChatClient chatClient;

    @BeforeEach
    void init(@Autowired ChatClient.Builder builder) {
        chatClient = builder
                .defaultAdvisors(
                        new Advisor1(),
                        new Advisor2()
                )
                .build();
    }

    @Test
    void test2() {
        String answer = chatClient.prompt()
                .user("스티브잡스의 명언 3가지를 알려줘")
                .call()
                .content();
        log.info("답변: {}",answer);
    }
}
