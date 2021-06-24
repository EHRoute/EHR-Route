package ehroute.authservice.security.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    @JsonIgnore private String password;
    private boolean enabled;
    private List<Role> roles;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

}
