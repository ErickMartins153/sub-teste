package br.upe.subscriber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
public class MessageController {

    @Autowired
    private MessageSubscriber messageSubscriber;
    private final MessageListenerAdapter listenerAdapter;
    private RedisConfig redisConfig;

    @Autowired
    public MessageController(RedisConfig redisConfig, MessageListenerAdapter listenerAdapter) {
        this.redisConfig = redisConfig;
        this.listenerAdapter = listenerAdapter;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/subscribe")
    public String getMessages(Model model) {
        List<String> mensagens = messageSubscriber.getMessageList();
        model.addAttribute("mensagens", mensagens); 
        return "subscribe"; 
    }

    @PostMapping("/subscribe")
    public String canal(@RequestParam String canal) {
        redisConfig.subscribeToChannel(canal, listenerAdapter);
        System.out.println("Inscrito no canal: " + canal);
        return "redirect:/subscribe";
    }
}
