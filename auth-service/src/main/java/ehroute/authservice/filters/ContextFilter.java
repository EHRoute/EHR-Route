package ehroute.authservice.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class ContextFilter implements WebFilter {

    private ServerProperties serverProperties;

    @Autowired
    public ContextFilter(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        final String contextPath = serverProperties.getServlet().getContextPath();
        final ServerHttpRequest request = exchange.getRequest();

        // If the request starts with the context path ("/auth")
        if (request.getURI().getPath().startsWith(contextPath)) {
            // Remove the context path and move on
            return chain.filter(
                exchange.mutate().request(
                    request.mutate().path(request.getURI().getPath().replace(contextPath, "")).build()
                ).build()
            );
        }

        return chain.filter(exchange);
    }

}
