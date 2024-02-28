package com.example.lab4;

import Domain.Car;
import Domain.Rental;
import Repository.Excepions.RepositoryException;
import Repository.MemoryRepository;
import Repository.Repository;
import Service.CarService;
import Service.RentalService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Repository<Car> carRepository = new MemoryRepository<Car>();
        CarService carService = new CarService(carRepository);
        Repository<Rental> rentalRepository = new MemoryRepository<Rental>();
        RentalService rentalService = new RentalService(rentalRepository);

        try {
            Car car1 = new Car(1, "a", "b");
            Car car2 = new Car(2, "c", "d");
            Car car3 = new Car(3, "e", "f");
            carService.addCar(car1);
            carService.addCar(car2);
            carService.addCar(car3);
            LocalDate date1 = LocalDate.of(1990, 6, 29);
            LocalDate date2 = LocalDate.of(1999, 7, 30);
            rentalService.addRental(new Rental(1, date1, date2, car1));
            rentalService.addRental(new Rental(2, date1, date2, car2));
            rentalService.addRental(new Rental(3, date1, date2, car3));

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

        VBox mainVerticalBox = new VBox();
        mainVerticalBox.setPadding(new Insets(10));
        HBox listsHorizontalBox = new HBox();

        ListView<Car> carsListView = new ListView<>();
        ObservableList<Car> cars = FXCollections.observableArrayList(carService.getAllCars());
        carsListView.setItems(cars);

        ListView<Rental> rentalsListView = new ListView<>();
        ObservableList<Rental> rentals = FXCollections.observableArrayList(rentalService.getAllRental());
        rentalsListView.setItems(rentals);

        listsHorizontalBox.getChildren().add(carsListView);
        listsHorizontalBox.getChildren().add(rentalsListView);
        mainVerticalBox.getChildren().add(listsHorizontalBox);
////////////////////////CAR BUTTONS///////////////////////////////////
        GridPane carGridPane = new GridPane();
        Label idLabel = new Label("Id");
        TextField carIdTextField = new TextField();
        idLabel.setPadding(new Insets(0, 10, 0, 0));
        Label marcaLabel = new Label("Marca");
        TextField carMarcaTextField = new TextField();
        marcaLabel.setPadding(new Insets(0, 10, 0, 0));
        Label modelLabel = new Label("Model");
        TextField carModelTextField = new TextField();
        modelLabel.setPadding(new Insets(0, 10, 0, 0));

//////////////////////////////RENTAL BUTTONS//////////////////////////////////////////////

        Label rentalIdLabel = new Label("Id");
        TextField rentalIdTextField = new TextField();
        rentalIdLabel.setPadding(new Insets(0, 10, 0, 50));
        Label startDateLabel = new Label("Start Date");
        TextField startDateTextField = new TextField();
        startDateLabel.setPadding(new Insets(0, 10, 0, 50));
        Label endDateLabel = new Label("Model");
        TextField endDateTextField = new TextField();
        endDateLabel.setPadding(new Insets(0, 10, 0, 50));
        Label carLabel = new Label("Car");
        TextField carTextField = new TextField();
        carLabel.setPadding((new Insets(0, 10, 0, 50)));

        carGridPane.add(idLabel, 0, 0);
        carGridPane.add(carIdTextField, 1, 0);
        carGridPane.add(marcaLabel, 0, 1);
        carGridPane.add(carMarcaTextField, 1, 1);
        carGridPane.add(modelLabel, 0, 2);
        carGridPane.add(carModelTextField, 1, 2);

        carGridPane.add(rentalIdLabel, 3, 0);
        carGridPane.add(rentalIdTextField, 4, 0);
        carGridPane.add(startDateLabel, 3, 1);
        carGridPane.add(startDateTextField, 4, 1);
        carGridPane.add(endDateLabel, 3, 2);
        carGridPane.add(endDateTextField, 4, 2);
        carGridPane.add(carLabel, 3, 3);
        carGridPane.add(carTextField, 4, 3);

        mainVerticalBox.getChildren().add(carGridPane);

        //BUTOANE Masina

        HBox carsActionsHorizontalBox = new HBox();
        Button addCarButton = new Button("Add Car");
        Button removeCarButton = new Button("Remove Car");
        Button updateCarButton = new Button("Update Car");
        carsActionsHorizontalBox.getChildren().add(addCarButton);
        carsActionsHorizontalBox.getChildren().add(removeCarButton);
        carsActionsHorizontalBox.getChildren().add(updateCarButton);


        mainVerticalBox.getChildren().add(carsActionsHorizontalBox);


        addCarButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(carIdTextField.getText());
                    String marca = carMarcaTextField.getText();
                    String model = carModelTextField.getText();
                    Car car = new Car(id, marca, model);
                    carService.addCar(car);
                    cars.setAll(carService.getAllCars());
                } catch (RepositoryException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        carsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Car car = carsListView.getSelectionModel().getSelectedItem();
                if (car != null) {


                    carIdTextField.setText(Integer.toString(car.getId()));
                    carMarcaTextField.setText(car.getMarca());
                    carModelTextField.setText(car.getModel());
                }
            }
        });

        updateCarButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(carIdTextField.getText());
                    String marca = carMarcaTextField.getText();
                    String model = carModelTextField.getText();
                    Car car = new Car(id, marca, model);
                    carService.updateCar(car);
                    cars.setAll(carService.getAllCars());
                } catch (RepositoryException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }

            }
        });


        removeCarButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(carIdTextField.getText());
                    carService.deleteCar(id);
                    cars.setAll(carService.getAllCars());
                } catch (RepositoryException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }

            }
        });

        HBox rentalActionsHorizontalBox = new HBox();
        Button addRentalButton = new Button("Add Rental");
        Button removeRentalButton = new Button("Remove Rental");
        Button updateRentalButton = new Button("Update Rental");
        rentalActionsHorizontalBox.getChildren().add(addRentalButton);
        rentalActionsHorizontalBox.getChildren().add(removeRentalButton);
        rentalActionsHorizontalBox.getChildren().add(updateRentalButton);
        carsActionsHorizontalBox.setMargin(rentalActionsHorizontalBox, new javafx.geometry.Insets(0, 0, 0, 0));

        mainVerticalBox.getChildren().add(rentalActionsHorizontalBox);


        addRentalButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(rentalIdTextField.getText());

                    String stringDate1 = startDateTextField.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = LocalDate.parse(stringDate1, formatter);


                    String stringDate2 = endDateTextField.getText();
                    LocalDate endDate = LocalDate.parse(stringDate2, formatter);

                    int carId = Integer.parseInt(carTextField.getText());
                    Car car = carService.find(carId);

                    Rental rental = new Rental(id, startDate, endDate, car);
                    rentalService.addRental(rental);
                    rentals.setAll(rentalService.getAllRental());
                } catch (RepositoryException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        rentalsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Rental rental = rentalsListView.getSelectionModel().getSelectedItem();
                if (rental != null) {


                    rentalIdTextField.setText(Integer.toString(rental.getId()));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String date1 = rental.getStartDate().format(formatter);
                    String date2 = rental.getEndDate().format(formatter);
                    startDateTextField.setText(date1);
                    endDateTextField.setText(date2);
                    carTextField.setText(Integer.toString(rental.getCar().getId()));
                }
            }
        });
        updateRentalButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(rentalIdTextField.getText());

                    String stringDate1 = startDateTextField.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = LocalDate.parse(stringDate1, formatter);


                    String stringDate2 = endDateTextField.getText();
                    LocalDate endDate = LocalDate.parse(stringDate2, formatter);

                    int carId = Integer.parseInt(carTextField.getText());
                    Car car = carService.find(carId);

                    Rental rental = new Rental(id, startDate, endDate, car);
                    rentalService.updateRental(rental);
                    rentals.setAll(rentalService.getAllRental());
                } catch (RepositoryException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }

            }
        });

        removeRentalButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    int id = Integer.parseInt(rentalIdTextField.getText());
                    rentalService.deleteRental(id);
                    rentals.setAll(rentalService.getAllRental());
                } catch (RepositoryException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }

            }
        });

        rentalActionsHorizontalBox.setPadding(new Insets(0,0,0,40));

        HBox mainLayout = new HBox(carsActionsHorizontalBox, rentalActionsHorizontalBox);

        VBox main = new VBox(mainVerticalBox, mainLayout) ;

        Scene scene = new Scene(main, 875, 300);
        rentalsListView.setPrefWidth(625);
        stage.setResizable(true);

        stage.setTitle("Cars and Rentals");
        stage.setScene(scene);
        stage.show();
    }


}