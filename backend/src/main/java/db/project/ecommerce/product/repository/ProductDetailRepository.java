package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.domain.ProductDetail;
import db.project.ecommerce.statistic.dto.projection.CategorySalesItemProjection;
import db.project.ecommerce.statistic.dto.projection.ProductSalesItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Optional<ProductDetail> findByProductAndId(Product product, Long productDetailId);

    @Query("""
            SELECT DISTINCT pd FROM ProductDetail pd
            LEFT JOIN FETCH pd.productOptions po
            LEFT JOIN FETCH po.optionDetail od
            LEFT JOIN FETCH od.optionType
            WHERE pd.product = :product
            """)
    List<ProductDetail> findAllByProductWithOptions(@Param("product") Product product); //AI 작성

    @Query(value = """
        SELECT  RANK() OVER (ORDER BY SUM(pd.sales) DESC) AS `rank`,
                p.product_id AS productId,
                p.product_name AS productName,
                c.category_name AS categoryName,
                SUM(pd.sales) AS soldQuantity,
                SUM(pd.sales * p.price) AS revenue
        FROM product p JOIN
             product_detail pd ON p.product_id = pd.product_id JOIN
             category c ON p.category_id = c.category_id
        GROUP BY p.product_id, p.product_name, c.category_name
        ORDER BY `rank`
    """, nativeQuery = true)
    List<ProductSalesItemProjection> getProductSales();

    @Query(value = """
        SELECT  c.category_name AS categoryName,
                COALESCE(SUM(oi.quantity), 0) AS soldQuantity,
                COALESCE(SUM(oi.quantity * (p.price+pd.surcharge)),0) AS revenue
        FROM category c JOIN
             product p ON c.category_id = p.category_id JOIN
             product_detail pd ON p.product_id = pd.product_id JOIN
             order_item oi ON p.product_id = oi.product_id
        GROUP BY c.category_id, c.category_name
        ORDER BY revenue DESC
    """, nativeQuery = true)
    List<CategorySalesItemProjection> getCategorySales();
}