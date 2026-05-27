//package db.project.ecommerce.cart.service;
//
//import db.project.ecommerce.cart.domain.Cart;
//import db.project.ecommerce.cart.domain.CartItem;
//import db.project.ecommerce.cart.dto.request.CreateCartItemRequest;
//import db.project.ecommerce.cart.repository.CartItemRepository;
//import db.project.ecommerce.cart.repository.CartRepository;
//import db.project.ecommerce.global.exception.CustomException;
//import db.project.ecommerce.global.exception.ErrorCode;
//import db.project.ecommerce.member.domain.Member;
//import db.project.ecommerce.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class CartService {
//    private final CartRepository cartRepository;
//    private final CartItemRepository cartItemRepository;
//    private final MemberRepository memberRepository;
//
//    @Transactional
//    public Long createCartItem(Long memberId, CreateCartItemRequest request) {
//        Member customer = findByMemberId(memberId);
//        CartItem cartItem = request.toEntity(customer);
//    }
//
//    private Member findByMemberId(Long memberId) {
//        return memberRepository.findById(memberId)
//                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
//    }
//
//    private
//}
