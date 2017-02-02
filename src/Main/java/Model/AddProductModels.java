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
        this.Model = model; //Setting the Model value
        this.Brand = brand; //Setting the Brand value
        this.Type = type; //Setting the Type value
        this.Year = year; //Setting the Year value
        this.Price = price; //Setting the Price value
        this.Deliverytime = deliveryTime; //Setting the Deliverytime value
        this.Description = description; //Setting the Description value
    }
    public String getModal(){return  Model;} //Getting the Model value
    public String getBrand(){return  Brand;}//Getting the Brand value
    public String getType(){return  Type;}//Getting the Type value
    public String getPrice(){return  Price;}//Getting the Price value
    public String getYear(){return  Year;}//Getting the Year value
    public String getDescription(){return  Description;}//Getting the Description value
    public String getDeliverytime(){return  Deliverytime;}//Getting the Deliverytime value
}
