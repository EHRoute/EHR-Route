package ehroute.identityservice.helpers.mediator;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an API response 
 */
@Data
@AllArgsConstructor
public class ApiResponse {
    private Object payload;     // Json data of the response
    private boolean success;    // Success status
    private int status;         // Http Status Code
    private Object error;       // Json of error messages or warnings
}
