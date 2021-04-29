package company.carsProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import company.carsProject.model.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

}
