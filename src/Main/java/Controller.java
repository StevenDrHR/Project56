import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by nilmor on 10/27/2016.
 */
public class Controller{
    static Connection connection;

    public void connection() throws SQLException {
        try {
//            connection = DriverManager.getConnection("jdbc:postgresql://83.86.251.189:5432/postgres2", "postgres", "postgres");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0906986");
        }
        catch(SQLException e){
            System.out.println("Failed");
        }
    }

    public String checkUserStatus(String UserName) throws SQLException {
        connection();       // connect to the database with connection string
        String Querry = "Select username from users where username ='" + UserName + "' and userstatus = 'Blocked'";     // query statement with flexible variable
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();      // execute query
        if (rs.next()) {        // checks if there is a result, go in the if
            return "Blocked";
        }
        return "Available";
    }
    // basically the same logic everywhere
    public String checkUserLevel(String UserName) throws SQLException{
        connection();
        String Querry = "Select username from users where username ='"+UserName+"' and userlevel = 1";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "admin";
        }
        Querry = "Select username from users where username ='"+UserName+"' and userlevel = 0";
        rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "user";
        }
        return "not registered";
    }



    public String RegisterUser( Deque<Modal> list) throws SQLException {
        int userid = 34;
        connection();
        String Querry = "Select username from users where username ='"+list.getFirst().getUsername()+"';";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "Username already exists";
        }
        Querry = "Select email from users where email ='"+list.getFirst().getEmail()+"';";
        rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "This Email adress is already used";
        }
        Querry = "INSERT INTO users (firstname,lastname,email,username,user_password,userlevel,age,userstatus,wishlist) VALUES ('" + list.getFirst().getFname() + "','"+list.getFirst().getLname() +"','"+list.getFirst().getEmail() +"','"+ list.getFirst().getUsername()+"','"+   list.getFirst().getpassword() +"',0,'"+list.getFirst().getAge()+"','Available', 'Private' );";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Something went wrong with the registration";
        }

        Querry ="Select userid from users where email='"+ list.getFirst().getEmail()+"'";
        rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            userid = rs.getInt("userid") ;
        }
        Querry = "INSERT INTO adress (userid,country,streetname,streetnumber,postalcode) VALUES (" + userid + ",'"+list.getFirst().getCounrty() +"','"+list.getFirst().getStreet() +"','"+ list.getFirst().getStreetNumber()+"','"+   list.getFirst().getPostalCode() +"' );";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            Querry = "Delete From users Where userid='"+userid+"';";;
            connection.prepareStatement(Querry).executeUpdate();
            e.printStackTrace();
            String m = e.getMessage();
            return m;
        }
        connection.close();
        return "Done";
    }


    public String LoginUser( Deque<Modal> list) throws SQLException {
        try{
            connection();
            String Querry = "Select userid from users where username='"+ list.getFirst().getUsername()+"' and user_password='"+ list.getFirst().getpassword()+"'";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            if(rs.next()){
                return list.getFirst().getUsername();
            }
            Querry = "Select userid from users where username='"+ list.getFirst().getUsername()+"'";
            rs = connection.prepareStatement(Querry).executeQuery();
            if(rs.next()){
                return "Wrong Password";
            }
            else{return "Wrong Username";

            }
        }
        catch (SQLException e){
            return "failed";
        }
    }
    public List<String> GetUsers() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select username from users where userlevel = 0";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("username"));

        }
        return list;
    }
    public List<String> GetAdmins() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select username from users where userlevel = 1";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("username"));

        }
        return list;
    }
    public List<String> getCarType() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select car_type, Count(car_type) as totalcartype from products group by car_type";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("car_type"));
            list.add(rs.getString("totalcartype"));
        }
        return list;
    }
    public ArrayList<String> getFavourites(String userid) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select p.brand, p.modal, p.car_type, p.price from products p, favourites f where p.productid = f.productid and f.userid = "+userid;
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("car_type"));
            list.add(rs.getString("price"));

        }
        return list;
    }


    public List<String> getMostSold() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select p.modal, p.brand, Count(o.productid)as mostwanted from products p, orders_products o where o.productid = p.productid group by p.modal,p.brand limit 3";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("mostwanted"));
        }
        return list;
    }

    public List<String> GetModels(String Category) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        if(Category.equals("")) {
            String Querry = "Select modal from products";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));

            }
        }
        else if(Category.equals("brand")){
            String Querry = "Select modal from products order by brand ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("type")){
            String Querry = "Select modal from products order by car_type ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("year")){
            String Querry = "Select modal from products order by build_year ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("pricelth")){
            String Querry = "Select modal from products order by price ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("pricehtl")){
            String Querry = "Select modal from products order by price DESC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        return list;
    }

    public List<String> GetBrands(String Category) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        if(Category.equals("")) {
            String Querry = "Select brand from products";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("brand"));

            }
        }
        else if(Category.equals("brand")){
            String Querry = "Select brand from products order by brand ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("brand"));
            }
        }
        else if(Category.equals("type")){
            String Querry = "Select brand from products order by car_type ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("brand"));
            }
        }
        else if(Category.equals("year")){
            String Querry = "Select brand from products order by build_year ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("brand"));
            }
        }
        else if(Category.equals("pricelth")){
            String Querry = "Select brand from products order by price ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("brand"));
            }
        }
        else if(Category.equals("pricehtl")){
            String Querry = "Select brand from products order by price DESC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("brand"));
            }
        }
        return list;
    }

    public List<String> GetType(String Category) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        if(Category.equals("")) {
            String Querry = "Select car_type from products";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("car_type"));

            }
        }
        else if(Category.equals("brand")){
            String Querry = "Select car_type from products order by brand ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("car_type"));
            }
        }
        else if(Category.equals("type")){
            String Querry = "Select car_type from products order by car_type ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("car_type"));
            }
        }
        else if(Category.equals("year")){
            String Querry = "Select car_type from products order by build_year ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("car_type"));
            }
        }
        else if(Category.equals("pricelth")){
            String Querry = "Select car_type from products order by price ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("car_type"));
            }
        }
        else if(Category.equals("pricehtl")){
            String Querry = "Select car_type from products order by price DESC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("car_type"));
            }
        }
        return list;
    }

    public List<String> GetPrice(String Category) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        if(Category.equals("")) {
            String Querry = "Select price from products";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("price"));
            }
        }
        else if(Category.equals("brand")){
            String Querry = "Select price from products order by brand ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("price"));
            }
        }
        else if(Category.equals("type")){
            String Querry = "Select price from products order by car_type ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("price"));
            }
        }
        else if(Category.equals("year")){
            String Querry = "Select price from products order by build_year ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("price"));
            }
        }
        else if(Category.equals("pricelth")){
            String Querry = "Select price from products order by price ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("price"));
            }
        }
        else if(Category.equals("pricehtl")){
            String Querry = "Select price from products order by price DESC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("price"));
            }
        }
        return list;
    }

    public List<String> GetImage(String Category) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        if(Category.equals("")) {
            String Querry = "Select image from products";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("image"));
            }
        }
        else if(Category.equals("brand")){
            String Querry = "Select image from products order by brand ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("image"));
            }
        }
        else if(Category.equals("type")){
            String Querry = "Select image from products order by car_type ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("image"));
            }
        }
        else if(Category.equals("year")){
            String Querry = "Select image from products order by build_year ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("image"));
            }
        }
        else if(Category.equals("pricelth")){
            String Querry = "Select image from products order by price ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("image"));
            }
        }
        else if(Category.equals("pricehtl")){
            String Querry = "Select image from products order by price DESC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("image"));
            }
        }
        return list;
    }

    public List<String> GetProductId(String Category) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        if(Category.equals("")) {
            String Querry = "Select productid from products";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("productid"));
            }
        }
        else if(Category.equals("brand")){
            String Querry = "Select productid from products order by brand ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("productid"));
            }
        }
        else if(Category.equals("type")){
            String Querry = "Select productid from products order by car_type ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("productid"));
            }
        }
        else if(Category.equals("year")){
            String Querry = "Select productid from products order by build_year ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("productid"));
            }
        }
        else if(Category.equals("pricelth")){
            String Querry = "Select productid from products order by price ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("productid"));
            }
        }
        else if(Category.equals("pricehtl")){
            String Querry = "Select productid from products order by price DESC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("productid"));
            }
        }
        return list;
    }

    public List<String> getPinfo(String pid) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select * from products where productid = "+pid;
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while (rs.next()) {
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("car_type"));
            list.add(rs.getString("price"));
        }
        return list;
    }

    public List<String> GetDescription() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select description from products";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while (rs.next()) {
            list.add(rs.getString("description"));

        }
        return list;
    }

    public String DeleteUser(String username) throws SQLException {
        try{
            connection();
            String Querry = "Delete from users where username ='"+username+"';";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }

    public String ResetPassword(String username) throws SQLException{
        try{
            connection();
            String Querry = "Update users set user_password = '12345' where username ='"+username+"';";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
    public String BlockUser(String username) throws SQLException{
        try{
            connection();
            String Querry = "Update users set userstatus = 'Blocked' where username ='"+username+"';";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
    public String UnblockUser(String username) throws SQLException{
        connection();
        String Querry = "Update users set userstatus = 'Available' where username ='"+username+"';";
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
    }

    public String AddProduct(Deque<Modal> list) throws SQLException{
        try{
            connection();
            String Querry = "INSERT INTO products (modal,brand,car_type,build_year,price,deliverytime,description) VALUES ('" + list.getFirst().getModal() + "','"+list.getFirst().getBrand() +"','"+ list.getFirst().getType() +"','"+ list.getFirst().getYear()+"',"+ list.getFirst().getPrice() +",'"+list.getFirst().getDeliverytime()+"','"+list.getFirst().getDescription()+"');";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }

    public String addFavourite(String productid, String userid) throws SQLException{
        try{
            connection();
            String Querry = "INSERT INTO favourites (productid,userid) VALUES ("+productid+", "+userid+");";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }

    public ArrayList<String> getUserData(String username) throws SQLException {
        connection();
        String Querry = "Select * from users where username ='"+username+"';";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        ArrayList<String> result = new ArrayList<>();
        if (rs.next()) {
            result.add(rs.getString("firstname"));
            result.add(rs.getString("lastname"));
            result.add(rs.getString("age"));
            result.add(rs.getString("email"));
            result.add(rs.getString("user_password"));
            result.add(rs.getString("userstatus"));
            result.add(rs.getString("userid"));
            result.add(rs.getString("wishlist"));
        }

        return result;
    }

    public ArrayList GetOrderHistory(String userid) throws SQLException {
        connection();
        String Querry = "Select  op.orderid , op.amount , op.productid from orders_products op,orders o where op.orderid = o.orderid and o.userid = "+userid+";";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        ArrayList result = new ArrayList<>();
        while(rs.next()) {
            result.add(rs.getInt("orderid"));
            result.add(rs.getInt("amount"));
            result.add(rs.getInt("productid"));
        }
        return result;
    }


    public String setToWishlist(String userid, String productid) throws SQLException {
        connection();
        String Querry = "insert INTO wishlist (userid, productid) VALUES ("+userid+" , "+productid+");";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            return "Already added to the wishlist";
        }


        return "Done";
    }

    public List<String> getWishlistinfo(String username) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "Select p.modal, p.brand, p.car_type, p.price from products p, wishlist w, users u where p.productid = w.productid and u.userid = w.userid and u.username = '"+username+"'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while (rs.next()) {
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("car_type"));
            list.add(rs.getString("price"));

        }
        return list;
    }
    public List<String> getPublicusers(String CurrentUser) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connection();
        String Querry = "select username from users where wishlist = 'public' and username != '"+CurrentUser+"'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while (rs.next()) {
            Querry = "select u.username from users u ,wishlist w where u.username ='"+rs.getString("username")+"' and u.userid = w.userid and w.userid > 0;";
            ResultSet rs2 = connection.prepareStatement(Querry).executeQuery();
            if(rs2.next()) {
                list.add(rs2.getString("username"));
            }

        }
        return list;
    }
    public String setWishlistToPublic(String username) throws SQLException{
        connection();
        String Querry = "UPDATE users SET wishlist = 'public' WHERE username = '"+username+"';";
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
    }
    public String setWishlistToPrivate(String username) throws SQLException{
        try{
            connection();
            String Querry = "UPDATE users SET wishlist = 'private' WHERE username = '"+username+"';";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }

    public String SetOrderHistory(String userid,String[] products, String[] amounts) throws SQLException{
        connection();
        int orderid = 0;
        ArrayList<String> list = new ArrayList<String>();
        String Querry = "INSERT INTO orders(userid) Values ("+userid+");";
        connection.prepareStatement(Querry).executeUpdate();
        Querry = "Select orderid from orders where userid = '"+userid+"' order by orderid desc";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            orderid = rs.getInt("orderid") ;
        }
        for (int i = 0; i<products.length; i++) {
            System.out.println(products[i] + " shamlalalalalalal");
            Querry = "INSERT INTO orders_products(orderid,productid,amount) Values(" + orderid + "," + products[i] + "," + amounts[i] + ") ";
            connection.prepareStatement(Querry).executeUpdate();
        }
        return "Done";
    }
    public ArrayList<Integer> checkWishlistStatus() throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        connection();
        String Querry = "Select count(username) AS total from users where wishlist = 'public'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if (rs.next()) {
            list.add(rs.getInt("total"));
        }
        return list;
    }
    public ArrayList<Integer> checkWishlistStatusOwn(String username) throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        connection();
        String Querry = "Select count(username) AS total from users where wishlist = 'public' and username != '"+username+"'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if (rs.next()) {
            list.add(rs.getInt("total"));
        }
        return list;
    }
    public ArrayList<Integer> checkOwnWishlist(String username) throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        connection();
        String Querry = "Select count(p.modal) AS total from products p, wishlist w, users u where p.productid = w.productid and u.userid = w.userid and u.username = '"+username+"'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if (rs.next()) {
            list.add(rs.getInt("total"));
        }
        return list;
    }
}




