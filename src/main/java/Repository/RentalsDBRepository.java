package Repository;

import Domain.Car;
import Domain.Rental;
import Repository.Excepions.RepositoryException;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalsDBRepository extends MemoryRepository<Rental> {
    Connection connection;
    private String JDBC_URL = "jdbc:sqlite:rentals.sqlite";

    public RentalsDBRepository() throws SQLException {
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
            st.execute("CREATE TABLE IF NOT EXISTS rentals(id int, startDate datetime, endDate datetime, car car)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        List<Rental> rentals = new ArrayList<>();
        LocalDate date1 = LocalDate.of(1990, 6, 29);
        LocalDate date2 = LocalDate.of(1999, 7, 30);
        int carId = 4;
        String marca = "Mercedes";
        String model = "C";
        Car car = new Car(carId, marca, model);
        rentals.add(new Rental(1, date1, date2, car));
        rentals.add(new Rental(2, date1, date2, car));
        rentals.add(new Rental(3, date1, date2, car));
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO rentals VALUES (?,?,?,?)")) {
            for (Rental rental : rentals) {
                Date startDateSql = Date.valueOf(rental.getStartDate());
                Date endDateSql = Date.valueOf(rental.getEndDate());
                statement.setInt(1, rental.getId());
                statement.setDate(2, startDateSql);
                statement.setDate(3, endDateSql);
                statement.setObject(4, rental.getCar());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Rental> getAll() {
        List<Rental> rentals = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM rentals")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LocalDate date1 = rs.getDate(2).toLocalDate();
                LocalDate date2 = rs.getDate(3).toLocalDate();
                String carString = rs.getString(4);
                Car car = Car.fromString(carString);
                rentals.add(new Rental(rs.getInt(1), date1, date2, car));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public void add(Rental rental) throws RepositoryException, IOException {
        super.add(rental);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO rentals VALUES (?,?,?,?)")) {

            Date startDateSql = Date.valueOf(rental.getStartDate());
            Date endDateSql = Date.valueOf(rental.getEndDate());
            statement.setInt(1, rental.getId());
            statement.setDate(2, startDateSql);
            statement.setDate(3, endDateSql);
            statement.setObject(4, rental.getCar());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int rentalId) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM rentals WHERE id = ?")) {
            statement.setInt(1, rentalId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RepositoryException("No car with ID " + rentalId + " found for deletion.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Rental rental) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE rentals SET startDate = ?, endDate = ?, car = ? WHERE id = ?")) {
            Date startDateSql = Date.valueOf(rental.getStartDate());
            Date endDateSql = Date.valueOf(rental.getEndDate());
            statement.setDate(1, startDateSql);
            statement.setDate(2, endDateSql);
            statement.setObject(3, rental.getCar());
            statement.setInt(4, rental.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RepositoryException("No car with ID " + rental.getId() + " found for update.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadDataInMemory(){
        for(Rental rental : getAll()){
            entities.add(rental);
        }
    }

}