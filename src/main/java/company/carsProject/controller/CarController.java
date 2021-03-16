package company.carsProject.controller;

import java.sql.Date;
import java.util.Calendar;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import company.carsProject.exceptions.ServiceException;
import company.carsProject.model.AdminAccount;
import company.carsProject.model.Car;
import company.carsProject.model.CarOwner;
import company.carsProject.model.Insurance;
import company.carsProject.model.UserAccount;
import company.carsProject.service.AccountService;
import company.carsProject.service.CarOwnerService;
import company.carsProject.service.CarService;
import company.carsProject.service.InsuranceService;

@RestController
@RequestMapping(path = "/car")

@Validated
public class CarController {

	@Autowired
	CarService carService;

	@Autowired
	CarOwnerService carOwnerService;

	@Autowired
	InsuranceService insuranceService;
	
	@Autowired
	AccountService accountService;

	@PostMapping(value = "addCarOwner")
	public ResponseEntity<CarOwner> addCarOwner(
			@NotEmpty(message = "Please, enter name") @RequestParam(value = "name") String name,
			@NotEmpty(message = "Please, enter egn") @RequestParam(value = "egn") String egn,
			@Nullable @NotNull(message = "Please, enter years of driving experience") @Min(value = 0, message = "Years must be greater than or equal to 0") @RequestParam(value = "yearsOfDrivingExperience") Integer yearsOfDrivingExperience) {

		CarOwner carOwner = new CarOwner();

		carOwner = carOwnerService.getCarOwnerByEgn(egn);
		if (carOwner != null) {
			throw new ServiceException("There is owner with this egn");
		}
		carOwner = carOwnerService.addCarOwner(name, egn, yearsOfDrivingExperience);

		return new ResponseEntity<CarOwner>(carOwner, HttpStatus.OK);
	}

	@PostMapping(value = "addCar")
	public ResponseEntity<Car> createCar(
			@NotEmpty(message = "Please, enter manufacturer") @RequestParam(value = "manufacturer") String manufacturer,
			@NotEmpty(message = "Please, enter model") @RequestParam(value = "model") String model,
			@NotEmpty(message = "Please, enter color") @RequestParam(value = "color") String color,
			@NotEmpty(message = "Please, enter license plate") @RequestParam(value = "licensePlate") String licensePlate,
			@Nullable @NotNull(message = "Please, enter horsepower") @Min(value = 1, message = "horsepower cannot be negative") @RequestParam(value = "horsepower") Integer horsepower,
			@NotEmpty(message = "Please, enter egn") @RequestParam(value = "egn") String egn) {

		CarOwner owner = carOwnerService.getCarOwnerByEgn(egn);
		if (owner == null)
			throw new ServiceException("Does not exist owner with this egn.");

		Car car = new Car();
		car = carService.addCar(manufacturer, model, color, licensePlate, horsepower, owner);

		return new ResponseEntity<Car>(car, HttpStatus.OK);
	}

	@PostMapping(value = "addInsurance")
	public ResponseEntity<Insurance> addInsurance(
			@Nullable @NotNull(message = "Please, enter insurance`s price") @Min(value = 0, message = "Price must be greater than or equal to 0") @RequestParam(value = "price") Double price,
			@NotEmpty(message = "Please, enter car`s license plate") @RequestParam(value = "licensePlate") String licensePlate) {

		Calendar calendar = Calendar.getInstance();

		Date startDate = new Date(calendar.getTime().getTime());

		calendar.add(Calendar.YEAR, 1);
		Date endDate = new Date(calendar.getTime().getTime());

		Car car = carService.getCarByLicensePlate(licensePlate);
		if (car == null) {
			throw new ServiceException("Does not exist car with this license plate");
		}

		Insurance insurance = insuranceService.getInsuranceByCar(car);
		if (insurance != null) {
			throw new ServiceException("The car has insurance. You can not add new insurance.");
		}
		insurance = insuranceService.addInsurance(startDate, endDate, price, car);

		return new ResponseEntity<Insurance>(insurance, HttpStatus.OK);

	}

	@GetMapping(path = "getOwner")
	public ResponseEntity<CarOwner> getCarOwner(@RequestParam(value = "egn") String egn) {
		CarOwner owner = carOwnerService.getCarOwnerByEgn(egn);
		if (owner == null)
			throw new ServiceException("Does not exist owner with this egn");

		return new ResponseEntity<CarOwner>(owner, HttpStatus.OK);
	}

	@PostMapping(value = "addUser")
	public ResponseEntity<UserAccount> addUser(
			@Email(message="Please, enter valid email")@NotEmpty(message = "Please, enter email") @RequestParam(value = "email") String email,
			@NotEmpty(message = "Please, enter password") @RequestParam(value = "password") String password,
			@NotEmpty(message = "Please, enter egn") @RequestParam(value = "egn") String egn) {
		
		CarOwner owner = carOwnerService.getCarOwnerByEgn(egn);
		if (owner == null)
			throw new ServiceException("Does not exist owner with this egn.");

		UserAccount account = new UserAccount();
		account = accountService.addUserAccount("user", email, password, owner);

		return new ResponseEntity<UserAccount>(account, HttpStatus.OK);
	}

	@PostMapping(value = "addAdmin")
	public ResponseEntity<AdminAccount> addAdmin(
			@Email(message="Please, enter valid email")@NotEmpty(message = "Please, enter email") @RequestParam(value = "email") String email,
			@NotEmpty(message = "Please, enter password") @RequestParam(value = "password") String password){
		
		AdminAccount account = new AdminAccount();
		account = accountService.addAdminAccount("admin", email, password);

		return new ResponseEntity<AdminAccount>(account, HttpStatus.OK);
	}
	
}
