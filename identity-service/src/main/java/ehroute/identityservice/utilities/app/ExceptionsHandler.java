package ehroute.identityservice.utilities.app;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import ehroute.identityservice.exceptions.ResourceNotFoundException;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class ExceptionsHandler implements WebExceptionHandler {
    
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        
        if (ex instanceof ResourceNotFoundException) {

            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);

            // marks the response as complete and forbids writing to it
            return exchange.getResponse().setComplete();

        }

        return Mono.error(ex);
    }

}
