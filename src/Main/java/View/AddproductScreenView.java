package View;


import Controller.AddProduct;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.modelAndView;
import static spark.Spark.post;

/**
 * Created by nilmor on 2/2/2017.
 */
public class AddproductScreenView {
    Deque<Model.AddProductModels> addProducts = new ArrayDeque<Model.AddProductModels>();
    public void RenderAddProductView(){
        get("/AddProduct", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel UserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = UserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){ //Redirecting if not admin
                res.redirect("/Home");
            }
            return modelAndView(attributes, "Webshop/addproduct.vm");
        },new VelocityTemplateEngine());

        post("/AddProduct", (request, response) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            //Checking User is admin or user
            String currentUser = request.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel UserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = UserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){ //Redirecting if not admin
                response.redirect("/Home");
            }
            String button = request.queryParams().iterator().next();
            System.out.println(button + " Shamala");
            String modal = request.queryParams("Model");
            String brand = request.queryParams("Brand");
            String type = request.queryParams("CarType");
            String year = request.queryParams("BuildYear");
            String price = request.queryParams("Price");
            String deliveryTime = request.queryParams("DeleiveryTime");
            String description = request.queryParams("Description");

            System.out.println("" + brand);



            if(button.equals("Brand") ){// if add to product is pressed store the values and the send them to the database
                Model.AddProductModels addProduct = new Model.AddProductModels();
                addProduct.AddProductModal(modal, brand, type, year, price, deliveryTime, description);
                addProducts.addFirst(addProduct);
                String AddProduct = new AddProduct().AddProducts(addProducts);
                attributes.put("message", AddProduct);

            }
            else if(button.equals("return_button") ){// return button is pressed then go to the admin page
                response.redirect("/Adminpage");
            }
            return modelAndView(attributes, "Webshop/addproduct.vm");
        },new VelocityTemplateEngine());
    }
}
