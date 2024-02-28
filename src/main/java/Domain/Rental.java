package Domain;
import java.io.Serializable;
import java.time.LocalDate;
import static com.sun.tools.javac.main.Option.O;

public class Rental extends Entity implements Serializable {
    private long serialVersionUID = 1L;
    private LocalDate startDate;
    private LocalDate endDate;
    private Car car;

    public Rental(int id, LocalDate dataInceput, LocalDate dataSfarsit, Car car) {
        super(id);
        this.startDate = dataInceput;
        this.endDate = dataSfarsit;
        this.car = car;
    }

    public Rental(){

    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate dataInceput) {
        this.startDate = dataInceput;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate dataSfarsit) {
        this.endDate = dataSfarsit;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car masina) {
        this.car = masina;
    }

    @Override
    public String toString() {
        return "Inchiriere |" +
                "Id: " + id +
                ", Car Id: " + car +
                ", Start Date: " + startDate +
                ", End Date: " + endDate +
                '}';
    }
}
