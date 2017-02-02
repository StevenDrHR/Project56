package Model;

import java.util.Date;

/**
 * Created by nilmor on 2/2/2017.
 */
public class RegisterModals {
    private String username;
    private String password;
    private Date createdAt;
    private Integer id;
    private Boolean deleted;
    private String email;
    private String Fname;
    private String Lname;
    private String Age;
    private String Street;
    private String StreetNumber;
    private String Country;
    private String PostalCode;

    public void RegisterModal(String username, String password, String Email,String FName,String LName ,String Age,String Street,String StreetNumber,String Country,String PostalCode) {
        this.username = username; //Setting the username value
        this.password = password; //Setting the password value
        this.createdAt = new Date(); //Setting the Date value
        this.deleted = false; //Setting the deleted value
        this.email = Email; //Setting the Email value
        this.Fname = FName; //Setting the FName value
        this.Lname = LName; //Setting the LName value
        this.Age = Age; //Setting the Age value
        this.Street =Street; //Setting the Street value
        this.StreetNumber=StreetNumber; //Setting the StreetNumber value
        this.Country = Country; //Setting the Country value
        this.PostalCode = PostalCode; //Setting the PostalCode value

    }
    public String getUsername() {
        return username;
    } //Getting the username value
    public String getpassword() {
        return password;
    } //Getting the username value
    public String getEmail(){return email;} //Getting the username value
    public String getFname(){return Fname;} //Getting the username value
    public String getLname(){return Lname;} //Getting the username value
    public String getAge(){return Age;} //Getting the username value
    public String getStreet(){return Street;} //Getting the username value
    public String getStreetNumber(){return StreetNumber;} //Getting the username value
    public String getCounrty(){return Country;} //Getting the username value
    public String getPostalCode(){return PostalCode;} //Getting the username value
}
