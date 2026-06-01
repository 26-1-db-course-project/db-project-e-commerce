package db.project.ecommerce.order.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.order.domain.Orders;
import db.project.ecommerce.order.dto.response.OrderDetailResponse;
import db.project.ecommerce.order.dto.response.OrderResponse;
import db.project.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderReadService {

    private final OrderRepository orderRepository;

    // 1. 특정 주문 상세 조회
    public OrderDetailResponse getOrderDetail(Long memberId, Long orderId) {
        Orders order = orderRepository.findOrderDetailByIdAndMemberId(orderId, memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)); // 내 주문이 아니거나 없으면 에러

        // Entity -> DTO 변환
        List<OrderDetailResponse.OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(item -> new OrderDetailResponse.OrderItemDto(
                        item.getProductDetail().getId(),
                        item.getProductDetail().getProduct().getProductName(),
                        item.getQuantity(),
                        item.getOrderPrice(),
                        item.getStatus().getStatusName()
                )).toList();

        return new OrderDetailResponse(
                order.getId(),
                order.getOrderDate(),
                order.getShippingAddress(),
                order.getTotalPrice(),
                itemDtos
        );
    }

    // 2. 특정 기간 이후 주문 목록 조회
    public List<OrderResponse> getOrdersByDateAfter(Long memberId, LocalDateTime startDate) {
        List<Orders> orders = orderRepository.findByMember_IdAndOrderDateAfterOrderByOrderDateDesc(memberId, startDate);

        return orders.stream()
                .map(order -> {
                    // "트위드 자켓 외 1건" 같은 요약 제목 만들기
                    String title = "주문 상품 없음";
                    int itemCount = order.getOrderItems().size();
                    if (itemCount > 0) {
                        String firstItemName = order.getOrderItems().get(0).getProductDetail().getProduct().getProductName();
                        title = itemCount == 1 ? firstItemName : firstItemName + " 외 " + (itemCount - 1) + "건";
                    }

                    return new OrderResponse(
                            order.getId(),
                            order.getOrderDate(),
                            order.getShippingAddress(),
                            order.getTotalPrice(),
                            title
                    );
                }).toList();
    }
}