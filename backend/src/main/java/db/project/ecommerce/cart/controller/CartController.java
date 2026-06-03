package db.project.ecommerce.cart.controller;

import db.project.ecommerce.cart.dto.request.CreateCartItemRequest;
import db.project.ecommerce.cart.dto.request.UpdateCartItemRequest;
import db.project.ecommerce.cart.dto.response.CartItemListResponse;
import db.project.ecommerce.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts/{memberId}")
public class CartController {
    private final CartService cartService;

    // 장바구니 상품 추가
    @PostMapping("/items")
    public ResponseEntity<Void> createCartItem(@PathVariable("memberId") Long memberId,
                                               @Valid @RequestBody CreateCartItemRequest request) {
        cartService.createCartItem(memberId, request);

        // 장바구니 전체 조회 주소를 Location 헤더에 담아 201 Created 반환
        return ResponseEntity.created(URI.create("/carts/" + memberId)).build();
    }

    // 장바구니 상세 조회
    @GetMapping
    public ResponseEntity<CartItemListResponse> getCart(@PathVariable("memberId") Long memberId) {
        CartItemListResponse response = cartService.getCart(memberId);
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 수량 수정
    @PatchMapping("/items/{productDetailId}")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable("memberId") Long memberId,
                                                       @PathVariable("productDetailId") Long productDetailId,
                                                       @Valid @RequestBody UpdateCartItemRequest request) {
        cartService.updateCartItemQuantity(memberId, productDetailId, request);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{productDetailId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable("memberId") Long memberId,
                                               @PathVariable("productDetailId") Long productDetailId) {
        cartService.deleteCartItem(memberId, productDetailId);
        return ResponseEntity.noContent().build();
    }
}