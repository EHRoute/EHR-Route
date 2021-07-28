package ehroute.identityservice.repositories.account;

import com.muizz.spring.jooq.utils.repository.DefaultResourceRepository;

import org.springframework.stereotype.Repository;

import ehroute.identityservice.entities.db.tables.Accounts;
import ehroute.identityservice.entities.db.tables.records.AccountsRecord;
import ehroute.identityservice.entities.domain.Account;

@Repository
public class AccountRepositoryImpl extends DefaultResourceRepository<Account, AccountsRecord, Accounts>
implements AccountRepository {

}
