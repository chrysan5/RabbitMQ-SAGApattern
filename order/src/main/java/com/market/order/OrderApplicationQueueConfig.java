package com.market.order;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.product}")
    private String queueProduct;

    @Value("${message.queue.payment}")
    private String queuePayment;


    @Value("${message.err.exchange}")
    private String exchangeErr;

    @Value("${message.queue.err.order}")
    private String queueErrOrder;

    @Value("${message.queue.err.product}")
    private String queueErrProduct;

    //exchage 생성 코드----------------------------------
    @Bean public TopicExchange exchange() { return new TopicExchange(exchange); }

    //큐 생성 코드
    @Bean public Queue queueProduct() { return new Queue(queueProduct); }
    @Bean public Queue queuePayment() { return new Queue(queuePayment); }

    //바인딩 생성 코드
    @Bean public Binding bindingProduct() { return BindingBuilder.bind(queueProduct()).to(exchange()).with(queueProduct); }
    @Bean public Binding bindingPayment() { return BindingBuilder.bind(queuePayment()).to(exchange()).with(queuePayment); }

    //에러 exchage 생성 코드---------------------------------
    @Bean public TopicExchange exchangeErr() { return new TopicExchange(exchangeErr); }

    //에러 큐 생성 코드
    @Bean public Queue queueErrOrder() { return new Queue(queueErrOrder); }
    @Bean public Queue queueErrProduct() { return new Queue(queueErrProduct); }

    //에러 바인딩 생성 코드
    @Bean public Binding bindingErrOrder() { return BindingBuilder.bind(queueErrOrder()).to(exchangeErr()).with(queueErrOrder); }
    @Bean public Binding bindingErrProduct() { return BindingBuilder.bind(queueErrProduct()).to(exchangeErr()).with(queueErrProduct); }
}
