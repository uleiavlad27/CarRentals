package Tests;

import Domain.Car;
import Domain.Rental;
import Repository.Excepions.RepositoryException;
import Repository.Repository;
import Repository.MemoryRepository;
import Service.RentalService;
import org.junit.jupiter.api.Test;
import Service.CarService;

import java.io.IOException;
import java.time.LocalDate;

public class ServiceTest {
    Repository<Car> cars = new MemoryRepository<Car>();
    CarService carService = new CarService(cars);
    Repository<Rental> rentals = new MemoryRepository<Rental>();
    RentalService rentalService = new RentalService(rentals);

    @Test
    public void CarServicetest() throws RepositoryException, IOException {
        assert carService.size() == 0;
        Car car1 = new Car(1,"a", "b");
        Car car2 = new Car(2,"c", "d");
        Car car3 = new Car(1, "e", "f");
        carService.addCar(car1);
        carService.addCar(car2);

        assert carService.size() == 2;
        carService.deleteCar(2);
        assert carService.size() == 1;
        carService.updateCar(car3);
        assert carService.find(1).getMarca() == "e";
        carService.addCar(car2);
        System.out.println(carService.find(2));
        assert car2 == carService.find(2);

    }

    @Test
    public void RentalService() throws  RepositoryException, IOException{
        assert rentalService.size() == 0;
        int id = 14;
        LocalDate date1 = LocalDate.of(1990,6,29);
        LocalDate date2 = LocalDate.of(1999,7,30);
        int carId = 4;
        String marca = "Mercedes";
        String model = "C";
        Car car = new Car(carId, marca, model);
        Rental rental = new Rental(id, date1, date2, car);

        int id1 = 2;
        LocalDate date3 = LocalDate.of(1990,6,29);
        LocalDate date4 = LocalDate.of(2000,7,30);
        int carId1 = 5;
        String marca1 = "Mercedes";
        String model1 = "DAAAAA";
        Car car1 = new Car(carId1, marca1, model1);
        Rental rental1 = new Rental(id1, date3, date4, car1);

        rentalService.addRental(rental);
        rentalService.addRental(rental1);
        assert rentals.size() == 2;

        rentalService.deleteRental(14);
        assert  rentals.size() == 1;
        rentalService.addRental(rental);

        Rental rental2 = new Rental(id, date3,date4,car1);
        rentalService.updateRental(rental2);
        assert rentals.find(14).getCar().getModel() == "DAAAAA";


    }
}
