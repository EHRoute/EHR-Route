package ehroute.identityservice.exceptions;

import org.springframework.http.HttpStatus;

import ehroute.identityservice.utilities.errorhandling.ApiException;

@ApiException(statusCode = HttpStatus.NOT_FOUND, errorCode = "RESOURCE_NOT_FOUND", messageSource = "resource.not_found")
public class ResourceNotFoundException extends RuntimeException {

    private long id;

    public ResourceNotFoundException(String resourceName, long id) {
        super(String.format("%s with Id: %s was not found", resourceName, id));
        this.id = id;
    }

    public long getId() { return id; }

}
