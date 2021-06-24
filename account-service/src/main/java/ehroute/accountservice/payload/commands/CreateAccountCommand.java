package ehroute.accountservice.payload.commands;
import ehroute.accountservice.helpers.requestdispatcher.ApiRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountCommand extends ApiRequest<CreateAccountResult> {

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,100}$", message = "Password should be at least 8 characters long, and should include one capital alphabet, and one number")
    String password;

}
