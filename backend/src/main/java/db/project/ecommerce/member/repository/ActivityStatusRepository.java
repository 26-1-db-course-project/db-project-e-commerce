package db.project.ecommerce.member.repository;

import db.project.ecommerce.member.domain.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityStatusRepository extends JpaRepository<ActivityStatus, Long> {
    Optional<ActivityStatus> findByStatusName(String statusName);
}
