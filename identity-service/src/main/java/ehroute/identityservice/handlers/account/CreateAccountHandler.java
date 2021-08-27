package ehroute.identityservice.handlers.account;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ehroute.identityservice.entities.db.tables.Accounts;
import ehroute.identityservice.entities.domain.Account;
import com.muizz.spring.mediator.annotations.RequestHandler;
import com.muizz.spring.mediator.payload.ApiResponse;
import com.muizz.spring.mediator.core.Handler;
import ehroute.identityservice.payload.requests.commands.CreateAccountCommand;
import ehroute.identityservice.repositories.account.AccountRepository;
import reactor.core.publisher.Mono;

@RequestHandler
public class CreateAccountHandler implements Handler<CreateAccountCommand, Mono<ApiResponse>> {


    @Autowired private AccountRepository accountRepository;


    @Override
    public Mono<ApiResponse> handle(CreateAccountCommand request) {

        // Construct Account
        var account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setAddress(UUID.randomUUID().toString());
        account.setCreatedOn(OffsetDateTime.now());
        account.setPublicKey(account.getAddress().getBytes());

        // Persist Account
        return accountRepository.insert(account, Accounts.ACCOUNTS).flatMap(acc -> {
            return Mono.just(new ApiResponse.Builder().successful().withPayload(acc).ofStatus(HttpStatus.CREATED.value()).build());
        });

    }

}
