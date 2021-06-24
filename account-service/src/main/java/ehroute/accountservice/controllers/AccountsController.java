package ehroute.accountservice.controllers;
import ehroute.accountservice.entities.user.User;
import ehroute.accountservice.helpers.requestdispatcher.RequestDispatcher;
import ehroute.accountservice.models.app.ApiRoutes;
import ehroute.accountservice.payload.commands.CreateAccountCommand;
import ehroute.accountservice.payload.commands.CreateAccountResult;
import ehroute.accountservice.payload.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class AccountsController {

    // <editor-fold desc="Beans">

    private final RequestDispatcher requestDispatcher;

    public AccountsController(RequestDispatcher requestDispatcher) {
        this.requestDispatcher = requestDispatcher;
    }

    // </editor-fold>


    // <editor-fold desc="Endpoints">

    @PostMapping(ApiRoutes.Account.Main)
    public Mono<CreateAccountResult> create(@RequestBody @Valid CreateAccountCommand command) throws Exception {
        return requestDispatcher.dispatch(command);
    }

    // </editor-fold>

}
