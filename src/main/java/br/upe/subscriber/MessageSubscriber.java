package br.upe.subscriber;


import org.springframework.stereotype.Service;

@Service
public class MessageSubscriber {

    public void handleMessage(String message) {
        System.out.println("Mensagem recebida: " + message);

    }
}
