package Tests;

import Domain.Car;
import Domain.Rental;
import Repository.BinaryRepository;
import Repository.Excepions.RepositoryException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

public class BinaryRepositoryTest {

    @Test
    public void CarBinaryTest() throws RepositoryException, IOException {
        BinaryRepository<Car> cars = new BinaryRepository<Car>("C:\\Users\\vlads\\OneDrive\\Desktop\\MAP\\a3-vladuleia\\Repository\\Files\\Cars.bin");
        //System.out.println(cars.size());
        assert cars.size() == 0;
        Car car1 = new Car(1, "a", "b");
        Car car2 = new Car(2, "a", "b");

        cars.add(car1);
        cars.add(car2);
        assert cars.size() == 2;
        cars.remove(2);
        assert cars.size() == 1;
        cars.add(car2);
        Car car3 = new Car(2, "h", "b");
        cars.update(car3);

        Car car4 = cars.find(2);
        assert car4.getMarca() == "h";
    }

    @Test
    public void RentalBinaryTest() throws RepositoryException, IOException{
        BinaryRepository<Rental> rentals = new BinaryRepository<Rental>("C:\\Users\\vlads\\OneDrive\\Desktop\\MAP\\a3-vladuleia\\Repository\\Files\\Rentals.bin");
        //System.out.println(rentals.size());
        assert rentals.size() == 0;
        int id = 14;
        LocalDate date1 = LocalDate.of(1990,6,29);
        LocalDate date2 = LocalDate.of(1999,7,30);
        int carId = 4;
        String marca = "Mercedes";
        String model = "C";
        Car car = new Car(carId, marca, model);
        Rental rental = new Rental(id, date1, date2, car);
        rentals.add(rental);
        assert rentals.size() == 1;

        int id1 = 1;
        LocalDate date3 = LocalDate.of(1990,6,29);
        LocalDate date4 = LocalDate.of(1999,7,30);
        int carId1 = 1;
        String marca1 = "Mer";
        String model1 = "C";
        Car car1 = new Car(carId1, marca1, model1);
        Rental rental1 = new Rental(id1, date3, date4, car1);
        rentals.add(rental1);
        assert  rentals.size() == 2;

        rentals.remove(1);
        assert  rentals.size() == 1;

        rentals.add(rental1);
        int id3 = 14;
        LocalDate date5 = LocalDate.of(1990,6,29);
        LocalDate date6 = LocalDate.of(1999,7,30);
        int carId2 = 4;
        String marca2 = "Merc";
        String model2 = "C";
        Car car2 = new Car(carId2, marca2, model2);
        Rental rental2 = new Rental(id3, date5, date6, car2);
        rentals.update(rental2);

        Rental rental3 = rentals.find(14);
        assert rental3.getCar().getMarca() == "Merc";
    }
}
