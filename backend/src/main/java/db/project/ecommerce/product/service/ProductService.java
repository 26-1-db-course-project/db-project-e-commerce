package db.project.ecommerce.product.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.product.domain.Category;
import db.project.ecommerce.product.domain.Manufacturer;
import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.dto.request.CreateProductRequest;
import db.project.ecommerce.product.dto.request.SearchProduct;
import db.project.ecommerce.product.dto.request.UpdateProductPrice;
import db.project.ecommerce.product.dto.response.ProductListResponse;
import db.project.ecommerce.product.dto.response.ProductResponse;
import db.project.ecommerce.product.repository.CategoryRepository;
import db.project.ecommerce.product.repository.ManufacturerRepository;
import db.project.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ManufacturerRepository manufacturerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    //TODO: 상품 생성
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        Manufacturer manufacturer = getManufacturer(request.getManufacturerId());
        Category category = getCategory(request.getCategoryId());

        Product newProduct = Product.builder()
                .manufacturer(manufacturer)
                .name(request.getProductName())
                .price(request.getPrice())
                .category(category)
                .imageUrl(request.getImageUrl())
                .build();

        productRepository.save(newProduct);

        return ProductResponse.of(newProduct);
    }

    //TODO: 상품 목록조회 (일반)
    @Transactional(readOnly = true)
    public ProductListResponse getProductList(String sortBy) {
        Sort sort = createSort(sortBy);
        List<Product> productList = productRepository.findAll(sort);

        return ProductListResponse.of(productList);
    }

    //TODO: 상품 목록조회 (카테고리)
    @Transactional(readOnly = true)
    public ProductListResponse getProductListByCategory(Long categoryId, String sortBy) {
        Category category = getCategory(categoryId);
        Sort sort = createSort(sortBy);
        List<Product> productList = productRepository.findAllByCategory(category, sort);

        return ProductListResponse.of(productList);
    }

    //TODO: 상품 개별조회
    @Transactional(readOnly = true)
    public ProductResponse getProductDetail(Long productId) {
        Product product = getProduct(productId);

        return ProductResponse.of(product);
    }

    //TODO: 상품 가격 수정
    @Transactional
    public void updateProductPrice(Long productId, UpdateProductPrice request) {
        Product product = getProduct(productId);
        product.updatePrice(request.getPrice());
    }

    //TODO: 상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }

    //TODO: 상품 검색
    @Transactional(readOnly = true)
    public ProductListResponse searchProduct(SearchProduct request, String sortBy) {
        Sort sort = createSort(sortBy);
        List<Product> productList = productRepository.searchByName(request.getKeyword(),sort);

        return ProductListResponse.of(productList);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
    private Manufacturer getManufacturer(Long manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    private Category getCategory (Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }


    //AI 도움.
    private Sort createSort (String sortBy) {
        try {
            String[] sortParams = sortBy.split(",");
            String property = sortParams[0];
            String direction = sortParams[1];

            if (direction.equalsIgnoreCase("asc")) {
                return Sort.by(Sort.Direction.ASC, property);
            } else {
                return Sort.by(Sort.Direction.DESC, property);
            }
        } catch (Exception e) {
            return Sort.by(Sort.Direction.DESC, "productName");
        }
    }


}
