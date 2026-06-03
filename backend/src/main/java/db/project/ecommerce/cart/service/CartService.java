package db.project.ecommerce.cart.service;

import db.project.ecommerce.cart.domain.Cart;
import db.project.ecommerce.cart.domain.CartItem;
import db.project.ecommerce.cart.dto.request.CreateCartItemRequest;
import db.project.ecommerce.cart.dto.request.UpdateCartItemRequest;
import db.project.ecommerce.cart.dto.response.CartItemListResponse;
import db.project.ecommerce.cart.dto.response.CartItemResponse;
import db.project.ecommerce.cart.repository.CartItemRepository;
import db.project.ecommerce.cart.repository.CartRepository;
import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.product.domain.ProductDetail;
import db.project.ecommerce.product.repository.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductDetailRepository productDetailRepository;

    // 장바구니 아이템 추가 (프로시저 사용)
    @Transactional
    public void createCartItem(Long memberId, CreateCartItemRequest request) {
        // DB 프로시저를 직접 호출
        cartItemRepository.callAddCart(
                memberId,
                request.getProductDetailId(),
                request.getQuantity()
        );
    }

    // 장바구니 상세 조회 (함수 사용)
    @Transactional(readOnly = true)
    public CartItemListResponse getCart(Long memberId) {
        // 장바구니 엔티티 조회
        Cart cart = findByMemberId(memberId);

        // 장바구니에 담긴 아이템 전체 조회
        List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);

        List<CartItemResponse> items = cartItems.stream()
                // 두 번째 파라미터로 들어갈 optionValue(옵션명)를 임시로 넣어줍니다.
                .map(item -> CartItemResponse.from(item, "기본 옵션"))
                .toList();

        // DB에서 Integer로 온 값을 Long으로 변환
        Integer dbTotalPrice = cartItemRepository.getCartTotalPrice(memberId);
        Long totalPrice = (dbTotalPrice != null) ? dbTotalPrice.longValue() : 0L;

        // 최종 응답 DTO 생성 후 반환
        return new CartItemListResponse(memberId, totalPrice, items);
    }

    @Transactional
    public void updateCartItemQuantity(Long memberId, Long productDetailId, UpdateCartItemRequest request) {
        CartItem cartItem = findByMemberProductDetailId(memberId, productDetailId);
        cartItem.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void deleteCartItem(Long memberId, Long productDetailId) {
        CartItem cartItem = findByMemberProductDetailId(memberId, productDetailId);
        cartItemRepository.delete(cartItem);
    }


    // ==== 헬퍼 함수 ====
    private ProductDetail findByProductDetailId(Long productDetailId) {
        return productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    private Cart findByMemberId(Long memberId) {
        return cartRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    private CartItem findByMemberProductDetailId(Long memberId, Long productDetailId) {
        return cartItemRepository.findByCart_MemberIdAndProductDetail_Id(memberId, productDetailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
}