package org.example.aistudy.presentation;

import org.example.aistudy.application.ChatbotService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/chatbot")
public class ChatbotController {

    private final ChatbotService service;

    public ChatbotController(ChatbotService service) {
        this.service = service;
    }

    @GetMapping
    public String index() {
        return "chatbot/index";
    }

    @ResponseBody
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    public Flux<String> message(@RequestBody String question) {
        return service.chat(question);
    }
}
