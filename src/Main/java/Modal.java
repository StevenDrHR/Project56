import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nilmor on 10/27/2016.
 */
public class Modal {
    private String username;
    private String password;
    private Date createdAt;
    private Integer id;
    private Boolean deleted;

    public Modal(String username, String password, Integer size) {
        this.username = username;
        this.password = password;
        this.createdAt = new Date();
        this.id = size;
        this.deleted = false;
    }
    public String getUsername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void delete() {
        this.deleted = true;
    }

    public Boolean readable() {
        return !this.deleted;
    }

    public String getCreatedAt() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(this.createdAt);
    }

    public String getEditLink() {
        return "<a href='/users/update/" + this.id + "'>Edit</a>";
    }

    public String getDeleteLink() {
        return "<a href='/users/delete/" + this.id + "'>Delete</a>";
    }

    public String getUsernameLink() {
        return "<a href='/users/read/" + this.id + "'>" + this.username + "</a>";
    }
}

