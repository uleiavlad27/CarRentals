package Service;
import Domain.*;
import Repository.*;
import Repository.Excepions.RepositoryException;
import java.util.List;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CarService {
    private Repository<Car> carRepository;

    public CarService(Repository<Car> carRepository){
        this.carRepository = carRepository;
    }

    public void addCar(Car car) throws RepositoryException, IOException {

        carRepository.add(car);
    }

    public void deleteCar(int id) throws RepositoryException {
        carRepository.remove(id);
    }

    public void updateCar(Car car) throws RepositoryException {
        carRepository.update(car);
    }

    public List<Car> getAllCars(){
        return carRepository.getAll();
    }

    public Car find(int id){
        return carRepository.find(id);
    }

    public int size(){
        return carRepository.size();
    }

    public int calculateTotalRentalDaysForCar(int carId, RentalService rentalService) {
        return rentalService.getAllRental()
                .stream()
                .filter(rental -> rental.getCar().getId() == carId)
                .mapToInt(this::calculateRentalDays)
                .sum();
    }

    // Metoda auxiliară pentru a calcula numărul de zile de închiriere pentru o închiriere dată
    private int calculateRentalDays(Rental rental) {
        return rental.getEndDate().getDayOfYear() - rental.getStartDate().getDayOfYear() + 1;
    }




}
