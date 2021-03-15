package company.carsProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import company.carsProject.model.CarOwner;

public interface CarOwnerRepository extends JpaRepository<CarOwner,Long> {

	

}
