package View;

import Controller.CheckUserStatus;
import Controller.LoginUsers;
import Controller.RegisterUser;
import Model.AddProductModels;
import Model.LoginModals;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by nilmor on 2/2/2017.
 */
public class RegisterScreenView {

    Deque<Model.RegisterModals> registerUsers = new ArrayDeque<Model.RegisterModals>();
    Deque<LoginModals> loginUsers = new ArrayDeque<Model.LoginModals>();
    Deque<AddProductModels> addProducts = new ArrayDeque<AddProductModels>();

    public void RenderRegisterView(){
        get("/Register", (req, res) ->  {
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("message","null");
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
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
                Model.LoginModals loginUser = new Model.LoginModals();
                loginUser.LoginModal(LoginUsername, LoginPassword);
                loginUsers.addFirst(loginUser);
                String LoginUser = new LoginUsers().LoginUser(loginUsers);
                String checkUserStatus = new CheckUserStatus().checkUserStatus(LoginUser);
                if (checkUserStatus.equals("Blocked")) {
                    attributes.put("message", "Blocked");
                }
                else {
                    request.session().attribute("User",LoginUser);
                }
                String currentUser = request.session().attribute("User");
                attributes.put("CurrentUser", currentUser);
                Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
                String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
                attributes.put("userlevel", currentUserLevel);
                return modelAndView(attributes, "Webshop/index.vm");
            }

            if(button.equals("RegEmail")) {
                Model.RegisterModals registerUser = new Model.RegisterModals();
                registerUser.RegisterModal(RegUsername, RegPassword, RegEmail, RegFName, RegLName, RegAge, RegStreet, RegStreetNumber, RegCountry, RegPostalCode);
                registerUsers.addFirst(registerUser);
                String SaveUser = new RegisterUser().RegisterUser(registerUsers);
                request.session().attribute("message", SaveUser);
                response.redirect("/Home");
            }
            return modelAndView(attributes, "Webshop/register.vm");
        },new VelocityTemplateEngine());
    }
}
