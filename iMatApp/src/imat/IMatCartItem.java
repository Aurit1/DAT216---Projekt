package imat;

import imat.MainViewController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    @FXML
    private ImageView addButton;
    @FXML
    private ImageView subButton;

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
        productPrice.setText(Double.toString(product.getPrice()) + product.getUnit());
        updateQuantLabel();
    }


    public String getName() {
        return product.getName();
    }


    protected void updateQuantLabel(){
        List<ShoppingItem> items = parentController.shoppingCart.getItems();

        if (items.isEmpty()) {
            System.out.println("No shopping cart items found");
            parentController.updateShoppingCart();
            return;
        }
        for(ShoppingItem item : items) {
            System.out.println(item.getProduct().getName());
            if(product.getProductId() == item.getProduct().getProductId()) {
                String ammountText = Integer.toString((int) item.getAmount());
                ammountLabel.setText(ammountText);
                return;
            }
        }
    }

    @FXML
    public void addButtonPressed() {
        parentController.shoppingCart.addProduct(product,true);
        parentController.updateQuantLabels();
    }

    @FXML
    public void removeButtonPressed() {
        var shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        var itemOptional = shoppingCart.getItems().stream().filter(x -> x.getProduct().getProductId() == product.getProductId()).findFirst();
        if (itemOptional.isEmpty())
            return;
        var item = itemOptional.get();
        if ((int)(item.getAmount()) == 1) {
            shoppingCart.removeItem(item);
            parentController.updateShoppingCart();
        }
        else {
            item.setAmount(item.getAmount() - 1);
            shoppingCart.fireShoppingCartChanged(item, false);
        }

        parentController.updateQuantLabels();
    }

    @FXML
    public void addButtonMouseEnter() {
        addButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/plus_button_hover.png")));
    }

    @FXML
    public void addButtonMouseExit() {
        addButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/plus_button.png")));
    }

    @FXML
    public void subButtonMouseEnter() {
        subButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/minus_button_hover.png")));
    }

    @FXML
    public void subButtonMouseExit() {
        subButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/minus_button.png")));
    }

    @FXML
    public void removeButton() {
        iMatDataHandler.getShoppingCart().removeProduct(product);
        parentController.updateQuantLabels();
        parentController.updateShoppingCart();
    }

}