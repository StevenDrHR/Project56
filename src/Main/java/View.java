import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.*;

/**
 * Created by nilmor on 10/27/2016.
 */
public class View {
    Deque<Modal> registerUsers = new ArrayDeque<Modal>();
    Deque<Modal> loginUsers = new ArrayDeque<Modal>();
    Deque<Modal> addProducts = new ArrayDeque<Modal>();

    public  void RenderHomeView(){
        get("/Home", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Index.vm");
        }, new VelocityTemplateEngine());


        post("/Home", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String LoginUsername = req.queryParams("LoginUsername");
            String LoginPassword = req.queryParams("LoginPassword");
            String variabel = req.queryParams().iterator().next();
            if (variabel.equals("LoginUsername")){
            Modal loginUser = new Modal();
            loginUser.LoginModal(LoginUsername,LoginPassword);
            loginUsers.addFirst(loginUser);
            String LoginUser = new Controller().LoginUser(loginUsers);
            String checkUserStatus = new Controller().checkUserStatus(LoginUser);
            if (checkUserStatus.equals("Blocked")) {
                attributes.put("message", "Blocked");}
            else {
            req.session().attribute("User",LoginUser);}
            }
            else {
            req.session().attribute("User", "");}

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            return modelAndView(attributes, "Webshop/Index.vm");
        },new VelocityTemplateEngine());
    }
    public void RenderRegisterView(){
        get("/Register", (req, res) ->  {
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("message","null");
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            if(currentUserLevel.equals("admin")||currentUserLevel.equals("user")){
                res.redirect("/Home");
            }
            return modelAndView(attributes, "Webshop/register.vm");
        },new VelocityTemplateEngine());

        post("/Register", (request, response) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String RegUsername = request.queryParams("RegUsername");
            String RegPassword = request.queryParams("RegPassword");
            String RegEmail = request.queryParams("RegEmail");
            String RegFName = request.queryParams("RegFName");
            String RegLName = request.queryParams("RegLName");
            String RegAge = request.queryParams("RegAge");
            String RegStreet = request.queryParams("RegStreet");
            String RegStreetNumber = request.queryParams("RegStreetNumber");
            String RegCountry = request.queryParams("RegCountry");
            String RegPostalCode = request.queryParams("RegPostalCode");
            String LoginUsername = request.queryParams("LoginUsername");
            String LoginPassword = request.queryParams("LoginPassword");
            String button = request.queryParams().iterator().next();
            if(button.equals("LoginUsername")) {
                Modal loginUser = new Modal();
                loginUser.LoginModal(LoginUsername, LoginPassword);
                loginUsers.addFirst(loginUser);
                String LoginUser = new Controller().LoginUser(loginUsers);
                String checkUserStatus = new Controller().checkUserStatus(LoginUser);
                if (checkUserStatus.equals("Blocked")) {
                    attributes.put("message", "Blocked");
                }
                else {
                    request.session().attribute("User",LoginUser);
                }
                String currentUser = request.session().attribute("User");
                attributes.put("CurrentUser", currentUser);
                Controller checkUserLevel = new Controller();
                String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
                attributes.put("userlevel", currentUserLevel);
                return modelAndView(attributes,"Webshop/index.vm");
            }

            if(button.equals("RegEmail")) {
                Modal registerUser = new Modal();
                registerUser.RegisterModal(RegUsername, RegPassword, RegEmail, RegFName, RegLName, RegAge, RegStreet, RegStreetNumber, RegCountry, RegPostalCode);
                registerUsers.addFirst(registerUser);
                String SaveUser = new Controller().RegisterUser(registerUsers);
                attributes.put("message", SaveUser);
            }
            return modelAndView(attributes, "Webshop/register.vm");
        },new VelocityTemplateEngine());
    }

    public void RenderAdminpageView(){
        get("/Adminpage", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String currentUserLevel = getUsers.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){
                res.redirect("/Home");
            }
            return modelAndView(attributes, "Webshop/admin.vm");
        },new VelocityTemplateEngine());

        post("/Adminpage", (request, response) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String currentUser = request.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String deleteUser = request.queryParams().iterator().next();
            String checkFunction = deleteUser.substring(0, 6);
            System.out.println(checkFunction);
            String addProduct = request.queryParams().iterator().next();
            if (addProduct.equals("addproduct_button")){
                response.redirect("/AddProduct");
            }

            if(checkFunction.equals("Delete") ){
                deleteUser= deleteUser.substring(7);
                Controller databaseDeleteUser = new Controller();
                databaseDeleteUser.DeleteUser(deleteUser);
            }
            else {
                deleteUser =deleteUser.substring(7);
                request.session().attribute("ModifyUser",deleteUser);
                response.redirect("/Modify");
                return modelAndView(attributes, "Webshop/modify.vm");
            }
            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users",list);

            return modelAndView(attributes, "Webshop/admin.vm");
        },new VelocityTemplateEngine());
    }

    public void RenderModifyView(){
        get("/Modify", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String modifyUser = req.session().attribute("ModifyUser");
            if (modifyUser == null){
                res.redirect("/Adminpage");
            }
            attributes.put("ModifyUser",modifyUser);
            return modelAndView(attributes, "Webshop/modify.vm");
        },new VelocityTemplateEngine());

        post("/Modify", (request, response) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String currentUser = request.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String modifyUser = request.session().attribute("ModifyUser");
            if (modifyUser == null){
                response.redirect("/Adminpage");
            }
            String button = request.queryParams().iterator().next();
            String test = button;
            System.out.println(button + " Shamala");

            if (button.equals("resetpassword_button") ) {
                Controller resetPassword = new Controller();
                resetPassword.ResetPassword(modifyUser);
            }
            else if(button.equals("blockuser_button")  ){
                System.out.println(request.session().attribute("ModifyUser") + " Shamala2");
                Controller blockUser = new Controller();
                blockUser.BlockUser(modifyUser);
            }
            else if(button.equals("unblockuser_button") ){
                Controller unblockUser = new Controller();
                unblockUser.UnblockUser(modifyUser);
            }
            else if(button.equals("return_button") ){
                request.session().attribute("ModifyUser",null);
                response.redirect("/Adminpage");
            }
            attributes.put("ModifyUser",modifyUser);
            return modelAndView(attributes, "Webshop/modify.vm");
        },new VelocityTemplateEngine());
    }

    public void RenderProfileView(){
        get("/Profile", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);


            Controller checkUser = new Controller();
            ArrayList<String> UserData = checkUser.getUserData(currentUser);
            attributes.put("firstname", UserData.get(0));
            attributes.put("lastname", UserData.get(1));
            attributes.put("age", UserData.get(2));
            attributes.put("emailaddress", UserData.get(3));
            attributes.put("userpassword", UserData.get(4));
            attributes.put("userstatus", UserData.get(5));

            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Profile.vm");
        }, new VelocityTemplateEngine());


        post("/Profile", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String LoginUsername = req.queryParams("LoginUsername");
            String LoginPassword = req.queryParams("LoginPassword");
            String variabel = req.queryParams().iterator().next();

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            Controller checkUser = new Controller();
            ArrayList<String> UserData = checkUser.getUserData(currentUser);
            attributes.put("firstname", UserData.get(0));
            attributes.put("lastname", UserData.get(1));
            attributes.put("age", UserData.get(2));
            attributes.put("emailaddress", UserData.get(3));
            attributes.put("userpassword", UserData.get(4));
            attributes.put("userstatus", UserData.get(5));

            return modelAndView(attributes, "Webshop/Profile.vm");
        },new VelocityTemplateEngine());
    }
    public void RenderAddProductView(){
        get("/AddProduct", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String currentUserLevel = getUsers.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){
                res.redirect("/Home");
            }
            return modelAndView(attributes, "Webshop/addproduct.vm");
        },new VelocityTemplateEngine());

        post("/AddProduct", (request, response) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            String currentUser = request.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String currentUserLevel = getUsers.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){
                response.redirect("/Home");
            }
            String button = request.queryParams().iterator().next();
            System.out.println(button + " Shamala");
            String modal = request.queryParams("Modal");
            String brand = request.queryParams("Brand");
            String type = request.queryParams("CarType");
            String year = request.queryParams("BuildYear");
            String price = request.queryParams("Price");
            String deliveryTime = request.queryParams("DeleiveryTime");
            String description = request.queryParams("Description");

            System.out.println("" + brand);



            if(button.equals("Brand") ){
                Modal addProduct = new Modal();
                addProduct.AddProductModal(modal, brand, type, year, price, deliveryTime, description);
                addProducts.addFirst(addProduct);
                String AddProduct = new Controller().AddProduct(addProducts);
                attributes.put("message", AddProduct);

            }
            else if(button.equals("return_button") ){
                request.session().attribute("ModifyUser",null);
                response.redirect("/Adminpage");
            }
            return modelAndView(attributes, "Webshop/addproduct.vm");
        },new VelocityTemplateEngine());
    }
    public void RenderShopView(){
        get("/Shop", (req,res) ->{
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String currentUserLevel = getUsers.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            Controller getModel = new Controller();
            List models = getModel.GetModels("");
            attributes.put("models", models);

            Controller getBrand = new Controller();
            List brands = getBrand.GetBrands("");
            attributes.put("brands", brands);

            Controller getType = new Controller();
            List types = getType.GetType("");
            attributes.put("types", types);

            Controller getPrice = new Controller();
            List price = getPrice.GetPrice("");
            attributes.put("price", price);

            Controller getPid = new Controller();
            List productid = getPid.GetProductId("");
            attributes.put("productid", productid);

            return modelAndView(attributes, "Webshop/shop.vm");
        }, new VelocityTemplateEngine());


        post("/Shop", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            String LoginUsername = req.queryParams("LoginUsername");
            String LoginPassword = req.queryParams("LoginPassword");
            String variabel = req.queryParams().iterator().next();

            if (variabel.equals("OrderedBrands")) {
                Controller getProducts = new Controller();
                List models = getProducts.GetModels("brand");
                attributes.put("models", models);

                List brands = getProducts.GetBrands("brand");
                attributes.put("brands", brands);

                List types = getProducts.GetType("brand");
                attributes.put("types", types);

                List price = getProducts.GetPrice("brand");
                attributes.put("price", price);

                List productid = getProducts.GetProductId("brand");
                attributes.put("productid", productid);
                System.out.println(productid + " brandpid");
            }
            else if (variabel.equals("OrderedTypes")) {
                Controller getProducts = new Controller();
                List models = getProducts.GetModels("type");
                attributes.put("models", models);

                List brands = getProducts.GetBrands("type");
                attributes.put("brands", brands);

                List types = getProducts.GetType("type");
                attributes.put("types", types);

                List price = getProducts.GetPrice("type");
                attributes.put("price", price);

                List productid = getProducts.GetProductId("type");
                attributes.put("productid", productid);
                System.out.println(productid + " typepid");
            }
            else if (variabel.equals("OrderedYears")) {
                Controller getProducts = new Controller();
                List models = getProducts.GetModels("year");
                attributes.put("models", models);

                List brands = getProducts.GetBrands("year");
                attributes.put("brands", brands);

                List types = getProducts.GetType("year");
                attributes.put("types", types);

                List price = getProducts.GetPrice("year");
                attributes.put("price", price);

                List productid = getProducts.GetProductId("year");
                attributes.put("productid", productid);
                System.out.println(productid + " yearpid");
            }
            else if (variabel.equals("OrderedPriceLtH")) {
                Controller getProducts = new Controller();
                List models = getProducts.GetModels("pricelth");
                attributes.put("models", models);

                List brands = getProducts.GetBrands("pricelth");
                attributes.put("brands", brands);

                List types = getProducts.GetType("pricelth");
                attributes.put("types", types);

                List price = getProducts.GetPrice("pricelth");
                attributes.put("price", price);

                List productid = getProducts.GetProductId("pricelth");
                attributes.put("productid", productid);
                System.out.println(productid + " pricelthpid");
            }
            else if (variabel.equals("OrderedPriceHtL")) {
                Controller getProducts = new Controller();
                List models = getProducts.GetModels("pricehtl");
                attributes.put("models", models);

                List brands = getProducts.GetBrands("pricehtl");
                attributes.put("brands", brands);

                List types = getProducts.GetType("pricehtl");
                attributes.put("types", types);

                List price = getProducts.GetPrice("pricehtl");
                attributes.put("price", price);

                List productid = getProducts.GetProductId("pricehtl");
                attributes.put("productid", productid);
                System.out.println(productid + " pricehtlpid");
            }
            else{
                Controller getProducts = new Controller();
                List models = getProducts.GetModels("");
                attributes.put("models", models);

                List brands = getProducts.GetBrands("");
                attributes.put("brands", brands);

                List types = getProducts.GetType("");
                attributes.put("types", types);

                List price = getProducts.GetPrice("");
                attributes.put("price", price);

                List productid = getProducts.GetProductId("");
                attributes.put("productid", productid);
                System.out.println(productid + " nothing");
            }

            System.out.println(variabel + " trololol");
            if (variabel.contains("AddToWishlist")){

                res.redirect("/Shop");
                System.out.println(variabel + " Shamala");
            }

            else if (variabel.contains("AddToCart")){
                String productid = variabel.substring(9);
                System.out.println(productid + " pid");
                if(req.session().attribute("productid")== null){
                    req.session().attribute("productid", productid);
                }
                else{
                    req.session().attribute("productid", productid + ", " + req.session().attribute("productid"));
                }

                System.out.println(req.session().attribute("productid") + " vet");
                res.redirect("/Shoppingcart");

            }

            else if (variabel.equals("LoginUsername")){
                System.out.println(variabel + " Shamala2");
                Modal loginUser = new Modal();
                loginUser.LoginModal(LoginUsername,LoginPassword);
                loginUsers.addFirst(loginUser);
                String LoginUser = new Controller().LoginUser(loginUsers);
                String checkUserStatus = new Controller().checkUserStatus(LoginUser);
                if (checkUserStatus.equals("Blocked")) {
                    attributes.put("message", "Blocked");}
                else {
                    req.session().attribute("User",LoginUser);}
            }
            else {
                System.out.println(variabel + " Shamala3");
                req.session().attribute("User", "");}

            Controller getUsers = new Controller();

            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String currentUserLevel = getUsers.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            return modelAndView(attributes, "Webshop/shop.vm");
        },new VelocityTemplateEngine());
    }

    public void RenderShoppingcartView() {
        get("/Shoppingcart", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null) {
                req.session().attribute("User", " ");
            }

            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users", list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String currentUserLevel = getUsers.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            String currentPid = req.session().attribute("productid");
            String[] pid = currentPid.split(", ");

            System.out.println(pid[0] + " get pid");

            System.out.println(currentPid + " get curpid");

            for (int i = 0; i < pid.length; i++) {
                Controller getProductInfo = new Controller();
                List productinfo = getProductInfo.getPinfo(pid[i]);
                if(i == 0){
                    req.session().attribute("model", productinfo.get(0));
                    req.session().attribute("brand", productinfo.get(1));
                    req.session().attribute("type", productinfo.get(2));
                    req.session().attribute("price", productinfo.get(3));
                }
                else{
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

            return modelAndView(attributes, "Webshop/shoppingcart.vm");
        }, new VelocityTemplateEngine());


        post("/Shoppingcart", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            String currentPid = req.session().attribute("product");
            System.out.println(currentPid + " post curpid");

            String LoginUsername = req.queryParams("LoginUsername");
            String LoginPassword = req.queryParams("LoginPassword");
            String variabel = req.queryParams().iterator().next();
            System.out.println(variabel + " Shamala");
            if (variabel.equals("LoginUsername")) {
                Modal loginUser = new Modal();
                loginUser.LoginModal(LoginUsername, LoginPassword);
                loginUsers.addFirst(loginUser);
                String LoginUser = new Controller().LoginUser(loginUsers);
                String checkUserStatus = new Controller().checkUserStatus(LoginUser);
                if (checkUserStatus.equals("Blocked")) {
                    attributes.put("message", "Blocked");
                } else {
                    req.session().attribute("User", LoginUser);
                }
            } else {
                req.session().attribute("User", "");
            }

            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users", list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            String currentUserLevel = getUsers.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            return modelAndView(attributes, "Webshop/shoppingcart.vm");
        }, new VelocityTemplateEngine());
    }
}

