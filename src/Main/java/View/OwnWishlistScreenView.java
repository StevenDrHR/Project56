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
public class OwnWishlistScreenView {
    public void RenderOwnWishlistView(){
        get("/Ownwishlist", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}
            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            Controller.CheckOwnWishList checkOwnWishList = new Controller.CheckOwnWishList();
            ArrayList<Integer> checkamountitems = checkOwnWishList.checkOwnWishlist(currentUser);
            attributes.put("amountitems", checkamountitems);

            if(currentUserLevel.equals("not registered")){// redirect if not registered
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

            // getting all public wishlists
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

            //getting all wishlist info
            Controller.GetWishlistInfo getWishlistData = new Controller.GetWishlistInfo();
            List getwishlistdata = getWishlistData.getWishlistinfo(currentUser);
            for (int i = 0; i < getwishlistdata.size(); i += 4){

                attributes.put("model", getwishlistdata.get(i) + ", " + attributes.get("model"));
                attributes.put("brand", getwishlistdata.get(i+1) + ", " + attributes.get("brand"));
                attributes.put("type", getwishlistdata.get(i+2) + ", " + attributes.get("type"));
                attributes.put("price", getwishlistdata.get(i+3) + ", " + attributes.get("price"));
            }

            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Ownwishlist.vm");
        }, new VelocityTemplateEngine());


        post("/Ownwishlist", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            //Checking User is admin or user
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

            Controller.GetWishlistInfo getWishlistData = new Controller.GetWishlistInfo();

            System.out.println(buttonuser + " Shamala");
            Controller.CheckWishListStatus checkWishlistStatus = new Controller.CheckWishListStatus();
            if (buttonuser.equals("makewishlistpublic")) {//making the users wishlist public
                System.out.println(buttonuser + " Shamala2");
                Controller.SetWishlistPublic setWishListToPublic = new Controller.SetWishlistPublic();
                setWishListToPublic.setWishlistToPublic(currentUser);
            }
            if (buttonuser.equals("makewishlistprivate")){// making the users wishlist private
                Controller.SetWishlistPrivate setWishListToPrivate = new Controller.SetWishlistPrivate();
                setWishListToPrivate.setWishlistToPrivate(currentUser);
                System.out.println(buttonuser +" Shamala3");
            }
            if (buttonuser.equals(buttonuser)){//get wishlist info of the pressed wishlist
                System.out.println(buttonuser.substring(8) + " shamala4");
                getWishlistData.getWishlistinfo(buttonuser.substring(8));
                System.out.println(buttonuser + " shamala5");
            }


            return modelAndView(attributes, "Webshop/Ownwishlist.vm");
        },new VelocityTemplateEngine());
    }
}
