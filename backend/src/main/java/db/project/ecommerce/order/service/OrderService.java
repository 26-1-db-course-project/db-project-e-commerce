package db.project.ecommerce.order.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.order.dto.request.CreateOrderRequest;
import db.project.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

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
}