package ehroute.accountservice.payload.response;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiResponse {
    boolean success;
    String message;
}
