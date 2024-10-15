package br.upe.subscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
public class RedisConfig {
    private RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    private String canal;


    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter) {
        
        container.setConnectionFactory(connectionFactory);

        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(MessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "handleMessage");
    }
    

    public ChannelTopic topic(String canal) {
        return new ChannelTopic(canal);
    }

    public void subscribeToChannel(String canal, MessageListenerAdapter listenerAdapter) {
        ChannelTopic topic = new ChannelTopic(canal);
        container.addMessageListener(listenerAdapter, topic);
        System.out.println("Inscrito no canal: " + canal);
    }
    
}
