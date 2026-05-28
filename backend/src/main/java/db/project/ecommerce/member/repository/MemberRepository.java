package db.project.ecommerce.member.repository;

import db.project.ecommerce.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
