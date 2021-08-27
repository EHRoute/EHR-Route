package ehroute.identityservice.utilities.app;

import com.muizz.spring.mediator.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import java.util.Locale;
import java.util.Objects;

@Component
@Order(-2)
public class ExceptionsHandler implements WebExceptionHandler {

    @Autowired private MessageSource messageSource;
    
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        var requestLocale = Objects.requireNonNullElse(exchange.getLocaleContext().getLocale(), Locale.getDefault());
        var response = new ApiResponse.Builder().failed();
        var apiException = getExceptionMetadata(ex);

        // Set the message and status code according to the thrown exception
        if (ex instanceof ResourceNotFoundException && apiException != null) {
            response.withError(new ApiErrorResponse(
                    messageSource.getMessage(apiException.messageSource(), new Object[] {"Account", "ID", ((ResourceNotFoundException) ex).getId()}, requestLocale),
                    apiException.errorCode()
            )).ofStatus(HttpStatus.NOT_FOUND.value());;
        }

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
