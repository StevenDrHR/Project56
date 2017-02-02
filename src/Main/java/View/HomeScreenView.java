package View;


import Controller.CheckUserStatus;
import Controller.LoginUsers;
import Model.AddProductModels;
import Model.LoginModals;
import Model.RegisterModals;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by nilmor on 2/2/2017.
 */
public class HomeScreenView {
    Deque<RegisterModals> registerUsers = new ArrayDeque<RegisterModals>();
    Deque<LoginModals> loginUsers = new ArrayDeque<Model.LoginModals>();
    Deque<AddProductModels> addProducts = new ArrayDeque<AddProductModels>();

    public  void RenderHomeView(){
        get("/Home", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            if (req.session().attribute("User") == null)
            {req.session().attribute("User", " ");}

            if(req.session().attribute("message") == null ){
                attributes.put("message", "null");
            }
            else {
                attributes.put("message", req.session().attribute("message"));
                req.session().attribute("message", null);
            }

            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            System.out.println(currentUser + " currentuservalue");
            System.out.println(req.session().attribute("User") + " uservalue");
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            System.out.println(currentUserLevel);
            return modelAndView(attributes, "Webshop/Index.vm");
        }, new VelocityTemplateEngine());


        post("/Home", (req,res)-> {
            Map<String, Object> attributes = new HashMap<String, Object>();

            if(req.session().attribute("message") == null ){
                attributes.put("message", "null");
            }
            else {
                attributes.put("message", req.session().attribute("message"));
                req.session().attribute("message", null);
            }


            String LoginUsername = req.queryParams("LoginUsername");
            String LoginPassword = req.queryParams("LoginPassword");
            String variabel = req.queryParams().iterator().next();

            if (variabel.equals("LoginUsername")){//if login button is pressed then check the cirdentials
                Model.LoginModals loginUser = new Model.LoginModals();
                loginUser.LoginModal(LoginUsername,LoginPassword);
                loginUsers.addFirst(loginUser);
                String LoginUser = new LoginUsers().LoginUser(loginUsers);
                String checkUserStatus = new CheckUserStatus().checkUserStatus(LoginUser);
                if (checkUserStatus.equals("Blocked")) { // check if the user is blocked
                    attributes.put("message", "Blocked");
                }
                else {
                    req.session().attribute("User",LoginUser);
                    //attributes.put("message", "Login Failed, Please Try Again");
                }
            }
            else {
                req.session().attribute("User", "");
                //attributes.put("message", "Login Succesful");
            }

            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            return modelAndView(attributes, "Webshop/Index.vm");
        },new VelocityTemplateEngine());
    }
}
