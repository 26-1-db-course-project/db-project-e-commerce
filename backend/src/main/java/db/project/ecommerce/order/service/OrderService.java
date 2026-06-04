package db.project.ecommerce.order.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.order.domain.OrderItem;
import db.project.ecommerce.order.domain.OrderStatus;
import db.project.ecommerce.order.domain.Orders;
import db.project.ecommerce.order.dto.request.CreateOrderRequest;
import db.project.ecommerce.order.repository.OrderRepository;
import db.project.ecommerce.order.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Transactional
    public String createPartialOrder(CreateOrderRequest request) {

        // List [101, 205] -> String "101,205" 로 변환
        String selectedItemsStr = request.selectedProductDetailIds().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // 프로시저 호출
        String result = orderRepository.callCheckoutPartialCart(
                request.memberId(),
                request.deliveryAddressId(),
                selectedItemsStr
        );

        // 프로시저 내부에서 보낸 에러 메시지(FAIL) 핸들링
        if (result != null && result.startsWith("FAIL")) {
            // 프로젝트 내에 만들어둔 예외 처리기가 있다면 그것을 사용하세요 (예: CustomException)
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        // 성공 메시지 반환 ("SUCCESS: 주문 완료...")
        return result;
    }

    @Transactional
    public void cancelOrder(Long memberId, Long orderId) {
        // 1. 주문 조회 및 본인 확인
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (!order.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED); // 내 주문이 아니면 error
        }

        // 2. 취소 상태(ID: 5) 엔티티 가져오기
        OrderStatus cancelStatus = orderStatusRepository.findById(5L)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        // 3. 주문에 딸린 모든 상품(OrderItem)을 돌면서 취소 처리
        for (OrderItem item : order.getOrderItems()) {
            // 이미 취소된 상품을 또 취소해서 재고가 무한 복사되는 것을 방지!
            if (item.getStatus().getId() != 5L) {
                item.changeStatus(cancelStatus);                  // 상태를 '주문취소'로 변경
                item.getProductDetail().addStock(item.getQuantity()); // 뺏어갔던 재고 롤백
                item.getProductDetail().decreaseSales(item.getQuantity()); // 판매량(통계)도 롤백
            }
        }

        // 4. 회원 누적 결제 금액에서 이번 주문 금액(totalPrice) 차감
        order.getMember().deductTotalAmount(order.getTotalPrice());
    }
}