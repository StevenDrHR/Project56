package View;

import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.modelAndView;
import static spark.Spark.post;

/**
 * Created by nilmor on 2/2/2017.
 */
public class OrderHistoryView {
    public void RenderOrderHistoryView(){
        get("/Orderhistory", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(currentUserLevel.equals("not registered")){
                res.redirect("/Home");
            }

            if(req.session().attribute("message") == null ){
                attributes.put("message", "null");
            }
            else {
                attributes.put("message", req.session().attribute("message"));
                req.session().attribute("message", null);
            }

            //Getting the order history of the user
            Controller.GetOrderHistory setorders = new Controller.GetOrderHistory();
            Controller.GetUserData getUserInfo = new Controller.GetUserData();
            ArrayList orders = setorders.GetOrderHistory(getUserInfo.getUserData(currentUser).get(6));

            attributes.put("orders", orders);
            Controller.GetUserData checkUser = new Controller.GetUserData();

            ArrayList<String> UserData = checkUser.getUserData(currentUser);
            attributes.put("userid", UserData.get(6));
            return modelAndView(attributes, "Webshop/orderhistory.vm");
        }, new VelocityTemplateEngine());


        post("/Orderhistory", (req,res)-> {

            Map<String, Object> attributes = new HashMap<String, Object>();
            System.out.println( "Shamalalala");
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            //Checking User is admin or user
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            String variable =req.queryParams().iterator().next();

            if(variable.contains("Favourite")){//adding the order to favorites
                Controller.GetUserData userInfo = new Controller.GetUserData();
                Controller.AddFavourite AddFavourite = new Controller.AddFavourite();
                AddFavourite.addFavourite(variable.substring(10, variable.length()), userInfo.getUserData(currentUser).get(6));
                res.redirect("/Favourite");
            }


            Controller.GetUserData checkUser = new Controller.GetUserData();
            ArrayList<String> UserData = checkUser.getUserData(currentUser);
            attributes.put("userid", UserData.get(6));

            return modelAndView(attributes, "Webshop/orderhistory.vm");
        },new VelocityTemplateEngine());
    }
}
