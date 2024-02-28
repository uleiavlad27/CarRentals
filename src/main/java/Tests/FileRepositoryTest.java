package Tests;

import Domain.*;
import Repository.Excepions.RepositoryException;
import Repository.Repository;
import org.junit.jupiter.api.Test;
import Repository.FileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class FileRepositoryTest {
    @Test
    public void carFileRepositoryTest() throws IOException, RepositoryException{
        IEntityFactory<Car> carsFactory = new CarFactory();
        FileRepository<Car> cars = new FileRepository<>("C:\\Users\\vlads\\OneDrive\\Desktop\\MAP\\a3-vladuleia\\Tests\\Masini.txt", carsFactory);
        assert cars.size() == 2;
        Car car = new Car(14,"a","b");
        cars.add(car);
        cars.writeToFile();
        Repository<Car> newCars = new FileRepository<>("C:\\Users\\vlads\\OneDrive\\Desktop\\MAP\\a3-vladuleia\\Tests\\Masini.txt", carsFactory);
        assert newCars.size() == 3;

    }

    @Test
    public void rentalFileRepositoryTest() throws IOException, RepositoryException{
        IEntityFactory<Rental> rentalFactory = new RentalFactory();
        FileRepository<Rental> rentals = new FileRepository<>("C:\\Users\\vlads\\OneDrive\\Desktop\\MAP\\a3-vladuleia\\Tests\\Rentals.txt", rentalFactory);
        assert rentals.size() == 2;
        int id = 14;
        LocalDate date1 = LocalDate.of(1990,6,29);
        LocalDate date2 = LocalDate.of(1999,7,30);
        int carId = 4;
        String marca = "Mercedes";
        String model = "C";
        Car car = new Car(carId, marca, model);
        Rental rental = new Rental(id, date1, date2, car);
        rentals.add(rental);
        Repository<Rental> newRentals = new FileRepository<>("C:\\Users\\vlads\\OneDrive\\Desktop\\MAP\\a3-vladuleia\\Tests\\Rentals.txt", rentalFactory);
        assert newRentals.size() == 3;
        rentals.remove(2);
        assert rentals.size() == 2;

        LocalDate date3 = LocalDate.of(1990,6,29);
        LocalDate date4 = LocalDate.of(2000,7,30);
        int carId1 = 5;
        String marca1 = "Mercedes";
        String model1 = "DAAAAA";
        Car car1 = new Car(carId1, marca1, model1);
        Rental rental1 = new Rental(id, date3, date4, car1);
        rentals.update(rental1);

        Rental rental2 = rentals.find(id);
        assert rental2.getCar().getId() == 5;
    }


}
