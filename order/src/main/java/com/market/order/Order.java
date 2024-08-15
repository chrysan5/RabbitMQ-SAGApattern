package com.market.order;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Builder
@Data
@ToString
//@Entity 실전에서 쓸때는 db와 연결하기 위해 엔티티 어노테이션 추가 필요
public class Order {
    private UUID orderId;
    private String userId;
    private String orderStatus;
    private String errorType;

    public void cancelOrder(String receiveErrorType) { //실전에서는 enum 타입을 사용해야함
        orderStatus = "CANCEL";
        errorType = receiveErrorType;
    }
}
