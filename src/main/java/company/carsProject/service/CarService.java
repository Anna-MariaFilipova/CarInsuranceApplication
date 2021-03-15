package company.carsProject.service;

import company.carsProject.model.Car;
import company.carsProject.model.CarOwner;





public interface CarService {
	
	public Car addCar(String manufacturer, String model, String color,
			String licensePlate, int horsepower, CarOwner carOwner);
	
	public Car getCarByLicensePlate(String licensePlate);	
}
