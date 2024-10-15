package br.upe.subscriber;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSubscriber {

    private SimpMessagingTemplate messagingTemplate;
    public static List<String> messageList = new ArrayList<>();

    public MessageSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void handleMessage(String message) {
        messageList.add(message);
        System.out.println("Mensagem recebida: " + message);
        messagingTemplate.convertAndSend("/topic/messages", message);


    }

    public List<String> getMessageList() {
        return messageList;
    }
}
