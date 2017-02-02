package View;

import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.modelAndView;
import static spark.Spark.post;

/**
 * Created by nilmor on 2/2/2017.
 */
public class OtherWishListView {
    public void RenderOtherWishlistView(){
        get("/Otherwishlist", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(currentUserLevel.equals("not registered")){
                res.redirect("/Home");
            }

            String Selecteduserwl = req.session().attribute("Selecteduserwl");
            System.out.println(req.session().attribute("Selecteduserwl") + " jemoeder");

            attributes.put("clickeduserwl", Selecteduserwl);

            Controller.GetUserData checkUser = new Controller.GetUserData();
            ArrayList<String> UserData = checkUser.getUserData(currentUser);
            attributes.put("firstname", UserData.get(0));
            attributes.put("lastname", UserData.get(1));
            attributes.put("age", UserData.get(2));
            attributes.put("emailaddress", UserData.get(3));
            attributes.put("userpassword", UserData.get(4));
            attributes.put("userstatus", UserData.get(5));
            attributes.put("userid", UserData.get(6));
            attributes.put("wishlist", UserData.get(7));

            Controller.GetWishlistInfo getWishlistData = new Controller.GetWishlistInfo();
            List getwishlistdata = getWishlistData.getWishlistinfo(Selecteduserwl);
            for (int i = 0; i < getwishlistdata.size(); i += 4){
                attributes.put("model", getwishlistdata.get(i) + ", " + attributes.get("model"));
                attributes.put("brand", getwishlistdata.get(i+1) + ", " + attributes.get("brand"));
                attributes.put("type", getwishlistdata.get(i+2) + ", " + attributes.get("type"));
                attributes.put("price", getwishlistdata.get(i+3) + ", " + attributes.get("price"));
            }

            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Otherwishlist.vm");
        }, new VelocityTemplateEngine());


        post("/Otherwishlist", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            return modelAndView(attributes, "Webshop/Otherwishlist.vm");
        },new VelocityTemplateEngine());
    }
}
