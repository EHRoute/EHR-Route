package ehroute.accountservice.handlers.account;
import com.tej.JooQDemo.jooq.sample.model.tables.Users;
import ehroute.accountservice.helpers.mappers.UserMapper;
import ehroute.accountservice.helpers.requestdispatcher.RequestHandler;
import ehroute.accountservice.payload.commands.CreateAccountResult;
import ehroute.accountservice.payload.response.ApiResponse;
import ehroute.accountservice.payload.commands.CreateAccountCommand;
import ehroute.accountservice.repositories.account.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;



@Component
@Slf4j
public class CreateAccountHandler implements RequestHandler<CreateAccountCommand, Mono<CreateAccountResult>> {

    // <editor-fold desc="Dependencies">

    @Autowired private AccountRepository accountRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    // </editor-fold>


    // <editor-fold desc="Handler">

    @Override
    public Mono<CreateAccountResult> handle(CreateAccountCommand command) {

        // Map command to user
        var user = userMapper.toUser(command);

        // Encode and set user password
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Generate and set user address
        // user.setAddress(UuidUtil.generateUUID());

        // TODO: Set user role

        // Insert the user
        // accountRepository.insert(user, Users.USERS).subscribe();

        return Mono.just(new CreateAccountResult(command.getEmail()));
    }

    // </editor-fold>A

}
