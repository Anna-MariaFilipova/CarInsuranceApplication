package company.carsProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import company.carsProject.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
