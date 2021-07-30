package ehroute.identityservice.controllers;

import java.security.Principal;
import java.util.Map;

import com.muizz.spring.jooq.utils.resource.ResourcePage;
import com.muizz.spring.jooq.utils.resource.ResourceSort;

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
import org.springframework.web.bind.annotation.RequestParam;



@RestController(ApiEndpoints.Account.Main)
public class AccountController {


    @Autowired Mediator mediator;

    
    @GetMapping(ApiEndpoints.Account.Current)
    public Mono<Principal> current(Mono<Principal> principal) {
        return principal;
    }


    @PostMapping(ApiEndpoints.Account.Register)
    public Mono<ApiResponse> postMethodName(@RequestBody CreateAccountCommand command) {
        return mediator.dispatch(command);
    }


    @GetMapping("/test")
    public Mono<Void> test(ResourcePage page, ResourceSort sort, @RequestParam Map<String, String> filters) {
        var ttt = sort.getOrder().toString();
        return Mono.empty();
    }


}
