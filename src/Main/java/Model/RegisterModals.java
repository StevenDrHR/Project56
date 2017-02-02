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
        this.username = username;
        this.password = password;
        this.createdAt = new Date();
        this.deleted = false;
        this.email = Email;
        this.Fname = FName;
        this.Lname = LName;
        this.Age = Age;
        this.Street =Street;
        this.StreetNumber=StreetNumber;
        this.Country = Country;
        this.PostalCode = PostalCode;

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
    public String getAge(){return Age;}
    public String getStreet(){return Street;}
    public String getStreetNumber(){return StreetNumber;}
    public String getCounrty(){return Country;}
    public String getPostalCode(){return PostalCode;}
}
