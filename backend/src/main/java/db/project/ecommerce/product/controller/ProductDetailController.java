package db.project.ecommerce.product.controller;

import db.project.ecommerce.product.dto.request.CreateProductDetailRequest;
import db.project.ecommerce.product.dto.request.UpdateProductRequest;
import db.project.ecommerce.product.dto.response.ProductDetailListResponse;
import db.project.ecommerce.product.dto.response.ProductDetailResponse;
import db.project.ecommerce.product.service.ProductDetailService;
import db.project.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-details")
public class ProductDetailController {
    private final ProductService productService;
    private final ProductDetailService productDetailService;

    //TODO: 상품 상세 생성
    @PostMapping
    public ResponseEntity<ProductDetailResponse> createProductDetail(@PathVariable("productId") Long productId,
                                                                     @RequestBody CreateProductDetailRequest request) {
        ProductDetailResponse response = productDetailService.createProductDetail(productId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //TODO: 상품 상세 조회 (목록: 상품안에 어떤 상세 요소들이 존재하는지)
    @GetMapping
    public ResponseEntity<ProductDetailListResponse> getProductDetailList (@PathVariable("productId")Long productId) {
        ProductDetailListResponse response = productService.getProductDetailList(productId);

        return ResponseEntity.ok(response);
    }

    //TODO: 상품 상세 개별 조회
    @GetMapping("/{productDetailId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail (@PathVariable("productDetailId")Long productDetailId) {
        ProductDetailResponse response = productDetailService.findProductDetail(productDetailId);

        return ResponseEntity.ok(response);
    }

    //TODO: 상품상세 수정
    @PatchMapping("{productDetailId}")
    public ResponseEntity<ProductDetailResponse> updateProductDetail(@PathVariable("productDetailId")Long productDetailId,
                                                                     @RequestBody UpdateProductRequest request) {
        ProductDetailResponse response = productDetailService.updateProductDetail(productDetailId, request);

        return ResponseEntity.ok(response);
    }
}
