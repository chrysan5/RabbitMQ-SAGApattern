package com.market.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${message.queue.payment}")
    private String paymentQueue;

    @Value("${message.queue.err.order}")
    private String orderErrorQueue;

    public void reduceProductAmount(DeliveryMessage deliveryMessage) {

        Integer productId = deliveryMessage.getProductId();
        Integer productQuantity = deliveryMessage.getProductQuantity();

        if (productId != 1 || productQuantity > 1) {
            this.rollbackProduct(deliveryMessage);
            return;
        }

        //product 메시지가 정상적으로 메시지를 받았을 경우 payment로 다시 메시지를 전달해준다
        rabbitTemplate.convertAndSend(paymentQueue,deliveryMessage);
    }

    public void rollbackProduct(DeliveryMessage deliveryMessage){
        log.info("PRODUCT ROLLBACK!!!");
        if(!StringUtils.hasText(deliveryMessage.getErrorType())){
            deliveryMessage.setErrorType("PRODUCT ERROR");
        }
        rabbitTemplate.convertAndSend(orderErrorQueue, deliveryMessage);
    }
}
