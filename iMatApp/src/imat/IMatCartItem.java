package imat;

import imat.MainViewController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

//import java.awt.*;
import java.io.IOException;
import java.util.List;

public class IMatCartItem extends AnchorPane {
    private MainViewController parentController;
    private Product product;

    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productPrice;
    @FXML
    private Label ammountLabel;

    IMatDataHandler iMatDataHandler;

    public IMatCartItem(Product product, MainViewController mainViewController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_cartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        iMatDataHandler = IMatDataHandler.getInstance();
        this.product = product;
        this.parentController = mainViewController;

        populateCartItem();
    }


    private void populateCartItem() {
        productImage.setImage(iMatDataHandler.getFXImage(product));
        productName.setText(product.getName());
        productPrice.setText(Double.toString(product.getPrice()) + "kr/" + product.getUnit());
        updateQuantLabel();
    }





    protected void updateQuantLabel(){

        List<ShoppingItem> items = parentController.shoppingCart.getItems();
        for(ShoppingItem item : items) {
            if(product.getProductId() == item.getProduct().getProductId()) {
                String ammountText = Integer.toString((int) item.getAmount());
                ammountLabel.setText(ammountText);
            }
        }

    }
}