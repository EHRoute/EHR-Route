package ehroute.identityservice.utilities.app;

import com.muizz.spring.mediator.payload.ApiResponse;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import ehroute.identityservice.exceptions.ResourceNotFoundException;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.Objects;

@Component
@Order(-2)
public class ExceptionsHandler implements WebExceptionHandler {
    
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        var requestLocale = exchange.getLocaleContext().getLocale();
        var response = new ApiResponse.Builder().failed();
        var apiException = getExceptionMetadata(ex);

        // Set the response's error message and code with the exception's specified message and code
        if (apiException != null) response.withError(new ApiErrorResponse(ex.getMessage(), apiException.errorCode()));

        // Set the status code according to the thrown exception
        if (ex instanceof ResourceNotFoundException) response.ofStatus(HttpStatus.NOT_FOUND.value());

        // Build response body
        var responseBody = response.build();

        // Set the response's HTTP status code and content type
        exchange.getResponse().setStatusCode(HttpStatus.valueOf(responseBody.getStatus()));
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Return response
        return exchange.getResponse()
        .writeWith(new Jackson2JsonEncoder().encode(
            Mono.just(responseBody),
            exchange.getResponse().bufferFactory(),
            ResolvableType.forInstance(exchange),
            MediaType.APPLICATION_JSON, null
        ));

    }

    private ApiException getExceptionMetadata(Object apiException) {
        return apiException.getClass().getAnnotation(ApiException.class);
    }

}
