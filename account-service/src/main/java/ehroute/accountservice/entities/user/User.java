package ehroute.accountservice.entities.user;
import ehroute.accountservice.entities.app.Auditable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("Users")
public class User {

    @Id private Long Id;
    private String email, password, address;
    @Column(value = "last_login") private OffsetDateTime lastLogin;
    @Column(value = "created_at") private OffsetDateTime createdAt;
}
