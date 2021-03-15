package company.carsProject.service;

import java.sql.Date;

import company.carsProject.model.Car;
import company.carsProject.model.Insurance;

public interface InsuranceService {
	
	public Insurance addInsurance(Date startDate, Date endDate, double price, Car car);

	public Insurance getInsuranceByCar(Car car);
}
