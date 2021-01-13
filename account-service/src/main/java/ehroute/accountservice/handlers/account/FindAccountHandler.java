package ehroute.accountservice.handlers.account;
import com.tej.JooQDemo.jooq.sample.model.tables.Users;
import ehroute.accountservice.entities.user.User;
import ehroute.accountservice.payload.queries.FindAccountQuery;
import ehroute.accountservice.repositories.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class FindAccountHandler {

    // <editor-fold desc="Dependencies">

    @Autowired private AccountRepository accountRepository;

    // </editor-fold>


    // <editor-fold desc="Handler">

    public String handle(FindAccountQuery command) {
        // var user = accountRepository.findById(command.getId(), User.class, Users.USERS);
        // var users = accountRepository.findAllByField("EMAIL", "string@gmail.com", User.class, Users.USERS);
        // return accountRepository.findAll();
        return "Hello World";
    }

    // </editor-fold>

}
