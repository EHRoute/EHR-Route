package ehroute.accountservice.payload.commands;
import ehroute.accountservice.helpers.requestdispatcher.RequestResult;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CreateAccountResult extends RequestResult {
    String result;
}
