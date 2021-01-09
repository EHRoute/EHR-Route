package ehroute.accountservice.helpers.errorhandler;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;
import liquibase.pro.packaged.S;
import org.fluentd.logger.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchangeDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.nio.channels.Channels;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.Map.entry;


@Configuration
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private FluentLogger fluentLogger;

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        /*final ServerHttpRequest request = serverWebExchange.getRequest();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        request.getBody().doOnNext(dataBuffer -> {
            try {
                Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                String body = new String(baos.toByteArray());
                System.out.println(body);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/


        // Default with a HTTP 500 Error
        var apiError = ApiError.builder().errorType(ErrorsTypes.INTERNAL_SERVER_ERROR.toString()).message(ErrorsMessages.INTERNAL_SERVER_ERROR.toString()).build();
        serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Wrap the Api error in a data buffer and return it in response
        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        DataBuffer dataBuffer = null;
        try { dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(apiError)); }
        catch (JsonProcessingException e) { dataBuffer = bufferFactory.wrap(ErrorsMessages.INTERNAL_SERVER_ERROR.toString().getBytes()); }

        return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer)).doFinally(e -> {
            // Log the error
            generateRequestLogEntry(serverWebExchange, throwable);
        });

    }


    private void generateRequestLogEntry(ServerWebExchange webExchange, Throwable exception) {

        var requestHeaders = webExchange.getRequest().getHeaders().entrySet().stream().map(headerMap ->
            new StringBuilder()
            .append(headerMap.getKey())
            .append(":")
            .append(String.join(",", headerMap.getValue())))
        .collect(Collectors.joining(", ", "{", "}"));

        var responseHeaders = webExchange.getResponse().getHeaders().entrySet().stream().map(headerMap ->
            new StringBuilder()
                .append(headerMap.getKey())
                .append(":")
                .append(String.join(",", headerMap.getValue())))
        .collect(Collectors.joining(", ", "{", "}"));

        var log = Map.ofEntries(
            entry("errorType", ErrorsTypes.INTERNAL_SERVER_ERROR.toString()),
            entry("hostAddress", webExchange.getRequest().getRemoteAddress().getAddress().getHostAddress()),
            entry("requestEndpoint", webExchange.getRequest().getURI().getPath()),
            entry("requestHeaders", requestHeaders),
            entry("requestMethod", webExchange.getRequest().getMethodValue()),
            entry("requestQueryParameters", webExchange.getRequest().getQueryParams().toString()),
            entry("requestPayload", "{}"),
            entry("responseHeaders", responseHeaders),
            entry("responseMessage", ErrorsMessages.INTERNAL_SERVER_ERROR.toString()),
            entry("exceptionType", exception.getClass().getCanonicalName()),
            entry("exceptionMessage", exception.getMessage()),
            entry("exceptionCause", Throwables.getRootCause(exception).getMessage()),
            entry("exceptionStackTrace", Throwables.getStackTraceAsString(exception))
        );

        /*fluentLogger.log("UnhandledException", Collections.singletonMap("ApiError", log));*/

    }

}
