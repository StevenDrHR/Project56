
import View.*;
import org.apache.log4j.BasicConfigurator;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Spark.staticFileLocation("/Webshop");
        String layout = "Css/RegisterScreen.css";
        HomeScreenView createhomeview = new HomeScreenView();
        RegisterScreenView createRegisterView = new RegisterScreenView();
        AdminScreenView createAdminView = new AdminScreenView();
        ProfileScreenView createProfileView = new ProfileScreenView();
        ShopScreenView createShopView = new ShopScreenView();
        ModifyScreenView createModifyView = new ModifyScreenView();
        AddproductScreenView createAddProductView = new AddproductScreenView();
        GraphScreenView createGraphView = new GraphScreenView();
        OwnWishlistScreenView createOwnWishListView = new OwnWishlistScreenView();
        ShoppingcartScreenView createShoppingCartView = new ShoppingcartScreenView();
        OtherWishListView createOtherWishListView = new OtherWishListView();
        AllWishListView createAllWishListView = new AllWishListView();
        FavouriteScreenView createFavouriteView = new FavouriteScreenView();
        OrderHistoryView createOrderHistoryView = new OrderHistoryView();

        createhomeview.RenderHomeView();
        createRegisterView.RenderRegisterView();
        createAdminView.RenderAdminpageView();
        createModifyView.RenderModifyView();
        createProfileView.RenderProfileView();
        createShopView.RenderShopView();
        createAddProductView.RenderAddProductView();
        createGraphView.RenderGraphView();
        createOwnWishListView.RenderOwnWishlistView();
        createShoppingCartView.RenderShoppingcartView();
        createOtherWishListView.RenderOtherWishlistView();
        createAllWishListView.RenderAllWishlistView();
        createFavouriteView.RenderFavouriteView();
        createOrderHistoryView.RenderOrderHistoryView();

    }

}

