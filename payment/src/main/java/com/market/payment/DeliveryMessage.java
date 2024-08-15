package com.market.payment;

import lombok.*;

import java.util.UUID;

//DeliveryMessage는 다른 서비스와 같은 구조로 만들어준다
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryMessage {
    private UUID orderId;
    private UUID paymentId;

    private String userId;

    private Integer productId;
    private Integer productQuantity;

    private Integer payAmount;

    private String errorType;
}
