package ehroute.identityservice.repositories.account;

import com.muizz.sajooq.repository.ResourceRepository;

import org.springframework.stereotype.Repository;

import ehroute.identityservice.entities.db.tables.Accounts;
import ehroute.identityservice.entities.db.tables.records.AccountsRecord;
import ehroute.identityservice.entities.domain.Account;

@Repository
public interface AccountRepository extends ResourceRepository<Account, AccountsRecord, Accounts> {
    
}
