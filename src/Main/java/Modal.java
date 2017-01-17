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
    private String Age;
    private String Street;
    private String StreetNumber;
    private String Country;
    private String PostalCode;
    private String Modal;
    private String Brand;
    private String Type;
    private String Year;
    private String Price;
    private String Deliverytime;
    private String Description;

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

    public void LoginModal(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void AddProductModal(String modal, String brand, String type,String year,String price ,String deliveryTime,String description) {
        this.Modal = modal;
        this.Brand = brand;
        this.Type = type;
        this.Year = year;
        this.Price = price;
        this.Deliverytime = deliveryTime;
        this.Description = description;
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
    public String getModal(){return  Modal;}
    public String getBrand(){return  Brand;}
    public String getType(){return  Type;}
    public String getYear(){return  Year;}
    public String getPrice(){return  Price;}
    public String getDeliverytime(){return  Deliverytime;}
    public String getDescription(){return  Description;}




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

