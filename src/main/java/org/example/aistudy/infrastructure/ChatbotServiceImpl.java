package org.example.aistudy.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.example.aistudy.application.ChatbotService;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ChatbotServiceImpl implements ChatbotService {

    private final ChatModel chatModel;

    public ChatbotServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public Flux<String> chat(String question) {
//        UserMessage userMessage = new UserMessage(question);
//        Prompt prompt = new Prompt(userMessage);
        Flux<String> answer = chatModel.stream(question);

        return answer;
    }
}
