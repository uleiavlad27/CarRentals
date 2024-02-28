package UI;

import Domain.Car;
import Domain.Rental;
import Repository.Excepions.RepositoryException;
import Repository.MemoryRepository;
import Repository.Repository;
import Service.CarService;
import Service.RentalService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.stream.Collectors;

public class UI {
    private static Scanner scanner = new Scanner(System.in);
    private static Repository<Car> carRepository = new MemoryRepository<Car>();
    private static Repository<Rental> rentalRepository = new MemoryRepository<Rental>();
    private static CarService carService = new CarService(carRepository);
    private static RentalService rentalService = new RentalService(rentalRepository);


    public UI(CarService carService, RentalService rentalService) {
        //UI.carRepository = carRepository;
        //UI.rentalRepository = rentalRepository;
        UI.carService = carService;
        UI.rentalService = rentalService;
    }

    private static void printMenu() {
        System.out.println("\n CAR MENU ");
        System.out.println("1. Print Cars");
        System.out.println("2. Add Car");
        System.out.println("3. Update Car Information");
        System.out.println("4. Delete Car by ID");
        System.out.println("\n RENTAL MENU");
        System.out.println("5. Print Rentals");
        System.out.println("6. Add Rental");
        System.out.println("7. Update Rental Information");
        System.out.println("8. Delete Rental by Id");
        System.out.println("\nREPORTS\n");
        System.out.println("9. Monthly Rentals Report");
        System.out.println("10. Cars with Longest Rentals Reports");
        System.out.println("11. Car Rentals Report\n");
        System.out.println("0. EXIT\n");
        System.out.print("Choose an option: ");
    }

    public static void run() throws RepositoryException, IOException {
        //data();
        int cmd = -1;

        while (cmd != 0) {
            printMenu();
            cmd = scanner.nextInt();
            switch (cmd) {
                case 1: {
                    printCarList();
                    break;
                }
                case 2: {
                    addCar();
                    break;
                }
                case 3: {
                    updateCar();
                    break;
                }
                case 4: {
                    deleteCar();
                    break;
                }
                case 5: {
                    printRentalList();
                    break;
                }
                case 6: {
                    addRental();
                    break;
                }
                case 7: {
                    updateRental();
                    break;
                }
                case 8: {
                    deleteRental();
                    break;
                }
                case 9:{
                    printMonthlyRentalsReport();
                    break;
                }
                case 10:{
                    printCarsWithLongestRentalsReport();
                    break;
                }
                case 11:{
                    printCarRentalsReport();
                    break;
                }
                case 0:
                    System.out.println("PApapapapa!");
                    break;
                default:
                    System.out.println("optiunea pe care ati ales-o este invalida, va rugam incercati din nou!");
                    break;


            }
        }
    }

    private static void addCar() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter brand name: ");
        String marca = scanner.nextLine();
        System.out.print("Enter model name: ");
        String model = scanner.nextLine();
        try {
            Car car = new Car(id, marca, model);
            carService.addCar(car);
            System.out.println("Car has been added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void printCarList() {
        System.out.println("\n CARS");
        List<Car> carsList = carService.getAllCars();
        for (Car car : carsList) {
            System.out.println(car);
        }
    }

    private static void deleteCar() throws RepositoryException {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        int index = -1;
        List<Car> carsList = carService.getAllCars();
        for (Car car : carsList) {
            if (id == car.getId()) {
                index = id;
                break;
            }
        }
        carService.deleteCar(index);
    }

    private static void updateCar() throws RepositoryException {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        int index = -1;
        List<Car> carsList = carService.getAllCars();
        for (Car car : carsList) {
            if (id == car.getId()) {
                scanner.nextLine();
                System.out.print("Enter brand: ");
                String brand = scanner.nextLine();

                System.out.print("Enter model: ");
                String model = scanner.nextLine();
                Car car1 = new Car(id, brand, model);
                carService.updateCar(car1);
            }
        }
    }

    //FOR RENTALS

    private static void addRental() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Car ID: ");
        int carId = scanner.nextInt();
        scanner.nextLine();
        System.out.println(carId);
        Car car = carService.find(carId);
        LocalDate startDate = null;
        LocalDate endDate = null;
        System.out.print("Enter start date(yyyy-mm-dd): ");
        String dateInputStart = scanner.nextLine();
        System.out.print("Enter end date(yyyy-mm-dd): ");
        String dateInputEnd = scanner.nextLine();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            startDate = LocalDate.parse(dateInputStart, dateFormat);
            endDate = LocalDate.parse(dateInputEnd, dateFormat);
        } catch (Exception e) {
            System.out.println("Invalid date format. Next time use yyyy-mm-dd");
        }
        try {
            Rental rental = new Rental(id, startDate, endDate, car);
            rentalService.addRental(rental);
            System.out.println("Rental has been added");
        } catch (RepositoryException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void printRentalList() {
        System.out.println("\n RENTALS");
        List<Rental> rentals = rentalService.getAllRental();
        for (Rental rental : rentals) {
            System.out.println(rental);
        }
    }

    private static void deleteRental() throws RepositoryException {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        int index = -1;
        List<Rental> rentals = rentalService.getAllRental();
        for (int i = 0; i < rentals.size(); i++) {
            Rental rental = rentals.get(i);
            if (id == rental.getId()) {
                index = id;
                break;
            }
        }
        rentalService.deleteRental(index);
    }

    private static void updateRental() throws RepositoryException {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        List<Rental> rentals = rentalService.getAllRental();
        for (Rental rental : rentals) {
            if (id == rental.getId()) {
                System.out.print("Enter a new Car ID: ");
                int carId = scanner.nextInt();
                scanner.nextLine();
                Car car = carService.find(carId);

                LocalDate startDate = null;
                LocalDate endDate = null;
                System.out.print("Enter start date(yyyy-mm-dd): ");
                String dateInputStart = scanner.nextLine();
                System.out.print("Enter end date(yyyy-mm-dd): ");
                String dateInputEnd = scanner.nextLine();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                try {
                    startDate = LocalDate.parse(dateInputStart, dateFormat);
                    endDate = LocalDate.parse(dateInputEnd, dateFormat);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Next time use yyyy-mm-dd");
                }
                Rental rental1 = new Rental(id, startDate, endDate, car);
                rentalService.updateRental(rental1);
                System.out.println("The Rental information has been modified");
            }
        }
    }

    private static void printCarRentalsReport() {
        System.out.println("\nCars with the most rentals:");
        Map<Car, Long> carRentalsMap = carService.getAllCars().stream()
                .collect(Collectors.toMap(car -> car, car -> rentalService.calculateTotalRentalsForCar(car.getId())));

        carRentalsMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> {
                    System.out.println(entry.getKey() + " - " + entry.getValue() + " rentals");
                });
    }

    private static void printMonthlyRentalsReport() {
        System.out.println("\nMonthly rentals:");
        Map<String, Long> monthlyRentalsMap = rentalService.getAllRental().stream()
                .collect(Collectors.groupingBy(
                        rental -> rental.getStartDate().getMonth().toString(),
                        Collectors.counting()
                ));

        monthlyRentalsMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> {
                    System.out.println(entry.getKey() + " - " + entry.getValue() + " rentals");
                });
    }

    private static void printCarsWithLongestRentalsReport() {
        System.out.println("\nCars with the longest total rental time:");
        Map<Car, Integer> carTotalRentalDaysMap = carService.getAllCars().stream()
                .collect(Collectors.toMap(car -> car, car -> rentalService.calculateTotalRentalDaysForCar(car.getId())));

        carTotalRentalDaysMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> {
                    System.out.println(entry.getKey() + " - " + entry.getValue() + " total rental days");
                });
    }


//    private static void data() throws RepositoryException, IOException {
//        Car car1 = new Car(1, "Ford", "Mondeo");
//        Car car2 = new Car(2, "Nissan", "Skyline");
//        Car car3 = new Car(27, "BMW", "X6");
//        Car car4 = new Car(7, "Toyota", "Camry");
//        Car car5 = new Car(108, "Hyundai", "Kona");
//        carService.addCar(car1);
//        carService.addCar(car2);
//        carService.addCar(car3);
//        carService.addCar(car4);
//        carService.addCar(car5);
//
//        LocalDate date1 = LocalDate.of(2020, 03, 19);
//        LocalDate date2 = LocalDate.of(2020, 05, 19);
//        LocalDate date3 = LocalDate.of(2003, 06, 27);
//        LocalDate date4 = LocalDate.of(2003, 07, 19);
//        LocalDate date5 = LocalDate.of(2004, 01, 01);
//        LocalDate date6 = LocalDate.of(2004, 01, 20);
//        LocalDate date7 = LocalDate.of(2005, 10, 19);
//        LocalDate date8 = LocalDate.of(2005, 11, 28);
//        LocalDate date9 = LocalDate.of(2006, 12, 03);
//        LocalDate date10 = LocalDate.of(2007, 01, 19);
//
//
//        Rental rental1 = new Rental(1, date1, date2, car1);
//        Rental rental2 = new Rental(102, date3, date4, car2);
//        Rental rental3 = new Rental(27, date5, date6, car3);
//        Rental rental4 = new Rental(17, date7, date8, car4);
//        Rental rental5 = new Rental(3, date9, date10, car5);
//
//        rentalService.addRental(rental1);
//        rentalService.addRental(rental2);
//        rentalService.addRental(rental3);
//        rentalService.addRental(rental4);
//        rentalService.addRental(rental5);
//    }

}
