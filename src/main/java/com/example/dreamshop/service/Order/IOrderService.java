package com.example.dreamshop.service.Order;

import com.example.dreamshop.dto.OrderDto;
import com.example.dreamshop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrderById(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
