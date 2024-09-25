package br.upe.subscriber;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class MessageSubscriber {

    @PostConstruct
    public void init() {
        System.out.println("Subscriber iniciado e aguardando mensagens...");
    }

    public void handleMessage(String message) {

        System.out.println("Mensagem recebida: " + message);
    }
}
