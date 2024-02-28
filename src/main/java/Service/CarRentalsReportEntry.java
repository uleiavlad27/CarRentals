package Service;

import Domain.Car;

public class CarRentalsReportEntry {
    private final Car car;
    private final int rentalsCount;

    public CarRentalsReportEntry(Car car, int rentalsCount) {
        this.car = car;
        this.rentalsCount = rentalsCount;
    }

    public Car getCar() {
        return car;
    }

    public int getRentalsCount() {
        return rentalsCount;
    }
}
