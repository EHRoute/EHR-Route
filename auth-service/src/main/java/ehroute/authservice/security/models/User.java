package ehroute.authservice.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;

    private Long id;
    @Getter @Setter private String email;
    @Getter @Setter @JsonIgnore private String password;
    @Getter @Setter private boolean enabled;
    @Getter @Setter private List<Role> roles;



    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }


    public boolean isAccountNonExpired() {
        return false;
    }


    public boolean isAccountNonLocked() {
        return false;
    }


    public boolean isCredentialsNonExpired() {
        return false;
    }


    public boolean isEnabled() {
        return enabled;
    }
}
