package ehroute.identityservice.controllers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.muizz.spring.mediator.payload.ApiResponse;
import com.muizz.spring.mediator.core.Mediator;

import ehroute.identityservice.exceptions.ResourceNotFoundException;
import ehroute.identityservice.payload.requests.commands.CreateAccountCommand;
import ehroute.identityservice.utilities.app.ApiEndpoints;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;

@RestController(ApiEndpoints.Account.Main)
public class AccountController {


    private Mediator mediator;
    private MessageSource messageSource;

    @Autowired
    public AccountController(Mediator mediator, MessageSource messageSource) {
        this.mediator = mediator;
        this.messageSource = messageSource;
    }

    
    @GetMapping(ApiEndpoints.Account.Current)
    public Mono<Principal> current(Mono<Principal> principal) {
        return principal;
    }


    @PostMapping(value = ApiEndpoints.Account.Register)
    public Mono<ApiResponse> create(@RequestBody CreateAccountCommand command) throws Exception {
//        System.out.println(messageSource.getMessage("resource.not_found", new Object[] {"Account", "ID", 2}, locale));
        throw new ResourceNotFoundException("Account", 2);
        // return mediator.dispatch(command);
    }

}
