package db.project.ecommerce.product.controller;

import db.project.ecommerce.product.dto.request.CreateProductRequest;
import db.project.ecommerce.product.dto.request.SearchProduct;
import db.project.ecommerce.product.dto.request.UpdateProductPrice;
import db.project.ecommerce.product.dto.response.ProductListResponse;
import db.project.ecommerce.product.dto.response.ProductOptionGroupResponse;
import db.project.ecommerce.product.dto.response.ProductResponse;
import db.project.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    //TODO: 상품 생성
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //TODO: 상품 목록 조회 (가격순 정렬)
    @GetMapping
    public ResponseEntity<ProductListResponse> getProductList(@RequestParam(defaultValue = "productName,desc") String sortBy) {
        ProductListResponse response = productService.getProductList(sortBy);

        return ResponseEntity.ok(response);
    }

    //TODO: 카테고리별 상품 목록 조회 (가격순 정렬)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ProductListResponse> getProductListByCategory(@PathVariable("categoryId") Long categoryId,
                                                                        @RequestParam(defaultValue = "productName,desc") String sortBy) {
        ProductListResponse response = productService.getProductListByCategory(categoryId, sortBy);

        return ResponseEntity.ok(response);
    }

    //TODO: 상품 개별 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable("productId") Long productId) {
        ProductResponse response = productService.getProductDetail(productId);

        return ResponseEntity.ok(response);
    }

    //TODO: 상품 검색
    @GetMapping("/search")
    public ResponseEntity<ProductListResponse> searchProduct(@RequestBody SearchProduct request,
                                                         @RequestParam(defaultValue = "productName,desc") String sortBy) {
        ProductListResponse response = productService.searchProduct(request, sortBy);

        return ResponseEntity.ok(response);

    }

    //TODO: 상품 가격 업데이트
    @PatchMapping("/{productId}")
    public ResponseEntity<Void> updateProductPrice(@PathVariable("productId") Long productId,
                                                   @RequestBody UpdateProductPrice request) {
        productService.updateProductPrice(productId, request);

        return ResponseEntity.ok().build();
    }

    //TODO: 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok().build();
    }

    //TODO: 상품 옵션 그룹 조회 (옵션 타입별 선택 가능한 값 목록)
    @GetMapping("/{productId}/options")
    public ResponseEntity<ProductOptionGroupResponse> getProductOptions(@PathVariable("productId") Long productId) {
        ProductOptionGroupResponse response = productService.getProductOptions(productId);

        return ResponseEntity.ok(response);
    }
}
