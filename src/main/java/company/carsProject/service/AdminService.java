package company.carsProject.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import company.carsProject.model.Car;
import company.carsProject.model.CarOwner;
import company.carsProject.model.Insurance;
import company.carsProject.model.Message;

public interface AdminService {

	public CarOwner addCarOwner(String name, String egn, int yearsOfDrivingExperience);

	public CarOwner getCarOwnerByEgn(String egn);

	public Car addCar(String manufacturer, String model, String color, String licensePlate, int horsepower,
			CarOwner carOwner);

	public Car getCarByLicensePlate(String licensePlate);

	public Insurance addInsurance(Car car, int months);

	public Insurance getInsuranceByCar(Car car);
	
	public void terminateInsurance(Insurance insurance);
	
	public HashSet<Insurance> getInsuranceByCarOwner(CarOwner carOwner);
	

}
