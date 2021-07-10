package ehroute.identityservice.helpers.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Resolves API requests by dispatching them to their assigned handlers and returning their response
 */
@Component
public class MediatorImpl implements Mediator {

    private final ReactiveWebApplicationContext context; // AbstractApplicationContext


    @Autowired
    public MediatorImpl(
        final ReactiveWebApplicationContext context
    ) {
        this.context = context;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T dispatch(Request<T> request) {

        // Validate request
        if (request == null) throw new NullPointerException("Null request");

        // Get the dispatched request type
        final Class<?> requestType = request.getClass();

        // Get the request's response type
        Class<T> responseClass;
        Type responseType = ((ParameterizedType) (requestType.getGenericInterfaces())[0]).getActualTypeArguments()[0];
        if (responseType instanceof ParameterizedType) responseClass = (Class<T>) ((ParameterizedType) responseType).getRawType();
        else responseClass = (Class<T>) responseType;

        // Retrieve the hanlders beans based on request and response types.
        final String[] handlers = context.getBeanNamesForType(ResolvableType.forClassWithGenerics(Handler.class, requestType, responseClass));

        // Validate handler existence
        if (handlers.length == 0) throw new IllegalStateException("No handler is registered to handle the Request");

        // Validate single handler
        if (handlers.length > 1) throw new IllegalStateException("More than one handlers found");

        // Get the handler
        final Handler<Request<T>, T> handler = (Handler<Request<T>, T>) context.getBean(handlers[0]);

        // Return the handler's response
        return handler.handle(request);

    }

}
