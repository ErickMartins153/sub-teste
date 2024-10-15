package br.upe.subscriber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
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
    private RedisMessageListenerContainer redisContainer;

    @Autowired
    public MessageController(RedisConfig redisConfig, MessageListenerAdapter listenerAdapter) {
        this.redisConfig = redisConfig;
        this.listenerAdapter = listenerAdapter;
    }

    @GetMapping("/subscribe")
    public String showSubscribePage() {
        return "subscribe";
    }

    @GetMapping("/messages")
    public String getMessages(Model model,  @ModelAttribute("canal") String canal) {
        List<String> mensagens = messageSubscriber.getMessageList();
        model.addAttribute("mensagens", mensagens);
        model.addAttribute("canal", canal);
        return "messages";
    }

    @PostMapping("/subscribe")
    public String subscribeToChannel(@RequestParam String canal) {
        if (canal.contains("*")) {
            redisConfig.psubscribeToPattern(canal, listenerAdapter, redisContainer);
            System.out.println("Inscrito em todos os canais que correspondem ao padrão: " + canal);
        } else {
            redisConfig.subscribeToChannel(canal, listenerAdapter, redisContainer);
            System.out.println("Inscrito no canal: " + canal);
        }
        return "redirect:/messages?canal=" + canal;
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestParam String canal) {
        if (canal.contains("*")) {
            redisConfig.punsubscribeFromPattern(canal, listenerAdapter, redisContainer);
            System.out.println("Desinscrito do padrão: " + canal);
        } else {
            redisConfig.unsubscribeFromChannel(canal, listenerAdapter, redisContainer);
            System.out.println("Desinscrito do canal: " + canal);
        }
        return "redirect:/subscribe";
    }

}
