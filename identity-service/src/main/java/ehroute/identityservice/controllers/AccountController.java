package ehroute.identityservice.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muizz.spring.mediator.payload.ApiResponse;
import com.muizz.spring.mediator.core.Mediator;

import ehroute.identityservice.payload.requests.commands.CreateAccountCommand;
import ehroute.identityservice.utilities.app.ApiEndpoints;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController(ApiEndpoints.Account.Main)
public class AccountController {


    private Mediator mediator;

    @Autowired
    public AccountController(Mediator mediator) {
        this.mediator = mediator;
    }

    
    @GetMapping(ApiEndpoints.Account.Current)
    public Mono<Principal> current(Mono<Principal> principal) {
        return principal;
    }


    @PostMapping(ApiEndpoints.Account.Register)
    public Mono<ApiResponse> create(@RequestBody CreateAccountCommand command) {
        return mediator.dispatch(command);
    }

}
