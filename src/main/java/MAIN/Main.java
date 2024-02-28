package MAIN;

import Domain.Car;
import Domain.CarFactory;
import Domain.Rental;
import Domain.RentalFactory;
import Repository.BinaryRepository;
import Repository.Excepions.RepositoryException;
import Repository.FileRepository;
import Repository.CarsDBRepository;
import Repository.RentalsDBRepository;
import Service.CarService;
import Service.RentalService;
import Settings.AppConfig;
import UI.UI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException, RepositoryException, SQLException {
        AppConfig configReader = new AppConfig();
        Map<String, String> config = configReader.config();
        if (Objects.equals(config.get("Repository"), "binary")) {
            BinaryRepository<Car> carRepo = new BinaryRepository<Car>(config.get("Cars"));
            BinaryRepository<Rental> rentalRepo = new BinaryRepository<Rental>(config.get("Rentals"));
            CarService carService = new CarService(carRepo);
            RentalService rentalService = new RentalService(rentalRepo);
            UI ui = new UI(carService, rentalService);
            UI.run();
        } else if (Objects.equals(config.get("Repository"), "text")) {
            CarFactory carFactory = new CarFactory();
            RentalFactory rentalFactory = new RentalFactory();
            FileRepository<Car> carRepo = new FileRepository<>(config.get("Cars"), carFactory);
            FileRepository<Rental> rentalRepo = new FileRepository<>(config.get("Rentals"), rentalFactory);
            CarService carService = new CarService(carRepo);
            RentalService rentalService = new RentalService(rentalRepo);
            UI ui = new UI(carService, rentalService);
            UI.run();
        } else if(Objects.equals(config.get("Repository"),"database")){
            CarsDBRepository carRepository = new CarsDBRepository();
            RentalsDBRepository rentalRepository = new RentalsDBRepository();
            CarService carService = new CarService(carRepository);
            RentalService rentalService = new RentalService(rentalRepository);
            UI ui = new UI(carService, rentalService);
            ui.run();
            carRepository.closeConnection();
            rentalRepository.closeConnection();
        } else
            System.out.println("Incorrect Repository, type one who exists!");
    }
}



