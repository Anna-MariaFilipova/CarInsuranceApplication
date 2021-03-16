package company.carsProject.service;


import company.carsProject.model.UserAccount;
import company.carsProject.model.AdminAccount;
import company.carsProject.model.CarOwner;

public interface AccountService {

	public UserAccount addUserAccount(String role, String email, String password, CarOwner carOwner);

	public AdminAccount addAdminAccount(String role, String email, String password);
}
