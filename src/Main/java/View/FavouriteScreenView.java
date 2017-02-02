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
public class FavouriteScreenView {
    public void RenderFavouriteView(){
        get("/Favourite", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}
            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(currentUserLevel.equals("not registered")){//if not registered redirect to home
                res.redirect("/Home");
            }

            //Getting all the favorite data
            Controller.GetFavourites getFavourites = new Controller.GetFavourites();
            Controller.GetUserData getUserInfo = new Controller.GetUserData();
            ArrayList<String> favourites = getFavourites.getFavourites(getUserInfo.getUserData(currentUser).get(6));
            attributes.put("favourites", favourites);

            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Favourite.vm");
        }, new VelocityTemplateEngine());


        post("/Favourite", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            String deleteUser = req.queryParams().iterator().next();


            req.session().attribute("message");

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

            String buttonuser = req.queryParams().iterator().next();


            return modelAndView(attributes, "Webshop/Favourite.vm");
        },new VelocityTemplateEngine());
    }
}
