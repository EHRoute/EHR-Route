package ehroute.identityservice.utilities.app;

import static org.springframework.boot.web.error.ErrorAttributeOptions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.channels.*;
import java.util.Map;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.muizz.spring.mediator.payload.ApiResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Order(-2)
public class UnhandledErrorsHandler extends AbstractErrorWebExceptionHandler {

    @Value("${LOG_DB_ORG}")
    private String logDbOrg;
    @Value("${LOG_DB_BUCKET}")
    private String logDbBucket;

    @Autowired
    private InfluxDBClient logDb;

    public UnhandledErrorsHandler(
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
        return RouterFunctions.route(RequestPredicates.all(), this::handle);
    }


    private Mono<ServerResponse> handle(ServerRequest request) {

        // Persist error log
        Mono.just(request).publishOn(Schedulers.newParallel("logScheduler", 2))
        .doOnNext(r -> logError(r)).subscribe();

        // Return an internal server error API response
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(
            new ApiResponse(
                null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Map.of("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            )
        ));

    }


    private void logError(ServerRequest request) {

        var baos = new ByteArrayOutputStream();
        request.body(BodyExtractors.toDataBuffers()).collectList().subscribe(r -> {

            if (r.isEmpty()) persistErrorLog(request, StringUtils.EMPTY);
            else {
                r.forEach(buffer -> {
                    try {
                        Channels.newChannel(baos).write(buffer.asByteBuffer().asReadOnlyBuffer());
                        var requestBody = new String(baos.toByteArray(), StandardCharsets.UTF_8);
                        persistErrorLog(request, requestBody);
                    } catch (IOException e) {
                        persistErrorLog(request, StringUtils.EMPTY);
                    } finally {
                        try {
                            baos.close();
                        } catch (IOException e) {
                            persistErrorLog(request, StringUtils.EMPTY);
                        }
                    }
                });
            }

        });

    }


    private void persistErrorLog(ServerRequest request, String requestBody) {

        // Set error attribute options to include the errors stack traces
        ErrorAttributeOptions errorAttributeOpts = of(Include.STACK_TRACE);

        // Get errors map
        Map<String, Object> errorAttributes = getErrorAttributes(request, errorAttributeOpts);

        // Log and persist errors on logging DB
        try (WriteApi writeApi = logDb.getWriteApi()) {
            writeApi.writeMeasurement(logDbBucket, logDbOrg, WritePrecision.NS, new ErrorLogMeasurement(errorAttributes));
        }

    }

}
