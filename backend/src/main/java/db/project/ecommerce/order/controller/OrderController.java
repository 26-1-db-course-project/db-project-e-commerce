package db.project.ecommerce.order.controller;

import db.project.ecommerce.order.dto.request.CreateOrderRequest;
import db.project.ecommerce.order.dto.response.OrderDetailResponse;
import db.project.ecommerce.order.dto.response.OrderResponse;
import db.project.ecommerce.order.service.OrderReadService;
import db.project.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderReadService orderReadService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {

        // 서비스 호출
        String resultMessage = orderService.createPartialOrder(request);

        // 201 CREATED 상태 코드와 함께 프로시저 결과 메시지 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(resultMessage);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(
            @PathVariable Long orderId,
            @RequestParam Long memberId
    ) {
        OrderDetailResponse response = orderReadService.getOrderDetail(memberId, orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersList(
            @RequestParam Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate
    ) {
        List<OrderResponse> response = orderReadService.getOrdersByDateAfter(memberId, startDate);
        return ResponseEntity.ok(response);
    }
}