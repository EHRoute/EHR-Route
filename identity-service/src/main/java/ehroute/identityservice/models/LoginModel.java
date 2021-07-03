package ehroute.identityservice.models;

import lombok.Data;

@Data
public class LoginModel {

    private String username;
    private String password;
    private String _csrf;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String get_csrf() {
        return _csrf;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

}
