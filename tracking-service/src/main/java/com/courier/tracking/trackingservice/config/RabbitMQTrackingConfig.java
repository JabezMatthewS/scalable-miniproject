package com.courier.tracking.trackingservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQTrackingConfig {
    public static final String EXCHANGE_NAME = "parcel.exchange";
    public static final String TRACKING_QUEUE = "parcel.tracking.events.queue";
    public static final String ROUTING_KEY_PATTERN = "parcel.#";

    @Bean
    public TopicExchange parcelExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue trackingQueue() {
        return QueueBuilder.durable(TRACKING_QUEUE).build();
    }

    @Bean
    public Binding trackingBinding() {
        return BindingBuilder.bind(trackingQueue()).to(parcelExchange()).with(ROUTING_KEY_PATTERN);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
