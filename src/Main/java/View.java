import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.modelAndView;
import static spark.Spark.post;

/**
 * Created by nilmor on 10/27/2016.
 */
public class View {
    Deque<Modal> registerUsers = new ArrayDeque<Modal>();
    Deque<Modal> loginUsers = new ArrayDeque<Modal>();
    public  void RenderHomeView(){
        Controller renderView = new Controller();
        get("/Home", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String currentUser = req.session().attribute("User");
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            System.out.println(currentUserLevel);
        return modelAndView(attributes, "Webshop/Index.vm");
        },new VelocityTemplateEngine());

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
        Controller checkUserLevel = new Controller();
        String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
        attributes.put("userlevel", currentUserLevel);
        System.out.println(currentUserLevel+ "Shamala123");
            return modelAndView(attributes, "Webshop/Index.vm");
        },new VelocityTemplateEngine());
    }
    public void RenderRegisterView(){
        Controller renderView = new Controller();
        get("/Register", (req, res) ->  {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("message","null");
            String currentUser = req.session().attribute("User");
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            System.out.println(currentUserLevel);
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


            Modal loginUser = new Modal();
            loginUser.LoginModal(LoginUsername,LoginPassword);
            loginUsers.addFirst(loginUser);
            String LoginUser = new Controller().LoginUser(loginUsers);
            request.session().attribute("User",LoginUser);
            System.out.println(request.session().attribute("User")+ " Shamala");
            String currentUser = request.session().attribute("User");
            Controller checkUserLevel = new Controller();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            System.out.println(currentUserLevel);

            Modal registerUser = new Modal();
            registerUser.RegisterModal(RegUsername, RegPassword, RegEmail,RegFName,RegLName,RegAge,RegStreet,RegStreetNumber,RegCountry,RegPostalCode);
            registerUsers.addFirst(registerUser);
            String SaveUser = new Controller().RegisterUser(registerUsers);

            attributes.put("message", SaveUser);

            return modelAndView(attributes, "Webshop/register.vm");
        },new VelocityTemplateEngine());
    }
    public void RenderAdminpageView(){
        get("/Adminpage", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users",list);

            return modelAndView(attributes, "Webshop/admin.vm");
        },new VelocityTemplateEngine());

        post("/Adminpage", (request, response) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            String deleteUser =request.queryParams().iterator().next();
            Controller databaseDeleteUser = new Controller();
            databaseDeleteUser.DeleteUser(deleteUser);
            Controller getUsers = new Controller();
            List list = getUsers.GetUsers();
            attributes.put("users",list);

            return modelAndView(attributes, "Webshop/admin.vm");
        },new VelocityTemplateEngine());
    }
}
