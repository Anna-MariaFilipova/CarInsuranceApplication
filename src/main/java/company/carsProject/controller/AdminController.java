package company.carsProject.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import company.carsProject.exceptions.DublicateException;
import company.carsProject.exceptions.NotFoundException;
import company.carsProject.model.CarOwner;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import company.carsProject.model.Car;
import company.carsProject.model.Insurance;
import company.carsProject.model.Message;
import company.carsProject.service.AdminService;

@RestController
@RequestMapping(path = "/car/admin")

@Validated
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping(value = "addCarOwner")
    public ResponseEntity < CarOwner > addCarOwner(
        @NotEmpty(message = "Please, enter name") @RequestParam(value = "name") String name,
        @NotEmpty(message = "Please, enter egn") @RequestParam(value = "egn") String egn,
        @Nullable @NotNull(message = "Please, enter years of driving experience") @Min(value = 0, message = "Years must be greater than or equal to 0") @RequestParam(value = "yearsOfDrivingExperience") Integer yearsOfDrivingExperience) {

        CarOwner carOwner = new CarOwner();

        carOwner = adminService.getCarOwnerByEgn(egn);
        if (carOwner != null) {
            throw new DublicateException("There is owner with this egn");
        }
        carOwner = adminService.addCarOwner(name, egn, yearsOfDrivingExperience);

        return new ResponseEntity < CarOwner > (carOwner, HttpStatus.CREATED);
    }

    @PostMapping(value = "addCar")
    public ResponseEntity < Car > createCar(
        @NotEmpty(message = "Please, enter manufacturer") @RequestParam(value = "manufacturer") String manufacturer,
        @NotEmpty(message = "Please, enter model") @RequestParam(value = "model") String model,
        @NotEmpty(message = "Please, enter color") @RequestParam(value = "color") String color,
        @NotEmpty(message = "Please, enter license plate") @RequestParam(value = "licensePlate") String licensePlate,
        @Nullable @NotNull(message = "Please, enter horsepower") @Min(value = 1, message = "horsepower cannot be negative") @RequestParam(value = "horsepower") Integer horsepower,
        @NotEmpty(message = "Please, enter egn") @RequestParam(value = "egn") String egn) {

        CarOwner carOwner = adminService.getCarOwnerByEgn(egn);
        if (carOwner == null) {
            throw new NotFoundException("Does not exist owner with this egn.");
        }

        Car car = adminService.getCarByLicensePlate(licensePlate);
        if (car != null) {
            throw new DublicateException("The car exist in the system.");
        }

        car = adminService.addCar(manufacturer, model, color, licensePlate, horsepower, carOwner);

        return new ResponseEntity < Car > (car, HttpStatus.CREATED);
    }

    @PostMapping(value = "addInsurance")
    public ResponseEntity < Insurance > addInsurance(
        @NotEmpty(message = "Please, enter car`s license plate") @RequestParam(value = "licensePlate") String licensePlate,
        @Nullable @NotNull(message = "Please, enter for how many months you create insurance.") @RequestParam(value = "months") int months) {

        Car car = adminService.getCarByLicensePlate(licensePlate);
        if (car == null) {
            throw new NotFoundException("Does not exist car with this license plate");
        }
        Calendar calendar = Calendar.getInstance();
        Date now = new Date(calendar.getTime().getTime());

        Insurance insurance = adminService.getInsuranceByCar(car);

        if ((insurance != null && (insurance.getEndDate().compareTo(now) > 0)) ||
            (insurance != null && insurance.getStatus().equals("valid"))) {

            throw new DublicateException("The car has valid  insurance. You can not add new insurance now.");
        }
        insurance = adminService.addInsurance(car, months);

        return new ResponseEntity < Insurance > (insurance, HttpStatus.CREATED);

    }

    @GetMapping(path = "getOwner/{egn}")
    public ResponseEntity < CarOwner > getCarOwner(@PathVariable String egn) {
        CarOwner owner = adminService.getCarOwnerByEgn(egn);
        if (owner == null)
            throw new NotFoundException("Does not exist owner with this egn");

        return new ResponseEntity < CarOwner > (owner, HttpStatus.OK);
    }

    @GetMapping(path = "getInsuranceExpiredDate/{licensePlate}")
    public ResponseEntity < Message > getInsuranceByCar(@PathVariable String licensePlate) {
        Car car = adminService.getCarByLicensePlate(licensePlate);

        Calendar calendar = Calendar.getInstance();
        Date now = new Date(calendar.getTime().getTime());

        if (car != null) {
            Insurance insurance = adminService.getInsuranceByCar(car);
            if ((insurance != null && (insurance.getEndDate().compareTo(now) < 0)) ||
                (insurance != null && !(insurance.getStatus().equals("valid")))) {
                Message message = new Message(now, "Does not exist valid insurance for this car!");
                return new ResponseEntity < Message > (message, HttpStatus.OK);
            }
            if (insurance == null) {
                Message message = new Message(now, "Does not exist insurance for this car!");
                return new ResponseEntity < Message > (message, HttpStatus.OK);
            }
            if (insurance != null && insurance.getEndDate().compareTo(now) > 0 &&
                insurance.getStatus().equals("valid")) {
                Message message = new Message(now, "The insurance`s expired date is :" + insurance.getEndDate());
                return new ResponseEntity < Message > (message, HttpStatus.OK);
            }
        }
        Message message = new Message(now, "The car does not exist in the system.");
        return new ResponseEntity < Message > (message, HttpStatus.OK);
    }

    @PostMapping(path = "terminateInsurance/{licensePlate}")
    public ResponseEntity < Message > terminateInsurance(@PathVariable String licensePlate) {
        Car car = adminService.getCarByLicensePlate(licensePlate);

        Calendar calendar = Calendar.getInstance();
        Date now = new Date(calendar.getTime().getTime());

        if (car != null) {
            Insurance insurance = adminService.getInsuranceByCar(car);
            if ((insurance != null && (insurance.getEndDate().compareTo(now) < 0)) ||
                (insurance != null && !(insurance.getStatus().equals("valid")))) {
                Message message = new Message(now, "Does not exist valid insurance for this car!");
                return new ResponseEntity < Message > (message, HttpStatus.OK);
            }
            if (insurance == null) {
                Message message = new Message(now, "Does not exist insurance for this car!");
                return new ResponseEntity < Message > (message, HttpStatus.OK);
            }
            if (insurance != null && insurance.getEndDate().compareTo(now) > 0 &&
                insurance.getStatus().equals("valid")) {
                adminService.terminateInsurance(insurance);
                Message message = new Message(now, "The insurance is successfully terminated!");
                return new ResponseEntity < Message > (message, HttpStatus.OK);
            }
        }
        Message message = new Message(now, "Does not exist car with this license plate in the system.");
        return new ResponseEntity < Message > (message, HttpStatus.OK);
    }
    
	@GetMapping(path = "getInsuranceByCarOwner/{egn}")
	public ResponseEntity<HashSet<Message>> getInsuranceByCarOwner(@PathVariable String egn) {

		Calendar calendar = Calendar.getInstance();
        Date now = new Date(calendar.getTime().getTime());
        
		CarOwner carOwner = adminService.getCarOwnerByEgn(egn);
		if (carOwner == null) {
			throw new DublicateException("Does not exist car owner with this egn in the system!");
		}
		HashSet<Insurance> insurances = adminService.getInsuranceByCarOwner(carOwner);
		if( insurances == null ) {
			throw new NotFoundException("Does not exist valid insurance!");
		}
		HashSet<Message> messages = new HashSet<Message>();
		for( Insurance insurance : insurances) {
			Message informationForInsurance = new Message(now,"License plate:" + insurance.getCar().getLicensePlate() +"   "
					+"Expired date:" + insurance.getEndDate() + "   "
					+"Price:" + insurance.getPrice());
			messages.add(informationForInsurance);
			}
		return new ResponseEntity<HashSet<Message>>(messages, HttpStatus.OK);

	}
}