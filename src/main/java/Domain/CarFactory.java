package Domain;

public class CarFactory implements IEntityFactory<Car>{
    @Override
    public Car createEntity(String line){
        int id = Integer.parseInt(line.split(",")[0]);
        String marca = line.split(",")[1];
        String model = line.split(",")[2];
        return new Car(id, marca, model);
    }

    @Override
    public String makeString(Car obj) {
        return obj.getId()+","+obj.getMarca()+","+obj.getModel();
    }
}
