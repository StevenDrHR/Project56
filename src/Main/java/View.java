import java.util.ArrayDeque;
import java.util.Deque;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by nilmor on 10/27/2016.
 */
public class View {

    Deque<Modal> users = new ArrayDeque<Modal>();

    public void ViewUsers() {
        get("/", (request, response) -> {
            String title = "Registered Users";
            String createUserLink = "<a href='/user/create'>Create users</a>";
            StringBuilder html = new StringBuilder();

            html.append("<h1>").append(title).append("</h1>").append(createUserLink);
            html.append("<hr>");

            if (users.isEmpty()) {
                html.append("<b>No articles have been posted</b>");
            } else {
                for (Modal user : users) {
                    html.append("Title: ").append(user.getUsername())
                            .append("<br/>")
                            .append(user.getCreatedAt())
                            .append("<br/>")
                            .append("Summary: ").append(user.getpassword())
                            .append("<br/>")
                            .append(user.getEditLink()).append(" | ").append(user.getDeleteLink())
                            .append("</p>");
                }
            }
            return html.toString();

        });

        get("/user/create", (request, response) -> {
            StringBuilder form = new StringBuilder();
            form.append("<form id='user-create-form' method='POST' action='/user/create'>")
                    .append("Title: <input type='text' name='username' />")
                    .append("<br/>")
                    .append("Summary: <input type='text' name='password' />")
                    .append("<br/>")
                    .append("</form>")
                    .append("<input type='submit' value='Publish' form='user-create-form' />");

            return form.toString();
        });

        post("/user/create", (request, response) -> {
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            Modal user = new Modal();
            user.UserModal(username, password, "","","");
            users.addFirst(user);
            response.status(201);
            response.redirect("/");
            Controller SaveUser = new Controller();
            SaveUser.DataInsert(users);

            return "";
        });
    }
    public  void RenderRegisterView(){
        Controller renderView = new Controller();
        get("/Home", (req, res) -> renderView.htmlToString("HTML/Index.html"));
    }
    public  void RenderHomeView(){
        Controller renderView = new Controller();
        get("/Register", (req, res) -> renderView.htmlToString("HTML/register.html"));
        post("/Register", (request, response) -> {
            String RegUsername = request.queryParams("RegUsername");
            String RegPassword = request.queryParams("RegPassword");
            String RegEmail = request.queryParams("RegEmail");
            String RegFName = request.queryParams("RegFName");
            String RegLName = request.queryParams("RegLName");
            Modal user = new Modal();
            user.UserModal(RegUsername, RegPassword, RegEmail,RegFName,RegLName);
            users.addFirst(user);
            response.status(201);
            response.redirect("/Home");
            String SaveUser = new Controller().DataInsert(users);
            if (SaveUser.contains("Detail: Key (username)")){
                System.out.println("Account not registered account name is already taken");
            }
            else if(SaveUser.contains("Detail: Key (email)")){
                System.out.println("Account not registered account email is already taken");
            }
            else if (SaveUser.contains("Done")){
                System.out.println("Account succesfully registered");
            }
            else {
                System.out.println("Something went horribly wrong please contact us");
            }
            return "";
        });
    }
}
