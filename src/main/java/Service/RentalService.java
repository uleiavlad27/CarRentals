package Service;
import Domain.*;
import Repository.*;
import Repository.Excepions.RepositoryException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.io.IOException;
import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;

public class RentalService {

    private Repository<Rental> rentalRepository;

    public RentalService(Repository<Rental> rentalRepository){
        this.rentalRepository = rentalRepository;
    }

    public void addRental(Rental rental) throws RepositoryException, IOException {
        if(rental.getCar() == null)
            throw new RepositoryException("Car can't be null");
        rentalRepository.add(rental);
    }

    public void deleteRental(int id) throws RepositoryException {
        rentalRepository.remove(id);
    }

    public void updateRental(Rental rental) throws RepositoryException {
        rentalRepository.update(rental);
    }

    public List<Rental> getAllRental(){
        return rentalRepository.getAll();
    }

    public Rental find(int id){
        return rentalRepository.find(id);
    }

    public int size(){
        return rentalRepository.size();
    }

    public long calculateTotalRentalsForCar(int carId) {
        List<Rental> rentalsForCar = rentalRepository.getAll().stream()
                .filter(rental -> rental.getCar().getId() == carId)
                .collect(Collectors.toList());

        return rentalsForCar.size();
    }

    public int calculateTotalRentalDaysForCar(int carId) {
        List<Rental> rentalsForCar = rentalRepository.getAll().stream()
                .filter(rental -> rental.getCar().getId() == carId)
                .collect(Collectors.toList());

        int totalRentalDays = 0;
        for (Rental rental : rentalsForCar) {
            LocalDate startDate = rental.getStartDate();
            LocalDate endDate = rental.getEndDate();
            long rentalDays = ChronoUnit.DAYS.between(startDate, endDate);
            totalRentalDays += rentalDays;
        }

        return totalRentalDays;
    }
}
