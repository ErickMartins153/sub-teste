package br.upe.subscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
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

    public void subscribeToChannel(String canal, MessageListenerAdapter listenerAdapter, RedisMessageListenerContainer container) {
        ChannelTopic topic = new ChannelTopic(canal);
        container.addMessageListener(listenerAdapter, topic);
        System.out.println("Inscrito no canal: " + canal);
    }

    public void unsubscribeFromChannel(String channel, MessageListener listener, RedisMessageListenerContainer container) {
        container.removeMessageListener(listener, new ChannelTopic(channel));
    }

    public void psubscribeToPattern(String pattern, MessageListenerAdapter listenerAdapter, RedisMessageListenerContainer container) {
        PatternTopic patternTopic = new PatternTopic(pattern);
        container.addMessageListener(listenerAdapter, patternTopic);
        System.out.println("Inscrito no padrão: " + pattern);
    }

    public void punsubscribeFromPattern(String pattern, MessageListenerAdapter listenerAdapter, RedisMessageListenerContainer container) {
        container.removeMessageListener(listenerAdapter, new PatternTopic(pattern));
        System.out.println("Desinscrito do padrão: " + pattern);
    }
}
