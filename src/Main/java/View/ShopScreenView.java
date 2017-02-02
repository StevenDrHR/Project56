package View;

import Controller.CheckUserStatus;
import Controller.LoginUsers;
import Model.LoginModals;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.*;

/**
 * Created by nilmor on 2/2/2017.
 */
public class ShopScreenView {
    Deque<LoginModals> loginUsers = new ArrayDeque<Model.LoginModals>();


    public void RenderShopView(){       // renders the shop.vm, get's called in main
        get("/Shop", (req,res) ->{      // gets the shop.vm data
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)        // cookie to know the status of the user (registered/nonregistered/admin)
            {req.session().attribute("User", " ");}

            Controller.GetUsers getUsers = new Controller.GetUsers();
            List nonregusers = getUsers.GetUsers();        // GetUsers() gets all the users out of the db that are non registered
            attributes.put("users", nonregusers);       // the nonregusers are then put in the attributes, where after it is used as a reference in the shop.vm file
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel userLevel = new Controller.CheckUserLevel();
            String currentUserLevel = userLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            Controller.GetModels getShopModels = new Controller.GetModels();
            List models = getShopModels.GetModels("");       // every function gets the respected data out of the db
            attributes.put("models", models);

            Controller.GetBrands getShopBrands = new Controller.GetBrands();
            List brands = getShopBrands.GetBrands("");
            attributes.put("brands", brands);

            Controller.GetType getShopType = new Controller.GetType();
            List types = getShopType.GetType("");
            attributes.put("types", types);

            Controller.GetPrice getShopPrice = new Controller.GetPrice();
            List price = getShopPrice.GetPrice("");
            attributes.put("price", price);

            Controller.GetProductId getPid = new Controller.GetProductId();
            List productid = getPid.GetProductId("");
            attributes.put("productid", productid);

            Controller.GetImage getImage = new Controller.GetImage();
            List getImages = getImage.GetImage("");
            attributes.put("image", getImages);

            return modelAndView(attributes, "Webshop/shop.vm");
        }, new VelocityTemplateEngine());


        post("/Shop", (req,res)-> {     // the post receives the data from the frontend, whenever a button is pushed, the post handles it
            Map<String, Object> attributes = new HashMap<String, Object>();


            String LoginUsername = req.queryParams("LoginUsername");
            String LoginPassword = req.queryParams("LoginPassword");
            String variabel = req.queryParams().iterator().next();      // variabel is just a check that iterates through every button
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel userLevel = new Controller.CheckUserLevel();
            String currentUserLevel = userLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if (variabel.equals("OrderedBrands")) {     // here it checks if the button has a specific value and acts accordingly
                Controller.GetModels getShopModels = new Controller.GetModels();
                List models = getShopModels.GetModels("brand");
                attributes.put("models", models);

                Controller.GetBrands getShopBrands = new Controller.GetBrands();
                List brands = getShopBrands.GetBrands("brand");
                attributes.put("brands", brands);

                Controller.GetType getShopProducts = new Controller.GetType();
                List types = getShopProducts.GetType("brand");
                attributes.put("types", types);

                Controller.GetPrice getShopPrice = new Controller.GetPrice();
                List price = getShopPrice.GetPrice("brand");
                attributes.put("price", price);

                Controller.GetProductId getShopProductId = new Controller.GetProductId();
                List productid = getShopProductId.GetProductId("brand");
                attributes.put("productid", productid);

                Controller.GetImage getImage = new Controller.GetImage();
                List getImages = getImage.GetImage("brand");
                attributes.put("image", getImages);
            }

            else if (variabel.equals("OrderedTypes")) {
                Controller.GetModels getModels = new Controller.GetModels();
                List models = getModels.GetModels("type");
                attributes.put("models", models);

                Controller.GetBrands getBrands = new Controller.GetBrands();
                List brands = getBrands.GetBrands("type");
                attributes.put("brands", brands);

                Controller.GetType getType = new Controller.GetType();
                List types = getType.GetType("type");
                attributes.put("types", types);

                Controller.GetPrice getPrice = new Controller.GetPrice();
                List price = getPrice.GetPrice("type");
                attributes.put("price", price);

                Controller.GetProductId getProductId = new Controller.GetProductId();
                List productid = getProductId.GetProductId("type");
                attributes.put("productid", productid);

                Controller.GetImage getImage = new Controller.GetImage();
                List getImages = getImage.GetImage("type");
                attributes.put("image", getImages);
            }

            else if (variabel.equals("OrderedYears")) {
                Controller.GetModels getModels = new Controller.GetModels();
                List models = getModels.GetModels("year");
                attributes.put("models", models);

                Controller.GetBrands getBrands = new Controller.GetBrands();
                List brands = getBrands.GetBrands("year");
                attributes.put("brands", brands);

                Controller.GetType getType = new Controller.GetType();
                List types = getType.GetType("year");
                attributes.put("types", types);

                Controller.GetPrice getPrice = new Controller.GetPrice();
                List price = getPrice.GetPrice("year");
                attributes.put("price", price);

                Controller.GetProductId getProductId = new Controller.GetProductId();
                List productid = getProductId.GetProductId("year");
                attributes.put("productid", productid);

                Controller.GetImage getImage = new Controller.GetImage();
                List getImages = getImage.GetImage("year");
                attributes.put("image", getImages);
            }
            else if (variabel.equals("OrderedPriceLtH")) {
                Controller.GetModels getModels = new Controller.GetModels();
                List models = getModels.GetModels("pricelth");
                attributes.put("models", models);

                Controller.GetBrands getBrands = new Controller.GetBrands();
                List brands = getBrands.GetBrands("pricelth");
                attributes.put("brands", brands);

                Controller.GetType getType = new Controller.GetType();
                List types = getType.GetType("pricelth");
                attributes.put("types", types);

                Controller.GetPrice getPrice = new Controller.GetPrice();
                List price = getPrice.GetPrice("pricelth");
                attributes.put("price", price);

                Controller.GetProductId getProductId = new Controller.GetProductId();
                List productid = getProductId.GetProductId("pricelth");
                attributes.put("productid", productid);

                Controller.GetImage getImage = new Controller.GetImage();
                List getImages = getImage.GetImage("pricelth");
                attributes.put("image", getImages);
            }
            else if (variabel.equals("OrderedPriceHtL")) {
                Controller.GetModels getModels = new Controller.GetModels();
                List models = getModels.GetModels("pricehtl");
                attributes.put("models", models);

                Controller.GetBrands getBrands = new Controller.GetBrands();
                List brands = getBrands.GetBrands("pricehtl");
                attributes.put("brands", brands);

                Controller.GetType getType = new Controller.GetType();
                List types = getType.GetType("pricehtl");
                attributes.put("types", types);

                Controller.GetPrice getPrice = new Controller.GetPrice();
                List price = getPrice.GetPrice("pricehtl");
                attributes.put("price", price);

                Controller.GetProductId getProductId = new Controller.GetProductId();
                List productid = getProductId.GetProductId("pricehtl");
                attributes.put("productid", productid);

                Controller.GetImage getImage = new Controller.GetImage();
                List getImages = getImage.GetImage("pricehtl");
                attributes.put("image", getImages);
            }
            else{
                Controller.GetModels getModels = new Controller.GetModels();
                List models = getModels.GetModels("");
                attributes.put("models", models);

                Controller.GetBrands getBrands = new Controller.GetBrands();
                List brands = getBrands.GetBrands("");
                attributes.put("brands", brands);

                Controller.GetType getType = new Controller.GetType();
                List types = getType.GetType("");
                attributes.put("types", types);

                Controller.GetPrice getPrice = new Controller.GetPrice();
                List price = getPrice.GetPrice("");
                attributes.put("price", price);

                Controller.GetProductId getProductId= new Controller.GetProductId();
                List productid = getProductId.GetProductId("");
                attributes.put("productid", productid);

                Controller.GetImage getImage = new Controller.GetImage();
                List getImages = getImage.GetImage("");
                attributes.put("image", getImages);
            }

            if (variabel.contains("AddToWishlist")){        // this checks if the button contains the "AddToWishlist" value
                System.out.println(variabel + " Shamala1");
                Controller.GetUserData getUserData = new Controller.GetUserData();
                System.out.println(variabel + " Shamala2");
                ArrayList<String> userid = getUserData.getUserData(req.session().attribute("User"));      // puts it in a cookie
                System.out.println(variabel + " Shamala3");
                Controller.SetToWishList settoWishlist = new Controller.SetToWishList();
                settoWishlist.setToWishlist(userid.get(6), variabel.substring(13));
                System.out.println(variabel + " Shamala4");
                res.redirect("/Wishlist");
                System.out.println(variabel + " Shamala");
            }

            else if (variabel.contains("AddToCart")){       // adds to cart if button contains "AddToCart"
                String productid = variabel.substring(9);
                System.out.println(productid + " pid");
                if(req.session().attribute("productid")== null){
                    req.session().attribute("productid", productid);
                    req.session().attribute("amount", 1 +"");
                }
                else{

                    String currentPid = req.session().attribute("productid");       // sessions the productid
                    String currentAmount = req.session().attribute("amount");

                    String[]  currentPids =  currentPid.split(", ");        // filters the list
                    String[] currentAmounts = currentAmount.split(", ");

                    int dubble = -1;
                    for(int i = 0; i < currentPids.length; i++){        // for every currentPid do the following
                        if (currentPids[i].equals(productid)){      // if the first item in the list == productid do the following
                            currentAmounts[i] = (Integer.valueOf(currentAmounts[i]) + 1) + "";      // increments tha product amounts
                            dubble = i;
                            currentAmount = "";
                            for (int j = 0; j < currentAmounts.length; j++){        // for every item in currentAmounts do the following
                                if(j == 0){     // this starts the first amount as 0
                                    req.session().attribute("amount",currentAmounts[j]);
                                    currentAmount = currentAmounts[j];      // here it initiates the currentAmount to the j'th (integer) currentAmounts
                                }
                                else {
                                    req.session().attribute("amount", req.session().attribute("amount") + ", " + currentAmounts[j]);
                                    System.out.println(currentAmount + "Shamlalalalalala");
                                }
                            }


                        }
                    }
                    if (dubble == -1){
                        req.session().attribute("productid", productid + ", " + req.session().attribute("productid"));
                        req.session().attribute("amount", 1 + ", " + req.session().attribute("amount"));
                    }

                }

                System.out.println(req.session().attribute("productid") + " vet");
                res.redirect("/Shoppingcart");

            }

            if (variabel.equals("LoginUsername")){
                Model.LoginModals loginUser = new Model.LoginModals();
                loginUser.LoginModal(LoginUsername,LoginPassword);
                loginUsers.addFirst(loginUser);
                String LoginUser = new LoginUsers().LoginUser(loginUsers);
                String checkUserStatus = new CheckUserStatus().checkUserStatus(LoginUser);
                if (checkUserStatus.equals("Blocked")) {
                    attributes.put("message", "Blocked");
                }
                else {
                    req.session().attribute("User",LoginUser);
                    res.redirect("/Shop");
                }
            }
            else if(variabel.equals("iets")) {
                req.session().attribute("User", "");
                res.redirect("/Shop");
            }
            return modelAndView(attributes, "Webshop/Shop.vm");
        },new VelocityTemplateEngine());
    }
}
