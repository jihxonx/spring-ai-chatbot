package org.example.aistudy.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;

@Slf4j
public class Advisor2 implements CallAdvisor {

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        //전처리
        log.info("[Advisor2] - 전처리"); //프롬프트 증강, 필터링
        ChatClientResponse response = chain.nextCall(request);

        //후처리
        log.info("[Advisor2] - 후처리"); // 대화내용 저장, 응답 필터링
        return response;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int getOrder() { //Advisor 적용 순서, 숫자 작을 수록 먼저 적용
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
