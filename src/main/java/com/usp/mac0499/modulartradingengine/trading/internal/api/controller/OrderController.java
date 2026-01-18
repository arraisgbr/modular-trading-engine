package com.usp.mac0499.modulartradingengine.trading.internal.api.controller;

import com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.request.OrderRequest;
import com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.response.OrderResponse;
import com.usp.mac0499.modulartradingengine.trading.internal.api.mappers.OrderMapper;
import com.usp.mac0499.modulartradingengine.trading.internal.service.interfaces.ITradingServiceInternal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ITradingServiceInternal tradingService;

    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequestDto) {
        var orderRequest = orderMapper.toEntity(orderRequestDto);
        var order = tradingService.createOrder(orderRequest);
        var orderResponse = orderMapper.toResponse(order);
        var location = fromCurrentRequest().path("/{id}").buildAndExpand(orderResponse.id()).toUri();
        return ResponseEntity.created(location).body(orderResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> readOrder(@PathVariable UUID id) {
        var order = tradingService.readOrder(id);
        var orderResponse = orderMapper.toResponse(order);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> readOrders() {
        var orders = tradingService.readOrders();
        var ordersResponse = orders.stream().map(orderMapper::toResponse).toList();
        return ResponseEntity.ok(ordersResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID id) {
        tradingService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

}
