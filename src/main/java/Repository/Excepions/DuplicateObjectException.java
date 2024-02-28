package Repository.Excepions;

public class DuplicateObjectException extends RepositoryException{
    public DuplicateObjectException(String message){
        super(message);
    }
}
