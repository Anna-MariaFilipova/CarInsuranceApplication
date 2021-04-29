package company.carsProject.service;


import company.carsProject.model.CarOwner;

public interface CarOwnerService {

	public CarOwner addCarOwner(String name, String egn, int yearsOfDrivingExperience);
	
	public CarOwner getCarOwnerByEgn(String egn); 
	
	
}
