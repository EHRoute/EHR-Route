package ehroute.accountservice.helpers.mappers;
import ehroute.accountservice.entities.user.User;
import ehroute.accountservice.payload.commands.CreateAccountCommand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(CreateAccountCommand createUserCommand);

}
