package Repository;
import Domain.Entity;
import Repository.Excepions.RepositoryException;
import java.util.List;
import java.io.IOException;

public interface Repository<T extends Entity> extends Iterable<T> {

    void add(T obj) throws RepositoryException, IOException;
    void remove(int id) throws RepositoryException;
    void update(T obj) throws RepositoryException;
    T find(int id);
    List<T> getAll();
    int size();
}
