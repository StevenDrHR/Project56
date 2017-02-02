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
public class GraphScreenView {
    public void RenderGraphView(){
        get("/Graphs", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            String currentUser = req.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel userLevel = new Controller.CheckUserLevel();
            String currentUserLevel = userLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);
            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){
                res.redirect("/Home");
            }
            return modelAndView(attributes, "Webshop/graphs.vm");
        },new VelocityTemplateEngine());

        post("/Graphs", (request, response) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            Controller.GetUsers getUsers = new Controller.GetUsers();
            List list = getUsers.GetUsers();
            attributes.put("users",list);
            String currentUser = request.session().attribute("User");
            attributes.put("CurrentUser", currentUser);
            Controller.CheckUserLevel userLevel = new Controller.CheckUserLevel();
            String currentUserLevel = userLevel.checkUserLevel(currentUser);
            attributes.put("userlevel", currentUserLevel);

            if(currentUserLevel.equals("not registered")||currentUserLevel.equals("user")){
                response.redirect("/Home");
            }
            String button = request.queryParams().iterator().next();

            if(button.equals("user_button") ){
                Controller.GetAdmins getAdmins = new Controller.GetAdmins();
                attributes.put("variable","User");
                attributes.put("users", getUsers.GetUsers());
                attributes.put("admins", getAdmins.GetAdmins());

                System.out.println(attributes.get("variable") + " Shamlalala" );

            }
            else if(button.equals("car_type_button") ){
                Controller.GetCarType getCarType = new Controller.GetCarType();
                attributes.put("variable","Car_Type");
                attributes.put("cartype", getCarType.getCarType());


            }
            else if(button.equals("most_sold_button") ){
                Controller.GetMostSold getMostSold = new Controller.GetMostSold();
                attributes.put("variable","Most_Sold");
                attributes.put("mostsold", getMostSold.getMostSold());
            }
            else if(button.equals("return_button") ){
                response.redirect("/Adminpage");
            }
            return modelAndView(attributes, "Webshop/graphs.vm");
        },new VelocityTemplateEngine());
    }
}
