
package imat;

import java.net.URL;
import java.util.*;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.PopupWindow;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable {

    private Map<String, IMatFoodItem> iMatFoodItemMap = new HashMap<String, IMatFoodItem>();
    private Map<String, IMatCartItem> iMatCartItemMap = new HashMap<String, IMatCartItem>();
    private Hashtable<String, ProductCategory> categoryDict = new Hashtable<>();
    private String searchWord;
    @FXML
    private Label pathLabel;
    @FXML
    private AnchorPane mainHeader;
    @FXML
    private ImageView headerCart;
    @FXML
    private ImageView headerLogo;
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private TextField headerSearchBar;

    @FXML
    private FlowPane cartFlowPane;
    @FXML
    private AnchorPane cartPane;

    private int quantLabelValue;
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private ProductCategory currentProductCategory;

    public ShoppingCart shoppingCart;

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();
        shoppingCart = IMatDataHandler.getInstance().getShoppingCart();

        pathLabel.setText(iMatDirectory);

        for (Product product : iMatDataHandler.getProducts()) {
            IMatFoodItem foodItem = new IMatFoodItem(product, this);
            iMatFoodItemMap.put(product.getName(), foodItem);
        }

        for (Product product : iMatDataHandler.getProducts()) {
            IMatCartItem cartItem = new IMatCartItem(product, this);
            iMatCartItemMap.put(product.getName(), cartItem);
        }

        updateItemList();
        //updateItemListCategory();
        populateDic();
        updateShoppingCart();
    }

    @FXML
    public void MouseCartEnter(){
        headerCart.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/Property 1=Hover.png")));
    }

    @FXML
    public void MouseCartExit(){
        headerCart.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/Property 1=Checkout.png")));
    }

    @FXML
    public void MouseLogoEnter(){
        headerLogo.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/logo_hover.png")));
    }

    @FXML
    public void MouseLogoExit(){
        headerLogo.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/logo.png")));
    }

    @FXML
    private void updateItemList() {

      List<Product> products = iMatDataHandler.getProducts();

      mainFlowPane.getChildren().clear();
      for(Product product : products){
          IMatFoodItem foodItem = iMatFoodItemMap.get(product.getName());
          mainFlowPane.getChildren().add(foodItem);
      }

      headerSearchBar.setPromptText("Sök i alla kategorier...");
    }

    private void populateDic() {
        categoryDict.put("Baljväxter", ProductCategory.POD);
        categoryDict.put("Bär", ProductCategory.BERRY);
        categoryDict.put("Grönsaker", ProductCategory.VEGETABLE_FRUIT);
        categoryDict.put("Kål", ProductCategory.CABBAGE);
        categoryDict.put("Meloner", ProductCategory.MELONS);
        categoryDict.put("Rotfrukter", ProductCategory.ROOT_VEGETABLE);
        categoryDict.put("Örtkryddor", ProductCategory.HERB);
        categoryDict.put("Exotiska frukter", ProductCategory.EXOTIC_FRUIT);
        categoryDict.put("Stenfrukter", ProductCategory.FRUIT);
        categoryDict.put("Citrusfrukter", ProductCategory.CITRUS_FRUIT);
        categoryDict.put("Kött", ProductCategory.MEAT);
        categoryDict.put("Fisk", ProductCategory.FISH);
        categoryDict.put("Bröd", ProductCategory.BREAD);
        categoryDict.put("Mejeriprodukter", ProductCategory.DAIRIES);
        categoryDict.put("Mjöl,Socker,Salt", ProductCategory.FLOUR_SUGAR_SALT);
        categoryDict.put("Nötter och frön", ProductCategory.NUTS_AND_SEEDS);
        categoryDict.put("Pasta", ProductCategory.PASTA);
        categoryDict.put("Potatis och ris", ProductCategory.POTATO_RICE);
        categoryDict.put("Drycker Varma", ProductCategory.HOT_DRINKS);
        categoryDict.put("Drycker Kalla", ProductCategory.COLD_DRINKS);
        categoryDict.put("Sötsaker", ProductCategory.SWEET);
    }

    @FXML
    private void updateItemListCategory() {
        List<Product> products = iMatDataHandler.getProducts(currentProductCategory);

        mainFlowPane.getChildren().clear();
        for(Product product : products){
            IMatFoodItem foodItem = iMatFoodItemMap.get(product.getName());
            mainFlowPane.getChildren().add(foodItem);
        }

   //     for (String key : categoryDict.keySet()) {
     //       if(categoryDict.get(key) == currentProductCategory){
       //         headerSearchBar.setPromptText("Sök i " + key +  "...");
         //   }
        //}
    }

    @FXML
    public void categoryButtonPressed(ActionEvent event) {
        Button activeButton = (Button) event.getSource();

        String category = activeButton.getText();
        currentProductCategory = categoryDict.get(category);
        updateItemListCategory();
    }

    @FXML
    public void searchProducts() {
        searchWord = headerSearchBar.getText();
        System.out.println(searchWord);
        mainFlowPane.getChildren().clear();
        for(Product product : iMatDataHandler.findProducts(searchWord)) {
            IMatFoodItem foodItem = iMatFoodItemMap.get(product.getName());
            mainFlowPane.getChildren().add(foodItem);
        }
// 👍
    }

    @FXML
    public void closeShoppingCartPreview() {
        cartPane.toBack();
        cartPane.setVisible(false);
    }

    @FXML
    public void openShoppingCartPreview() {
        updateShoppingCart();
        cartPane.toFront();
        cartPane.setVisible(true);
    }

    private void updateShoppingCart() {
        List<ShoppingItem> items = iMatDataHandler.getShoppingCart().getItems();
        cartFlowPane.getChildren().clear();

        for(ShoppingItem item : items) {
            IMatCartItem cartItem = iMatCartItemMap.get(item.getProduct().getName());
            cartFlowPane.getChildren().add(cartItem);
        }
    }

}

