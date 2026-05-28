package db.project.ecommerce.cart.service;

import db.project.ecommerce.cart.domain.Cart;
import db.project.ecommerce.cart.domain.CartItem;
import db.project.ecommerce.cart.dto.request.CreateCartItemRequest;
import db.project.ecommerce.cart.repository.CartItemRepository;
import db.project.ecommerce.cart.repository.CartRepository;
import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.product.domain.ProductDetail;
import db.project.ecommerce.product.repository.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductDetailRepository productDetailRepository;

    @Transactional
    public Long createCartItem(Long memberId, CreateCartItemRequest request) {
        // memberId로 장바구니 조회
        Cart cart = cartRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
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


    // ==== 헬퍼 함수 ====
    private ProductDetail findByProductDetailId(Long productDetailId) {
        return productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
}
