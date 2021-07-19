package ehroute.identityservice.models.view;

import lombok.Data;

@Data
public class LoginModel {

    private String username;
    private String password;
    private boolean remember;
    private String _csrf;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getRemember() {
        return remember;
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

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

}
