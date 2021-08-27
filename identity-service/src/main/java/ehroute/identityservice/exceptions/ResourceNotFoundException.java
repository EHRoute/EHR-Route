package ehroute.identityservice.exceptions;

import org.springframework.http.HttpStatus;

import ehroute.identityservice.utilities.app.ApiException;

@ApiException(statusCode = HttpStatus.NOT_FOUND, errorCode = "RESOURCE_NOT_FOUND", messageSource = "resource.not_found")
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, long id) {
        super(String.format("%s with Id: %s was not found", resourceName, id));
    }
}
