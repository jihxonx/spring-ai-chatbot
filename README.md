# 🤖 Spring AI Study

> **Spring AI**를 활용한 AI 기능 학습 프로젝트입니다.  
> OpenAI GPT 모델을 기반으로 프롬프트 엔지니어링, 출력 변환, Advisor 패턴 등을 실습합니다.

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring AI 2.0.0-M
- OpenAI GPT-4o-mini 
- Gradle
- Lombok
- Thymeleaf
- WebFlux



## 📁 프로젝트 구조

```
src
├── main
│   ├── java/org/example/aistudy
│   │   ├── AistudyApplication.java
│   │   ├── application/ChatbotService.java
│   │   ├── infrastructure/ChatbotServiceImpl.java   # ChatClient 기반 챗봇 구현
│   │   └── presentation/ChatbotController.java
│   └── resources
│       ├── application.yaml
│       ├── templates/chatbot/index.html
│       └── static/chatbot/
│           ├── chat.js
│           └── style.css
└── test
    └── java/org/example/aistudy
        ├── advisor/        # Advisor 패턴 실습
        ├── converter/      # 출력 변환기 실습
        └── prompt/         # 프롬프트 엔지니어링 실습
```

---

## 📚 학습 내용

### 1. ChatClient 기반 챗봇 구현

`ChatModel` 저수준 API에서 `ChatClient` 고수준 API로 마이그레이션하는 과정을 실습했습니다.  
System 프롬프트로 **어린 왕자** 페르소나를 부여하고, ChatOptions를 통해 모델과 temperature를 설정합니다.

```java
chatClient.prompt()
    .options(ChatOptions.builder().model("gpt-4o-mini").temperature(0.7).build())
    .system(littlePrincePersona)
    .user(question)
    .call()
    .content();
```

---

### 2. 프롬프트 엔지니어링 (`prompt/`)

| 기법 | 클래스 | 설명 |
|------|--------|------|
| **Prompt Template** | `PromptTemplateTest` | `{변수}` 치환을 통한 재사용 가능한 프롬프트 생성 |
| **Few-Shot Prompting** | `FewShotPromptTest` | User/Assistant 예시를 제공해 JSON 감정 분석 수행 |
| **Self-Consistency** | `SelfConsistencyTest` | 동일 질문을 여러 번 요청 후 다수결로 일관된 답변 선택 |
| **Step-Back Prompting** | `StepBackPromptTest` | 추상적인 배경 지식을 먼저 확보한 뒤 구체적 답변 생성 |

#### Few-Shot 예시 (감정 분석)
```java
// 예시 1 (입력 → 출력)
new UserMessage("이 제품 정말 최고예요!"),
new AssistantMessage("{\"sentiment\": \"positive\", \"confidence\": 0.98}"),

// 예시 2
new UserMessage("품질이 떨어집니다."),
new AssistantMessage("{\"sentiment\": \"negative\", \"confidence\": 0.85}"),

// 실제 입력
new UserMessage(reviewText)
```

#### Self-Consistency 흐름
```
질문 5회 반복 요청 → [긍정, 부정, 긍정, 긍정, 부정] → 다수결 → "긍정"
```

#### Step-Back Prompting 흐름
```
원래 질문 → Step-back 질문 생성 → 배경 지식 확보 → 최종 답변 생성
"냉장고 문이 안 닫혀요" → "냉장고 도어 실링 원리는?" → 배경지식 → 구체적 해결책
```

---

### 3. 출력 변환기 (`converter/`)

`BeanOutputConverter`를 사용해 AI 응답을 Java 객체로 자동 변환하는 방법을 실습했습니다.

```java
// 저수준 방식
BeanOutputConverter<University> converter = new BeanOutputConverter<>(University.class);
String response = chatClient.prompt(prompt).call().content();
University result = converter.convert(response);

// 고수준 방식 (권장)
University result = chatClient.prompt()
    .user("인천의 대학교 이름 5개를 출력하세요.")
    .call()
    .entity(University.class);
```

---

### 4. Advisor 패턴 (`advisor/`)

`CallAdvisor` 인터페이스를 구현하여 AI 요청/응답 전후에 커스텀 로직을 삽입하는 인터셉터 패턴을 실습했습니다.

```java
// Advisor 등록
chatClient = builder
    .defaultAdvisors(new Advisor1(), new Advisor2())
    .build();
```

```
요청 →  [Advisor1 전처리] → [Advisor2 전처리] → AI 모델
응답 ← [Advisor1 후처리] ← [Advisor2 후처리] ← AI 모델
```

`getOrder()`를 통해 Advisor 실행 순서를 제어할 수 있으며, 숫자가 작을수록 먼저 적용됩니다.  
활용 예: 프롬프트 증강, 요청 필터링, 대화 내용 저장, 응답 로깅

---

## ⚙️ 실행 방법

### 1. 환경변수 설정

```bash
export OPENAI_API_KEY=your_openai_api_key_here
```

### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

### 3. 테스트 실행

```bash
./gradlew test
```

> ⚠️ 테스트 실행 시 실제 OpenAI API가 호출되므로 API 키와 비용에 유의하세요.

---

## 🔑 환경 설정

`src/main/resources/application.yaml`

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
```
