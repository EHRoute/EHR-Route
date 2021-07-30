package ehroute.identityservice.payload.requests.commands;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.muizz.spring.mediator.payload.ApiResponse;
import com.muizz.spring.mediator.payload.ApiRequest;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
public class CreateAccountCommand implements ApiRequest<Mono<ApiResponse>> {
    
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,100}$", message = "Password should be at least 8 characters long, and should include one capital alphabet, and one number")
    String password;

}
