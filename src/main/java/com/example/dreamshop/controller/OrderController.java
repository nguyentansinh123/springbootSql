package com.example.dreamshop.controller;

import com.example.dreamshop.dto.OrderDto;
import com.example.dreamshop.exceptions.ResourceNotFoundException;
import com.example.dreamshop.model.Order;
import com.example.dreamshop.response.ApiResponse;
import com.example.dreamshop.service.Order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder (@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Item Order Success", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured", e.getMessage()));
        }

    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Oops", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Oops", e.getMessage()));
        }
    }
}
