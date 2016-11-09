import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.*;

/**
 * Created by nilmor on 10/27/2016.
 */
public class View {
    Deque<Modal> registerUsers = new ArrayDeque<Modal>();
    Deque<Modal> loginUsers = new ArrayDeque<Modal>();
    public  void RenderHomeView(){
        get("/Home", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Index.vm");
        }, new VelocityTemplateEngine());


        post("/Home", (req,res)-> {
            String LoginUsername = req.queryParams("LoginUsername");
            String LoginPassword = req.queryParams("LoginPassword");
            Modal loginUser = new Modal();
            loginUser.LoginModal(LoginUsername,LoginPassword);
            loginUsers.addFirst(loginUser);
            String LoginUser = new Controller().LoginUser(loginUsers);
            req.session().attribute("User",LoginUser);
            System.out.println(req.session().attribute("User")+ " Shamala");

            Map<String, Object> attributes = new HashMap<String, Object>();
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
                request.session().attribute("User", LoginUser);
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
}

