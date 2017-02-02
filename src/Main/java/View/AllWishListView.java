package View;

import Controller.GetWishlistInfo;
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
public class AllWishListView {
    public void RenderAllWishlistView(){
        get("/Allwishlist", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            Controller.CheckWishListStatus checkwishliststatus = new Controller.CheckWishListStatus();
            ArrayList<Integer> AllUserStats = checkwishliststatus.checkWishlistStatus();
            attributes.put("allwishliststats", AllUserStats);
            Controller.CheckWishListStatusOwn checkownwishliststatus = new Controller.CheckWishListStatusOwn();
            ArrayList<Integer> OwnUserStats = checkownwishliststatus.checkWishlistStatusOwn(currentUser);
            attributes.put("ownwishliststats", OwnUserStats);

            if(currentUserLevel.equals("not registered")){
                res.redirect("/Home");
            }


            if(req.session().attribute("message") == null ){
                attributes.put("message", "null");
            }
            else{
                attributes.put("message", req.session().attribute("message"));
                req.session().attribute("message", null);
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

            Controller.GetWishlistInfo getWishlistData = new Controller.GetWishlistInfo();
            List getwishlistdata = getWishlistData.getWishlistinfo(currentUser);
            for (int i = 0; i < getwishlistdata.size(); i += 4){

                attributes.put("model", getwishlistdata.get(i) + ", " + attributes.get("model"));
                attributes.put("brand", getwishlistdata.get(i+1) + ", " + attributes.get("brand"));
                attributes.put("type", getwishlistdata.get(i+2) + ", " + attributes.get("type"));
                attributes.put("price", getwishlistdata.get(i+3) + ", " + attributes.get("price"));

            }

            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Allwishlist.vm");
        }, new VelocityTemplateEngine());


        post("/Allwishlist", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            String deleteUser = req.queryParams().iterator().next();
            String checkFunction = deleteUser.substring(0, 6);
            System.out.println(checkFunction + "dikkop");
            System.out.println(deleteUser + " dikkop2");

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

            Controller.GetWishlistInfo getWishlistData = new GetWishlistInfo();

            System.out.println(buttonuser + " Shamala");
            Controller.CheckWishListStatus checkWishlistStatus = new Controller.CheckWishListStatus();
            if (buttonuser.equals("makewishlistpublic")) {
                System.out.println(buttonuser + " Shamala2");
                Controller.SetWishlistPublic setWishListToPublic = new Controller.SetWishlistPublic();
                setWishListToPublic.setWishlistToPublic(currentUser);
            }
            if (buttonuser.equals("makewishlistprivate")){
                System.out.println(buttonuser +" Shamala3");
            }
            if (buttonuser.equals(buttonuser)){
                System.out.println(buttonuser.substring(8) + " shamala4");
                getWishlistData.getWishlistinfo(buttonuser.substring(8));
                req.session().attribute("Selecteduserwl", buttonuser.substring(8));
                System.out.println(req.session().attribute("Selecteduserwl") + " jemoeder2");
                System.out.println(buttonuser + " shamala5");
                res.redirect("/Otherwishlist");
            }


            return modelAndView(attributes, "Webshop/Allwishlist.vm");
        },new VelocityTemplateEngine());
    }
}
