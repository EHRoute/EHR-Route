package ehroute.identityservice.utilities.errorhandling;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import org.apache.commons.lang3.StringUtils;

@Measurement(name = "unhandled-error-log")
public class ErrorLogMeasurement {
    
    @Column(tag = true) String requestId;
    @Column String path;
    @Column int status;
    @Column String error;
    @Column String stackTrace;
    @Column(timestamp = true) Instant time;


    public ErrorLogMeasurement(Map<String, Object> handlerErrorAttributes) {
        requestId = Objects.requireNonNullElse(handlerErrorAttributes.get("requestId").toString(), StringUtils.EMPTY);
        path = Objects.requireNonNullElse(handlerErrorAttributes.get("path").toString(), StringUtils.EMPTY);
        status = Objects.requireNonNullElse((int) handlerErrorAttributes.get("status"), 500);
        error = Objects.requireNonNullElse(handlerErrorAttributes.get("error").toString(), StringUtils.EMPTY);
        stackTrace = Objects.requireNonNullElse(handlerErrorAttributes.get("trace").toString(), StringUtils.EMPTY);
        time = Instant.now();
    }


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

}
