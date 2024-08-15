package com.market.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${message.queue.product}")
    private String productQueue;

    private final RabbitTemplate rabbitTemplate;

    private Map<UUID, Order> orderStore = new HashMap<>(); //현재는 메모리상에 넣지만 실제로 db에 넣는 코드 만들어보기

    public Order getOrder(UUID orderId) {
        return orderStore.get(orderId);
    }


    public Order createOrder(OrderEndpoint.OrderRequestDto orderRequestDto) {
        Order order = orderRequestDto.toOrder(); //uuid로 생성한 orderId, userId, order status가 들어있는 order 객체 생성

        DeliveryMessage deliveryMessage = orderRequestDto.toDeliveryMessage(order.getOrderId()); //***

        orderStore.put(order.getOrderId(), order); //orderStore에 orderId에 해당하는 order객체 넣어줌

        log.info("send Message : {}",deliveryMessage.toString());

        rabbitTemplate.convertAndSend(productQueue, deliveryMessage); //레빗mq를 통한 메시지 전송***

        return order;

    }

    public void rollbackOrder(DeliveryMessage message) {
        Order order = orderStore.get(message.getOrderId());
        order.cancelOrder(message.getErrorType());
        log.info(order.toString());

    }

}

