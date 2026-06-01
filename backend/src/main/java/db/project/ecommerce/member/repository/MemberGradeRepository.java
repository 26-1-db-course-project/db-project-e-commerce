package db.project.ecommerce.member.repository;

import db.project.ecommerce.member.domain.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Long> {
    Optional<MemberGrade> findByGradeName(String gradeName);
}
