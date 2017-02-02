package Model;

/**
 * Created by nilmor on 2/2/2017.
 */
public class LoginModals {
    private String username;
    private String password;

    public void LoginModal(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getpassword() {
        return password;
    }
}
