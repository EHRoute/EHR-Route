package ehroute.identityservice.repositories.account;

import org.springframework.stereotype.Repository;

import ehroute.identityservice.entities.db.tables.Accounts;
import ehroute.identityservice.entities.db.tables.records.AccountsRecord;
import ehroute.identityservice.entities.domain.Account;
import ehroute.identityservice.repositories.DefaultResourceRepository;

@Repository
public class AccountRepositoryImpl extends DefaultResourceRepository<Account, AccountsRecord, Accounts>
implements AccountRepository {

}
