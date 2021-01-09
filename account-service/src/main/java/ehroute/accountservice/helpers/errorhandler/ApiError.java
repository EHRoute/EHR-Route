package ehroute.accountservice.helpers.errorhandler;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


// <editor-fold desc="Api Error DTO">

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private String errorType;
    private List<FieldError> fieldErrors;

    public void addFieldError(String field, String message) {
        if (fieldErrors == null) fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError(field, message));
    }

}

// </editor-fold>


// <editor-fold desc="Field Error DTO">

@AllArgsConstructor
@Getter
class FieldError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String field;
    private final String message;

}

//</editor-fold>


// <editor-fold desc="Errors Types">

@AllArgsConstructor
enum ErrorsTypes {

    CONCURRENCY_FAILURE("error.concurrencyFailure"),
    ACCESS_DENIED("error.accessDenied"),
    VALIDATION_ERROR("error.validation"),
    METHOD_NOT_SUPPORTED("error.methodNotSupported"),
    INTERNAL_SERVER_ERROR("error.internalServerError");

    private final String errorType;
    @Override public String toString() { return errorType; }

}

@AllArgsConstructor
enum ErrorsMessages {

    INTERNAL_SERVER_ERROR("Service Unavailable");

    private final String errorMessage;
    @Override public String toString() { return errorMessage; }

}

// </editor-fold>
