package Model;

/**
 * Created by nilmor on 2/2/2017.
 */
public class LoginModals {
    private String username;
    private String password;

    public void LoginModal(String username, String password) {
        this.username = username; //Setting the username value
        this.password = password;//Setting the password value
    }
    public String getUsername() {
        return username;
    }//Getting the username value
    public String getpassword() {
        return password;
    }//Getting the password value
}
