package Domain;

import java.io.ObjectInputStream;
import java.io.Serializable;

import static com.sun.tools.javac.main.Option.O;

public class Car extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String marca;
    private String model;

    public Car(int id, String marca, String model) {
        super(id);
        this.marca = marca;
        this.model = model;
    }
    public Car(){
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car| " +
                "Id:" + id +
                ",  Marca='" + marca + '\'' +
                ",  Model='" + model + '\'' +
                '|';
    }
    public static Car fromString(String carString) {
        // Example: "Car| Id:4, Marca='Mercedes', Model='C'|"

        // Remove the leading and trailing "Car|" and "|"
        String carData = carString.substring("Car| ".length(), carString.length() - 1);

        // Split the remaining string into key-value pairs
        String[] keyValuePairs = carData.split(", ");

        // Extract values from key-value pairs
        int id = Integer.parseInt(keyValuePairs[0].substring("Id:".length()));
        String marca = keyValuePairs[1].substring("Marca='".length(), keyValuePairs[1].length() - 1);
        String model = keyValuePairs[2].substring("Model='".length(), keyValuePairs[2].length() - 1);

        // Create and return a new Car object
        return new Car(id, marca, model);
    }


}