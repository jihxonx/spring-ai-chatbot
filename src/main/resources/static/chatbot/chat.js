document.addEventListener('DOMContentLoaded', () => {
    const chatForm = document.getElementById('chat-form');
    const userInput = document.getElementById('user-input');
    const chatWindow = document.getElementById('chat-window');
    const loadingIndicator = document.getElementById('loading');

    chatForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const question = userInput.value.trim();
        if (!question) return;

        // 1. 사용자 메시지 추가
        appendMessage('user', question);
        userInput.value = '';

        // 2. 로딩 표시 시작
        loadingIndicator.classList.remove('hidden');
        scrollToBottom();

        try {
            // 3. API 호출 (ChatbotController의 엔드포인트)
            const response = await fetch('/chatbot', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(question)
            });

            if (!response.ok) throw new Error('서버 응답 오류');

            const answer = await response.text();

            // 4. 봇 응답 추가
            appendMessage('bot', answer);
        } catch (error) {
            appendMessage('bot', '미안해, 지금은 소행성 통신이 원활하지 않아. "어른들은 정말 이상해."');
            console.error(error);
        } finally {
            // 5. 로딩 표시 종료
            loadingIndicator.classList.add('hidden');
            scrollToBottom();
        }
    });

    function appendMessage(sender, text) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', sender);
        messageDiv.innerText = text;
        chatWindow.appendChild(messageDiv);
        scrollToBottom();
    }

    function scrollToBottom() {
        chatWindow.scrollTop = chatWindow.scrollHeight;
    }
});