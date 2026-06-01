package db.project.ecommerce.product.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.product.domain.option.OptionDetail;
import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.domain.ProductDetail;
import db.project.ecommerce.product.domain.ProductOption;
import db.project.ecommerce.product.dto.request.CreateProductDetailRequest;
import db.project.ecommerce.product.dto.request.UpdateProductRequest;
import db.project.ecommerce.product.dto.response.ProductDetailResponse;
import db.project.ecommerce.product.repository.OptionDetailRepository;
import db.project.ecommerce.product.repository.ProductDetailRepository;
import db.project.ecommerce.product.repository.ProductOptionRepository;
import db.project.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OptionDetailRepository optionDetailRepository;

    //TODO: 상품 상세 생성
    @Transactional
    public ProductDetailResponse createProductDetail(Long productId, CreateProductDetailRequest request) {
        Product product = getProduct(productId);
        ProductDetail newProductDetail = ProductDetail.builder()
                .product(product)
                .stockQuantity(request.getStockQuantity())
                .surcharge(request.getSurcharge())
                .sales((long) 0)
                .imageUrl(request.getImageUrl())
                .build();
        productDetailRepository.save(newProductDetail);

        for (Long optionDetailId : request.getOptionDetailId()) {
            OptionDetail optionDetail = findOptionDetail(optionDetailId);
            ProductOption newProductOption = ProductOption.builder()
                    .optionDetail(optionDetail)
                    .productDetail(newProductDetail)
                    .build();
            productOptionRepository.save(newProductOption);
        }

        return ProductDetailResponse.of(newProductDetail);
    }

    //TODO: 상품 상세 단건 조회
    @Transactional(readOnly = true)
    public ProductDetailResponse findProductDetail(Long productDetailId) {
        ProductDetail productDetail = getProductDetail(productDetailId);

        return ProductDetailResponse.of(productDetail);
    }

    @Transactional
    public ProductDetailResponse updateProductDetail(Long productDetailId, UpdateProductRequest request) {
        ProductDetail productDetail = getProductDetail(productDetailId);
        productDetail.updateProductDetail(request.getStockQuantity(), request.getSurcharge(),
                request.getSales(),request.getImageUrl());

        System.out.println(">>> " + request.getStockQuantity());
        return ProductDetailResponse.of(productDetail);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public ProductDetail getProductDetail(Long productDetailId) {
        return productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public OptionDetail findOptionDetail(Long optionDetailId) {
        return optionDetailRepository.findById(optionDetailId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }


}
