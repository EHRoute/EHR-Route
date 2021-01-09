package ehroute.accountservice.helpers.errorhandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;


@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Mono<ApiError> handleValidationErrors(WebExchangeBindException ex) {

        ApiError apiError = new ApiError();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            apiError.setMessage("Some fields are invalid");
            apiError.setErrorType(ErrorsTypes.VALIDATION_ERROR.toString());
            var fieldError = ((FieldError) error);
            apiError.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return Mono.just(apiError);

    }

}
