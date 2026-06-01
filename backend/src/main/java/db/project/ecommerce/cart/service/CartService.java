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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductDetailRepository productDetailRepository;

    @Transactional
    public Long createCartItem(Long memberId, CreateCartItemRequest request) {
        // memberId로 장바구니 조회
        Cart cart = findByMemberId(memberId);
        // 담으려는 상품 상세 조회
        ProductDetail productDetail = findByProductDetailId(request.getProductDetailId());
        // 이미 장바구니에 담겨있는 상품인지 확인
        Optional<CartItem> existingItem = cartItemRepository
                .findByCart_MemberIdAndProductDetail_Id(memberId, request.getProductDetailId());

        if (existingItem.isPresent()) {
            // 이미 존재하면 수량만 증가
            CartItem cartItem = existingItem.get();
            cartItem.addQuantity(request.getQuantity());
            return cartItem.getId();
        } else {
            // 없으면 새로 생성해서 저장
            CartItem newCartItem = request.toEntity(cart, productDetail);
            cartItemRepository.save(newCartItem);
            return newCartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public CartItemListResponse getCart(Long memberId) {
        // 장바구니 조회
        Cart cart = findByMemberId(memberId);
        // 장바구니에 속한 모든 아이템 조회
        List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
        // 각각의 CartItem을 CartItemResponse DTO로 변환
        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(item -> {
                    // TODO: 나중에 OptionDetail 테이블 조회해서 실제 "S / 화이트" 문자열을 만들어야 합니다.
                    String optionValue = "옵션 정보";
                    return CartItemResponse.from(item, optionValue);
                })
                .collect(Collectors.toList());

        // 장바구니 전체 DTO로 조립하여 반환
        return CartItemListResponse.of(cart, itemResponses);
    }

    @Transactional
    public void updateCartItemQuantity(Long memberId, Long productDetailId, UpdateCartItemRequest request) {
        // 장바구니 조회
        Cart cart = findByMemberId(memberId);
        // 수정하려는 장바구니 상품 조회
        CartItem cartItem = findByMemberProductDetailId(memberId, productDetailId);
        // 수량 업데이트
        cartItem.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void deleteCartItem(Long memberId, Long productDetailId) {
        // 삭제할 장바구니 아이템 조회
        CartItem cartItem = findByMemberProductDetailId(memberId, productDetailId);
        // 삭제 쿼리 실행
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
