package db.project.ecommerce.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member_grade")
public class MemberGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long id;

    @Column(name = "grade_name")
    private String gradeName;

    @Column(name = "total_purchase_amount")
    private BigDecimal totalPurchaseAmount;

    @Column(name = "shipping_fee")
    private int shippingFee;
}
