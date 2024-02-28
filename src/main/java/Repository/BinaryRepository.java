package Repository;

import Domain.Entity;
import Repository.Excepions.RepositoryException;

import java.io.*;
import java.util.ArrayList;

public class BinaryRepository<T extends Entity> extends MemoryRepository<T> {

    protected String filename;

    public BinaryRepository(String filename) throws IOException, RepositoryException {
        // Initialize with an empty list
        this.filename = filename;
        readFromFile();
    }
    private void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            entities = (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Handle file not found (create an empty list)
            entities = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.reset();
            oos.writeObject(entities);
            System.out.println("Wrote entities to file: " + entities);
        } catch (IOException e) {
            // Handle IO exceptions
            e.printStackTrace();
        }
    }



    @Override
    public void add(T entity) throws RepositoryException, IOException {
        super.add(entity);
        writeToFile();
    }

    @Override
    public void remove(int id) throws RepositoryException {
        super.remove(id);
        writeToFile();
    }

    @Override
    public void update(T entity) throws RepositoryException {
        super.update(entity);
        writeToFile();
    }
}


