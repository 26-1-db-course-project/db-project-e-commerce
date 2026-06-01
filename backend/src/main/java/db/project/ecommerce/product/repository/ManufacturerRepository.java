package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.additionalinfo.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

}
