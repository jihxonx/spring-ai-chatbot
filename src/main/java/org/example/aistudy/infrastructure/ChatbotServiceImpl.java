package org.example.aistudy.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.example.aistudy.application.ChatbotService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ChatbotServiceImpl implements ChatbotService {

    //    private final ChatModel chatModel;

    //    public ChatbotServiceImpl(ChatModel chatModel) {
//        this.chatModel = chatModel;
//    }

    private final ChatClient chatClient;

    public ChatbotServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


//    @Override
//    public String chat(String question) {
//
//        String littlePricePersona = """
//            당신은 생텍쥐페리의 '어린 왕자'입니다. 다음 특성을 따라주세요:
//            1. 순수한 관점으로 세상을 바라봅니다.
//            2. "어째서?"라는 질문을 자주 하며 호기심이 많습니다.
//            3. 철학적 통찰을 단순하게 표현합니다.
//            4. "어른들은 참 이상해요"라는 표현을 씁니다.
//            5. B-612 소행성에서 왔으며 장미와의 관계를 언급합니다.
//            6. 여우의 "길들임"과 "책임"에 대한 교훈을 중요시합니다.
//            7. "중요한 것은 눈에 보이지 않아"라는 문장을 사용합니다.
//            8. 공손하고 친절한 말투를 사용합니다.
//            9. 비유와 은유로 복잡한 개념을 설명합니다.
//
//            항상 간결하게 답변하세요. 길어야 2-3문장으로 응답하고, 어린 왕자의 순수함과 지혜를 담아내세요.
//            복잡한 주제도 본질적으로 단순화하여 설명하세요.""";
//
//        SystemMessage systemMessage = new SystemMessage(littlePricePersona);
//        UserMessage userMessage = new UserMessage(question);
//
//        Prompt prompt = Prompt.builder()
//                .chatOptions(ChatOptions.builder()
//                        .model("gpt-4o-mini")
//                        .temperature(0.7)
//                        .build())
//                .messages(systemMessage, userMessage)
//                .build();
//
//        AssistantMessage assistantMessage = chatModel.call(prompt)
//                .getResult()
//                .getOutput();
//
//        // 2.0.0-M2 -> gpt-5-mini
//
//        return assistantMessage.getText();
//    }

    public String chat(String question) {

        String littlePricePersona = """
            당신은 생텍쥐페리의 '어린 왕자'입니다. 다음 특성을 따라주세요:
            1. 순수한 관점으로 세상을 바라봅니다.
            2. "어째서?"라는 질문을 자주 하며 호기심이 많습니다.
            3. 철학적 통찰을 단순하게 표현합니다.
            4. "어른들은 참 이상해요"라는 표현을 씁니다.
            5. B-612 소행성에서 왔으며 장미와의 관계를 언급합니다.
            6. 여우의 "길들임"과 "책임"에 대한 교훈을 중요시합니다.
            7. "중요한 것은 눈에 보이지 않아"라는 문장을 사용합니다.
            8. 공손하고 친절한 말투를 사용합니다.
            9. 비유와 은유로 복잡한 개념을 설명합니다.""";

        return chatClient.prompt()
                .options(ChatOptions.builder()
                        .model("gpt-4o-mini")
                        .temperature(0.7)
                        .build())
                .system(littlePricePersona)
                .user(question)
                .call()
                .content();
    }
}
