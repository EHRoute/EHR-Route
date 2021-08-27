package ehroute.identityservice.utilities.errorhandling;

import java.io.Serializable;
import java.util.List;

public class ApiFieldErrorResponse extends ApiErrorResponse implements Serializable {

    List<FieldError> fieldErrors;

    public ApiFieldErrorResponse(String message, String code) {
        super(message, code);
    }

    public class FieldError implements Serializable {
        private String code;
        private String property;
        private String message;
        private String rejectedValue;
    }

}
