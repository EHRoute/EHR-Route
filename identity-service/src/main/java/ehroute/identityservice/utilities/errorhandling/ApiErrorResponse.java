package ehroute.identityservice.utilities.errorhandling;

import java.io.Serializable;

public class ApiErrorResponse {

    private String message;
    private String code;

    public ApiErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
