package Domain;

public interface IEntityFactory<T extends Entity>{
    T createEntity(String line);
    String makeString(T obj);
}
