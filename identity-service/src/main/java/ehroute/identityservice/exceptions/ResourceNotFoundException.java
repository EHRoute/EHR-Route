package ehroute.identityservice.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, long id) {
        super(String.format("%s with Id: %s was not found", resourceName, id));
    }
}
