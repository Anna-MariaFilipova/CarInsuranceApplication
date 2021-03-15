package company.carsProject.repositories;
import org.springframework.data.repository.CrudRepository;

import company.carsProject.model.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

}
