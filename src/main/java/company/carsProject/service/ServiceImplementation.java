package company.carsProject.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.carsProject.model.Car;
import company.carsProject.model.CarOwner;
import company.carsProject.model.Insurance;
import company.carsProject.repositories.CarOwnerRepository;
import company.carsProject.repositories.CarRepository;
import company.carsProject.repositories.InsuranceRepository;

@Service
public class ServiceImplementation implements CarService, CarOwnerService, InsuranceService {

	@Autowired
	private CarRepository carRepository;
	@Autowired
	private CarOwnerRepository carOwnerRepository;
	@Autowired
	private InsuranceRepository insuranceRepository;

	/**
	 * This method is used to add car`s insurance to the database.
	 * 
	 * @param startDate      This is the date that car`s insurance was created.
	 * @param expirationDate This is the date that insurance coverage ends.
	 * @param price          This is the price that car`s insurance costs.
	 * @param car            This is the car for which the insurance is valid.
	 * @return Insurance This returns insurance which is created and saved to the
	 *         database.
	 */
	@Override
	public Insurance addInsurance(Date startDate, Date expirationDate, double price, Car car) {
		Insurance insurance = new Insurance(startDate, expirationDate, price, car);
		insuranceRepository.save(insurance);
		return insurance;
	}

	/**
	 * This method is used to get car which license plate equals to the searched
	 * one.
	 * 
	 * @param licensePlate This is searched license plate.
	 * @return Car This return the car that is stored in the database with the given
	 *         license plate.
	 * @return null This return null if in the database is not stored car with the
	 *         given license plate.
	 */
	@Override
	public Car getCarByLicensePlate(String licensePlate) {
		List<Car> cars = (List<Car>) carRepository.findAll();
		if (cars == null) {
			return null;
		} else {
			for (Car car : cars) {
				if (car.getLicensePlate().equals(licensePlate)) {
					return car;
				}
			}
		}
		return null;
	}

	/**
	 * This method get car`s insurance.
	 * 
	 * @param car This is the searched car .
	 * @return Insurance This return the insurance that is stored in the database
	 *         with the given car.
	 * @return null This return null if in the database is not stored insurance for
	 *         the searched car.
	 * 
	 */
	@Override
	public Insurance getInsuranceByCar(Car car) {
		List<Insurance> insurances = insuranceRepository.findAll();
		for (Insurance insurance : insurances) {
			if (insurance.getCar().getLicensePlate().equals(car.getLicensePlate())) {
				return insurance;
			}
		}
		return null;
	}

	/**
	 * This method creates car and stores the created car to the database.
	 * 
	 * @param manufacturer This is car`s manufacturer.
	 * @param model        This is car`s model.
	 * @param color        This is car`color.
	 * @param licensePlate This is car`s license plate.
	 * @param horsepower   This is car`s horsepower.
	 * @param carOwner     This is car`s car owner. return Car This returns the car
	 *                     that is created and stored to the database.
	 */
	@Override
	public Car addCar(String manufacturer, String model, String color, String licensePlate, int horsepower,
			CarOwner carOwner) {
		Car car = new Car(manufacturer, model, color, licensePlate, horsepower, carOwner);
		carRepository.save(car);
		return car;
	}

	/**
	 * This method creates car owner and stored the created car owner to the
	 * database.
	 * 
	 * @param name                     This is the name of car owner.
	 * @param egn                      This is the egn of the car owner.
	 * @param yearsOfDrivingExperience This is the years car owner has a driver`s
	 *                                 license.
	 * @return Car This returns car that is created and stored to the database.
	 */
	@Override
	public CarOwner addCarOwner(String name, String egn, int yearsOfDrivingExperience) {
		CarOwner carOwner = new CarOwner(name, egn, yearsOfDrivingExperience);
		carOwnerRepository.save(carOwner);
		return carOwner;
	}

	/**
	 * This method is used to get car owner which egn equals to searched one.
	 * 
	 * @param egn This is the egn of the car owner.
	 * @return CarOwner This returns car owner that is stored to the database with
	 *         searched egn.
	 * @return null This return null if in the database there is not car owners or
	 *         does not exist car owner with searched egn.
	 */

	@Override
	public CarOwner getCarOwnerByEgn(String egn) {
		List<CarOwner> carOwners = carOwnerRepository.findAll();
		if (carOwners == null) {
			return null;
		} else {
			for (CarOwner owner : carOwners) {
				if (owner.getEgn().equals(egn)) {
					return owner;
				}
			}
		}
		return null;
	}

}
