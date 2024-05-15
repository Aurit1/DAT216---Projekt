
package imat;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.PopupWindow;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class MainViewController implements Initializable {

    private Map<String, IMatFoodItem> iMatFoodItemMap = new HashMap<String, IMatFoodItem>();

    @FXML
    Label pathLabel;
    @FXML
    AnchorPane mainHeader;
    @FXML
    ImageView headerCart;
    @FXML
    ImageView headerLogo;
    @FXML
    FlowPane mainFlowPane;




    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        pathLabel.setText(iMatDirectory);


        for (Product product : iMatDataHandler.getProducts()) {
            IMatFoodItem foodItem = new IMatFoodItem(product, this);
            iMatFoodItemMap.put(product.getName(), foodItem);
        }

        updateItemList();
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
    }

   // recipeContainer.getChildren().clear();
     //   for(Recipe var : bgController.getRecipes()){
       // RecipeListItem RLI = recipeListItemMap.get(var.getName());
        //recipeContainer.getChildren().add(RLI);
}
