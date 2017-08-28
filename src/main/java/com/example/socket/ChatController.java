package com.example.socket;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.domain.Message;
import com.example.domain.MessagingError;

@Controller
public class ChatController {

    @MessageMapping("/message") // エンドポイントの指定
    @SendTo("/topic/message") // メッセージの宛先を指定
    public Message greeting(Message message) {
		return message;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors") // 送信者のみを宛先にする
    public MessagingError handleException(Throwable exception) {
        MessagingError error = new MessagingError();
        error.setMessage(exception.getMessage());
        return error;
    }
}