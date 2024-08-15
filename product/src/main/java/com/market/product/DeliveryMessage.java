package com.market.product;


import lombok.*;

import java.util.UUID;

//order 어플리케이션이 보내준 것과 동일하게 받아야 하므로 order 의 DeliveryMessage와 동일함
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
