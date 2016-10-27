/**
 * Created by nilmor on 10/26/2016.
 */

import java.util.ArrayDeque;
import java.util.Deque;

import static spark.Spark.get;
import static spark.Spark.post;

public class registerView {
    public static Deque<usersModal> users = new ArrayDeque<usersModal>();

    public void controlUsers() {
        get("/", (request, response) -> {
            String title = "Registered Users";
            String createUserLink = "<a href='/user/create'>Create users</a>";
            StringBuilder html = new StringBuilder();

            html.append("<h1>").append(title).append("</h1>").append(createUserLink);
            html.append("<hr>");

            if (registerView.users.isEmpty()) {
                html.append("<b>No articles have been posted</b>");
            } else {
                for (usersModal users : registerView.users) {
                    html.append("Title: ").append(users.getUsername())
                            .append("<br/>")
                            .append(users.getCreatedAt())
                            .append("<br/>")
                            .append("Summary: ").append(users.getpassword())
                            .append("<br/>")
                            .append(users.getEditLink()).append(" | ").append(users.getDeleteLink())
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
                usersModal user = new usersModal(username, password, registerView.users.size() + 1);
                registerView.users.addFirst(user);

                response.status(201);
                response.redirect("/");
                return "";

        });

   /*     get("/users/read/:id",(request, response) -> {
                Integer id = Integer.parseInt(request.params(":id"));
                StringBuilder html = new StringBuilder();
                for(usersModal user : registerView.users) {
                    if(id.equals(user.getId())) {
                        html.append("<a href='/'>Home</a>").append("<p />")
                                .append("Title: ").append(user.getUsername()).append("<br />")
                                .append(user.getCreatedAt())
                                .append("<p>").append(user.getpassword()).append("</p>");

                        break;
                    }
                }
                return html.toString();
       });
    */}

}

