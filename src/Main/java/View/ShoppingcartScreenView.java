package View;

import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.modelAndView;
import static spark.Spark.post;

/**
 * Created by nilmor on 2/2/2017.
 */
public class ShoppingcartScreenView {
    public void RenderShoppingcartView() {
        get("/Shoppingcart", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null) {
                req.session().attribute("User", " ");
            }
            //Checking User is admin or user
            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users", list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel userLevel = new Controller.CheckUserLevel();
            String currentUserLevel = userLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(req.session().attribute("productid") == null){
                attributes.put("shopcheck", "none");
            }
            else {
                attributes.put("shopcheck", req.session().attribute("productid"));
            }
            if(req.session().attribute("productid") != null){// getting all the added products

                String currentPid = req.session().attribute("productid");
                String[] pid = currentPid.split(", ");

                System.out.println(pid[0] + " get pid");

                System.out.println(currentPid + " get curpid");

                for (int i = 0; i < pid.length; i++) {
                    Controller.GetProductInfo getProductInfo = new Controller.GetProductInfo();
                    List productinfo = getProductInfo.getPinfo(pid[i]);
                    if (i == 0) {
                        req.session().attribute("model", productinfo.get(0));
                        req.session().attribute("brand", productinfo.get(1));
                        req.session().attribute("type", productinfo.get(2));
                        req.session().attribute("price", productinfo.get(3));
                    } else {
                        req.session().attribute("model", productinfo.get(0) + ", " + req.session().attribute("model"));
                        req.session().attribute("brand", productinfo.get(1) + ", " + req.session().attribute("brand"));
                        req.session().attribute("type", productinfo.get(2) + ", " + req.session().attribute("type"));
                        req.session().attribute("price", productinfo.get(3) + ", " + req.session().attribute("price"));
                    }

                }
                System.out.println(req.session().attribute("model") + " test");

                attributes.put("model", req.session().attribute("model"));
                attributes.put("brand", req.session().attribute("brand"));
                attributes.put("type", req.session().attribute("type"));
                attributes.put("price", req.session().attribute("price"));
                attributes.put("amount", req.session().attribute("amount"));
            }

            return modelAndView(attributes, "Webshop/shoppingcart.vm");
        }, new VelocityTemplateEngine());


        post("/Shoppingcart", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            String currentPid = req.session().attribute("product");
            System.out.println(currentPid + " post curpid");

            String variabel = req.queryParams().iterator().next();
            System.out.println(variabel + " Shamala");
            if (variabel.equals("resetcart")) {//emptys the cart
                req.session().attribute("productid", null);
                req.session().attribute("amount", null);
            }
            if (variabel.equals("orderbutton")) {//makes an order of the items in the cart
                System.out.println("ordermsg");
                req.session().attribute("message", "Succesfully Ordered");
                String currentUser = req.session().attribute("User");
                Controller.GetUserData getUserData = new Controller.GetUserData();
                String userid  = getUserData.getUserData(currentUser).get(6);
                String products = req.session().attribute("productid");
                String amounts = req.session().attribute("amount");
                String[] product = products.split(", ");
                String[] amount = amounts.split(", ");

                Controller.SetOrderHistory setOrderHistory = new Controller.SetOrderHistory();
                setOrderHistory.SetOrderHistory(userid, product, amount);
                req.session().attribute("productid", null);
                req.session().attribute("amount", null);

                res.redirect("/Orderhistory");
            }

            if(req.session().attribute("productid") == null){// checks if the cart is empty
                attributes.put("shopcheck", "none");
            }
            else {
                attributes.put("shopcheck", req.session().attribute("productid"));
            }


            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users", list);
            String currentUser = req.session().attribute("User");
            //Checking User is admin or user
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel userLevel = new Controller.CheckUserLevel();
            String currentUserLevel = userLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);


            return modelAndView(attributes, "Webshop/shoppingcart.vm");
        }, new VelocityTemplateEngine());
    }
}
