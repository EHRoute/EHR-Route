package ehroute.identityservice.entities.domain;

import java.time.OffsetDateTime;

import com.muizz.sajooq.entities.BaseEntity;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseEntity {

    private String email, address, password;
    @Column("public_key") private byte[] publicKey;
    @Column("last_login") private OffsetDateTime lastLogin;

}
