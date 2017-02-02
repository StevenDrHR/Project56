package Model;

/**
 * Created by nilmor on 2/2/2017.
 */
public class AddProductModels {
    private String Model;
    private String Brand;
    private String Type;
    private String Year;
    private String Price;
    private String Deliverytime;
    private String Description;

    public void AddProductModal(String model, String brand, String type,String year,String price ,String deliveryTime,String description) {
        this.Model = model;
        this.Brand = brand;
        this.Type = type;
        this.Year = year;
        this.Price = price;
        this.Deliverytime = deliveryTime;
        this.Description = description;
    }
    public String getModal(){return  Model;}
    public String getBrand(){return  Brand;}
    public String getType(){return  Type;}
    public String getPrice(){return  Price;}
    public String getYear(){return  Year;}
    public String getDescription(){return  Description;}
    public String getDeliverytime(){return  Deliverytime;}
}
