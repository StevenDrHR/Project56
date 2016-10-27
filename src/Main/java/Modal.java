import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nilmor on 10/27/2016.
 */
public class Modal {
    //Initialize users for datatbase
    private String username;
    private String password;
    private Date createdAt;
    private Integer id;
    private Boolean deleted;
    private String email;
    private String Fname;
    private String Lname;
    //initialiaze database connection
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "123lol123";
    public Connection conn;


    public void DataCon() {
        //Connection
        try {
            //Driver name + credentials + ip address check.
           // Class.forName("org.postgresql.Driver");
            //System.out.println("Connecting to a selected database...");
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");
        }
        //When something goes horribly wrong
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UserModal(String username, String password, String Email,String FName,String LName) {
        this.username = username;
        this.password = password;
        this.createdAt = new Date();
        this.deleted = false;
        this.email = Email;
        this.Fname = FName;
        this.Lname = LName;
    }
    public String getUsername() {
        return username;
    }

    public String getpassword() {
        return password;
    }
    public String getEmail(){return email;}
    public String getFname(){return Fname;}
    public String getLname(){return Lname;}

    public void setUsername(String username) {this.username = username;}
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

