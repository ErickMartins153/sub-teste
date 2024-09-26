package br.upe.subscriber;

import io.lettuce.core.RedisConnectionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.util.ErrorHandler;

import java.util.Scanner;

@Configuration
public class RedisConfig {
    Scanner scanner = new Scanner(System.in);


    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter) {
        System.out.println("Digite o canal que você deseja se inscrever: ");
        String canal = scanner.nextLine();

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, topic(canal));

        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(MessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "handleMessage");
    }

    public ChannelTopic topic(String canal) {
        return new ChannelTopic(canal);
    }
}
