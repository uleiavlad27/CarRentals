package Tests;
import Domain.Car;
import Repository.Excepions.RepositoryException;
import Repository.MemoryRepository;
import Repository.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RepositoryTest {
    Repository<Car> cars = new MemoryRepository<Car>();
    @Test
    public void memoryRepositoryTest() throws RepositoryException, IOException {
        Car car1 = new Car(1, "a", "b");
        Car car2 = new Car(2, "b", "c");
        Car car3 = new Car(3, "d", "e");
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        assert cars.size() == 3;
        cars.remove(2);
        assert cars.size() == 2;
        Car car4 = new Car(3, "e", "f");
        cars.update(car4);
        assert cars.find(3).getMarca().equals("e");

    }

}
