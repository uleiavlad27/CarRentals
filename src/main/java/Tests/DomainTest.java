package Tests;
import Domain.Car;
import Domain.Rental;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


public class DomainTest {

    @Test
    public void carTest(){
        Car car = new Car(1, "Ford", "Mondeo");
        assert car.getId() == 1;
        assert "Ford".equals(car.getMarca());
        assert "Mondeo".equals(car.getModel());
    }

    @Test
    public void rentalTest(){
        Car car = new Car(1, "Ford", "Mondeo");
        LocalDate date1 = LocalDate.of(2003, 06, 27);
        LocalDate date2 = LocalDate.of(2006, 07, 18);
        Rental rental = new Rental(1, date1, date2, car);
        assert rental.getId() == 1;
        assert rental.getCar() == car;
        assert rental.getStartDate() == date1;
        assert rental.getEndDate() == date2;
    }

}

