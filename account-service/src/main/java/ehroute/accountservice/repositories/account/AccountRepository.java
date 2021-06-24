package ehroute.accountservice.repositories.account;
import com.tej.JooQDemo.jooq.sample.model.tables.Users;
import com.tej.JooQDemo.jooq.sample.model.tables.records.UsersRecord;
import ehroute.accountservice.entities.user.User;
import ehroute.accountservice.repositories.base.BaseRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;


@Component
public class AccountRepository extends BaseRepository<User, UsersRecord, Users> implements IAccountRepository {

    // <editor-fold desc="Dependencies">

    @Autowired private DSLContext context;
    @Autowired private R2dbcEntityTemplate db;

    // </editor-fold>


    // <editor-fold desc="Methods">



    // </editor-fold>

}
