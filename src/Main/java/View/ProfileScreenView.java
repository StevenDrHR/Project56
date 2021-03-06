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
public class ProfileScreenView {
    public void RenderProfileView(){
        get("/Profile", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}
            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(currentUserLevel.equals("not registered")){//redirect if not registerd
                res.redirect("/Home");
            }

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

            //getting all public wishlists
            Controller.GetPublicusers getAllUsers = new Controller.GetPublicusers();
            List getallusers = getAllUsers.getPublicusers(currentUser);
            for (int i = 0; i < getallusers.size(); i ++) {
                if (i == 0) {
                    attributes.put("usernames", getallusers.get(i));
                }
                else {
                    attributes.put("usernames", getallusers.get(i) + ", " + attributes.get("usernames"));
                }
            }
            //getting all wishlist data of a wishlist
            Controller.GetWishlistInfo getWishlistData = new Controller.GetWishlistInfo();
            List getwishlistdata = getWishlistData.getWishlistinfo(currentUser);
            for (int i = 0; i < getwishlistdata.size(); i += 4){

                attributes.put("model", getwishlistdata.get(i) + ", " + attributes.get("model"));
                attributes.put("brand", getwishlistdata.get(i+1) + ", " + attributes.get("brand"));
                attributes.put("type", getwishlistdata.get(i+2) + ", " + attributes.get("type"));
                attributes.put("price", getwishlistdata.get(i+3) + ", " + attributes.get("price"));

            }

            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Profile.vm");
        }, new VelocityTemplateEngine());


        post("/Profile", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

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

            String button = req.queryParams().iterator().next();

            System.out.println(button + " Shamala");

            if (button.equals("makewishlistpublic")) {//make wishlist public
                System.out.println(button + " Shamala2");
                Controller.SetWishlistPublic setWishlisPublic = new Controller.SetWishlistPublic();
                setWishlisPublic.setWishlistToPublic(currentUser);
                res.redirect("/Profile");
            }
            else if (button.equals("makewishlistprivate")){// make wishlist private
                System.out.println(button +" Shamala3");
                Controller.SetWishlistPrivate setWishlisPrivate = new Controller.SetWishlistPrivate();
                setWishlisPrivate.setWishlistToPrivate(currentUser);
                res.redirect("/Profile");
            }

            return modelAndView(attributes, "Webshop/Profile.vm");
        },new VelocityTemplateEngine());
    }
}
