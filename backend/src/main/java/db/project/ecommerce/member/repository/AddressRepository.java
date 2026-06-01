package db.project.ecommerce.member.repository;

import db.project.ecommerce.member.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
