package Repository.Excepions;

public class RepositoryException extends Exception{
    public RepositoryException(String message, ClassNotFoundException e){
        super(message);
    }

    public RepositoryException(String message) {
        super(message);
    }
}
