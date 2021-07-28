package ehroute.identityservice.handlers.account;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ehroute.identityservice.entities.db.tables.Accounts;
import ehroute.identityservice.entities.domain.Account;
import ehroute.identityservice.helpers.annotations.RequestHandler;
import ehroute.identityservice.helpers.mediator.ApiResponse;
import ehroute.identityservice.helpers.mediator.Handler;
import ehroute.identityservice.models.resource.ResourcePage;
import ehroute.identityservice.models.resource.ResourceQuery;
import ehroute.identityservice.models.resource.ResourceSort;
import ehroute.identityservice.models.resource.SortOrder;
import ehroute.identityservice.payload.requests.commands.CreateAccountCommand;
import ehroute.identityservice.repositories.account.AccountRepository;
import ehroute.identityservice.repositories.account.AccountRepositoryImpl;
import reactor.core.publisher.Mono;

@RequestHandler
public class CreateAccountHandler implements Handler<CreateAccountCommand, Mono<ApiResponse>> {


    @Autowired private AccountRepository accountRepository;


    @Override
    public Mono<ApiResponse> handle(CreateAccountCommand request) {

        var account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setAddress(UUID.randomUUID().toString());
        account.setCreatedOn(OffsetDateTime.now());
        account.setPublicKey(account.getAddress().getBytes());

        // return accountRepository.insert(account, Accounts.ACCOUNTS).flatMap(acc -> {
        //     return Mono.just(new ApiResponse(acc, true, HttpStatus.CREATED.value(), null));
        // });
        
        var query = new ResourceQuery();
        var page = new ResourcePage();
        // page.setNumber(1);
        // page.setSize(2);
        page.setLimit(3);
        page.setSeekId(1l);
        query.setPage(page);

        return accountRepository.findAllByQuery(query, Accounts.ACCOUNTS).collectList().flatMap(acc -> {
            return Mono.just(new ApiResponse(acc, true, HttpStatus.CREATED.value(), null));
        });

    }

}
