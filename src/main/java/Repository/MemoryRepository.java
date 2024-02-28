package Repository;

import Domain.Entity;
import Repository.Excepions.DuplicateObjectException;
import Repository.Excepions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MemoryRepository<T extends Entity> implements Repository<T> {

    List<T> entities = new ArrayList<>();



    @Override
    public void add(T entity) throws RepositoryException, IOException {
        if(entity == null)
            throw new IllegalArgumentException("Entity can't be null");
        if(!isIdUnique(entity.getId()))
            throw new DuplicateObjectException("Entity with the same ID already exists");
        entities.add(entity);
    }

    @Override
    public void remove(int id) throws RepositoryException {
        if(find(id) == null)
            throw new IllegalArgumentException("Entity doesn't exist");
        entities.removeIf(entities -> entities.getId() == id);
    }

    @Override
    public void update(T obj) throws RepositoryException {
        if(find(obj.getId()) == null)
            throw new IllegalArgumentException("Entity doesn't exist");
        int index = -1;
        for(int i = 0; i <entities.size(); i++) {
            if (entities.get(i).getId() == obj.getId()) {
                index = i;
                break;
            }
        }
        if(index != -1)
            entities.set(index, obj);
    }


    @Override
    public T find(int id) {
        for(T entity : entities){
            if(entity.getId() == id){
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return entities;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<>(entities).iterator();
    }

    public int size(){
        return entities.size();
    }

    public boolean isIdUnique(int id){
        for (T item : entities) {
            if (item.getId() == id) {
                return false;
            }
        }
        return true;
    }
}
