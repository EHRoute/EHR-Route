package ehroute.accountservice.payload.queries;
import ehroute.accountservice.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.time.OffsetDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
public class FindAccountQuery {

    @Min(1) private Long Id;
    @Email(message = "Invalid email") private String email;
    private String address;
    private OffsetDateTime lastLogin;
    private OffsetDateTime createdAt;

    public FindAccountQuery(Long Id) {
        this.Id = Id;
    }

}
