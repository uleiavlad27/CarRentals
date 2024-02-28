package Repository;

import Domain.Car;
import Repository.Excepions.RepositoryException;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarsDBRepository extends MemoryRepository<Car> {
    Connection connection;
    private String JDBC_URL = "jdbc:sqlite:cars.sqlite";

    public CarsDBRepository() throws SQLException {
        openConnection();
        createTable();
        loadDataInMemory();
    }

    private void openConnection() throws SQLException {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws SQLException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (final Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS cars(id int, marca varchar(400), model varchar(400))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initData() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(1, "ford", "mondeo"));
        cars.add(new Car(2, "bord", "leo"));
        cars.add(new Car(3, "cactus", "oreo"));
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO cars VALUES (?,?,?)")) {
            for (Car car : cars) {
                statement.setInt(1, car.getId());
                statement.setString(2, car.getMarca());
                statement.setString(3, car.getModel());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cars")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                cars.add(new Car(rs.getInt(1), rs.getString(2), rs.getString(3)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void add(Car car) throws RepositoryException, IOException {
        super.add(car);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO cars VALUES (?,?,?)")) {

            statement.setInt(1, car.getId());
            statement.setString(2, car.getMarca());
            statement.setString(3, car.getModel());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int carId) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM cars WHERE id = ?")) {
            statement.setInt(1, carId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RepositoryException("No car with ID " + carId + " found for deletion.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Car car) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE cars SET marca = ?, model = ? WHERE id = ?")) {
            statement.setString(1, car.getMarca());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RepositoryException("No car with ID " + car.getId() + " found for update.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadDataInMemory(){
        for(Car car : getAll()){
            entities.add(car);
        }
    }


}

