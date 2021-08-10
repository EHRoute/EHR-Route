package ehroute.identityservice.utilities.app;

import static org.springframework.boot.web.error.ErrorAttributeOptions.*;

import java.util.Map;
import java.util.Optional;

import com.muizz.spring.mediator.payload.ApiResponse;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorHandler(
        ErrorAttributes errorAttributes,
        Resources resources,
        ApplicationContext context,
        ServerCodecConfigurer codecConfigurer
    ) {
        super(errorAttributes, resources, context);

        // Configure HTTP message writers
        this.setMessageWriters(codecConfigurer.getWriters());
    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        // For all HTTP methods with errors, return the formatted error message
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }


    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {

        // Set error attribute options to include the errors stack traces
        ErrorAttributeOptions errorAttributeOpts = of(Include.STACK_TRACE);

        // Get errors map
        Map<String, Object> errorAttributesMap = getErrorAttributes(request, errorAttributeOpts);

        // Get status from errors
        int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);

        // TODO: Log and persist errors on InfluxDb
        // TODO: Handle validation errors
        // TODO: Add custom exceptions' errors and their statuses

        // Return an ApiResponse with the errors
        return ServerResponse
        .status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(new ApiResponse(null, false, status, errorAttributesMap)));

    }
    
}
