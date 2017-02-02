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
public class AdminScreenView {
    public void RenderAdminpageView(){
        get("/Adminpage", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            //Checking User is admin or user
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel checkUserLevel = new Controller.CheckUserLevel();
            String currentUserLevel = checkUserLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){//Redirecting if not admin
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
            if (addProduct.equals("addproduct_button")){ // if add product button is pressed then go to the add product page
                response.redirect("/AddProduct");
            }

            if(checkFunction.equals("Delete") ){ // if delete user is pressed then delete the user
                deleteUser= deleteUser.substring(7);
                Controller.DeleteUser databaseDeleteUser = new Controller.DeleteUser();
                databaseDeleteUser.DeleteUser(deleteUser);
            }
            else { // if modify user is pressed then redirect to the modify user page
                deleteUser =deleteUser.substring(7);
                request.session().attribute("ModifyUser",deleteUser);
                response.redirect("/Modify");
                return modelAndView(attributes, "Webshop/modify.vm");
            }
            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users",list);

            return modelAndView(attributes, "Webshop/admin.vm");
        },new VelocityTemplateEngine());
    }

}
