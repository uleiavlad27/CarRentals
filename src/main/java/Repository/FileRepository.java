package Repository;

import Domain.Entity;
import Domain.IEntityFactory;
import Repository.Excepions.RepositoryException;

import java.io.*;
import java.util.Scanner;

public class FileRepository<T extends Entity> extends MemoryRepository<T> {
    private final String filename;
    private IEntityFactory<T> entityFactory;

    public FileRepository(String filename, IEntityFactory<T> entityFactory) throws IOException, RepositoryException {
        this.filename = filename;
        this.entityFactory = entityFactory;
        readFromFile();
    }

    public FileRepository(String filepath) throws RepositoryException, IOException {
        this.filename = filepath;
        readFromFile();
    }

    public void readFromFile() throws IOException, RepositoryException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                T entity = entityFactory.createEntity(line);
                add(entity);
            }
        }
    }

    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (T object : getAll()) {
                String lineToWrite = entityFactory.makeString(object);
                writer.write(lineToWrite);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }


    public void add(T entity) throws RepositoryException, IOException {
        super.add(entity);
        writeToFile();
    }

    public void remove(int id) throws RepositoryException {
        super.remove(id);
        writeToFile();
    }

    public void update(T entity) throws RepositoryException{
        super.update(entity);
        writeToFile();
    }


}
