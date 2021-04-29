package company.carsProject.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
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
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Override
    public CarOwner addCarOwner(String name, String egn, int yearsOfDrivingExperience) {
        CarOwner carOwner = new CarOwner(name, egn, yearsOfDrivingExperience);
        carOwnerRepository.save(carOwner);
        return carOwner;
    }

    @Override
    public CarOwner getCarOwnerByEgn(String egn) {
        List < CarOwner > carOwners = carOwnerRepository.findAll();
        if (carOwners == null) {
            return null;
        } else {
            for (CarOwner owner: carOwners) {
                if (owner.getEgn().equals(egn)) {
                    return owner;
                }
            }
        }
        return null;
    }

    @Override
    public Car addCar(String manufacturer, String model, String color, String licensePlate, int horsepower,
        CarOwner carOwner) {

        Car car = new Car(manufacturer, model, color, licensePlate, horsepower, carOwner);
        carRepository.save(car);
        return car;
    }

    @Override
    public Car getCarByLicensePlate(String licensePlate) {

        List < Car > cars = (List < Car > ) carRepository.findAll();

        if (cars == null) {

            return null;

        } else {

            for (Car car: cars) {

                if (car.getLicensePlate().equals(licensePlate)) {

                    return car;
                }
            }
        }
        return null;
    }

    @Override
    public Insurance addInsurance(Car car, int months) {

        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar.getTime().getTime());
        calendar.add(Calendar.MONTH, months);
        Date expirationDate = new Date(calendar.getTime().getTime());

        double price = (((13 * months + car.getHorsepower() * 0.115) -
            ((car.getCarOwner().getYearsOfDrivingExperience()) * 0.1)));

        Insurance insurance = new Insurance(startDate, expirationDate, price, "valid", car);
        insuranceRepository.save(insurance);

        return insurance;
    }

    @Override
    public Insurance getInsuranceByCar(Car car) {

        List < Insurance > insurances = insuranceRepository.findAll();

        for (Insurance insurance: insurances) {

            if (insurance.getCar().getLicensePlate().equals(car.getLicensePlate())) {

                return insurance;

            }

        }

        return null;
    }

    @Override
    public void terminateInsurance(Insurance insurance) {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date(calendar.getTime().getTime());

        insurance.setStatus("invalid");
        insurance.setEndDate(now);
    }

    @Override
    public HashSet < Insurance > getInsuranceByCarOwner(CarOwner carOwner) {

        List < Car > cars = carRepository.findAll();
        List < Insurance > allInsurances = insuranceRepository.findAll();
        HashSet < Insurance > userInsurances = new HashSet < Insurance > ();

        if (cars == null) {

            return null;

        } else {

            for (Car car: cars) {

                if (car.getCarOwner().getEgn().equals(carOwner.getEgn())) {

                    for (Insurance insurance: allInsurances) {

                        if (insurance.getCar().getCarOwner().getEgn().equals(carOwner.getEgn())) {

                            userInsurances.add(insurance);
                        }
                    }
                }
            }
        }
        return userInsurances;
    }

}