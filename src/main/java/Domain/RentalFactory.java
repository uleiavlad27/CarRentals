package Domain;
import java.time.LocalDate;

public class RentalFactory implements IEntityFactory<Rental>{
    @Override
    public Rental createEntity(String line){
        int id = Integer.parseInt(line.split(",")[0]);

        String date1 = line.split(",")[1];
        int year1 = Integer.parseInt(date1.split("-")[0]);
        int month1 = Integer.parseInt(date1.split("-")[1]);
        int day1 = Integer.parseInt(date1.split("-")[2]);
        LocalDate dateStart = LocalDate.of(year1, month1, day1);

        String date2 = line.split(",")[2];
        int year2 = Integer.parseInt(date2.split("-")[0]);
        int month2 = Integer.parseInt(date2.split("-")[1]);
        int day2 = Integer.parseInt(date2.split("-")[2]);
        LocalDate dateEnd = LocalDate.of(year2, month2, day2);

        String carInfo = line.split(",")[3];
        int carId = Integer.parseInt(carInfo.split("-")[0]);
        String carMarca = carInfo.split("-")[1];
        String carModel = carInfo.split("-")[2];
        Car car = new Car(carId, carMarca, carModel);

        Rental rental = new Rental(id, dateStart, dateEnd, car);
        return rental;
    }

    @Override
    public String makeString(Rental obj) {
        Car car = obj.getCar();
        return obj.getId()+","+obj.getStartDate()+","+obj.getEndDate()+","+car.getId()+"-"+car.getMarca()+"-"+car.getModel();
    }
}
