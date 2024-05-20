package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.Console;
import java.io.IOException;
import java.util.List;

public class IMatFoodItem extends AnchorPane {
    private MainViewController parentController;
    private Product product;


    @FXML
    ImageView itemImage;
    @FXML
    Label itemName;
    @FXML
    Label itemPrice;
    @FXML
    Label itemDiff;

    @FXML
    ImageView purchaseButton;
    @FXML
    Label quantLabel;
    @FXML
    ImageView subButton;
    @FXML
    ImageView addButton;

    ShoppingItem produktItem;

    public IMatFoodItem(Product product, MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_fooditem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.parentController = mainViewController;

        produktItem = new ShoppingItem(product);

        updateItem();
    }
    private void updateItem() {
        itemImage.setImage(IMatDataHandler.getInstance().getFXImage(product));
        itemName.setText(product.getName());
        itemPrice.setText(Double.toString(product.getPrice()));
       // itemDiff.setText(product.getUnit()); //ser rätt ut
    }

    @FXML
    public void purchaseButtonEnter() {
        purchaseButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/köpknapp_hover.png")));
    }

    @FXML
    public void purchaseButtonExit() {
        purchaseButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/köpknapp.png")));
    }

    //WIP
    @FXML
    public void purchaseButtonPressed() {
        purchaseButton.toBack();
        purchaseButton.setVisible(false);


    }

    @FXML
    private void updateQuantLabel(){
        List<ShoppingItem> items = parentController.shoppingCart.getItems();
        for(ShoppingItem item : items) {
            if(product.getProductId() == item.getProduct().getProductId()) {
                quantLabel.setText(Double.toString(item.getAmount()));
            }
        }

    }

    public void subButtonPressed(){
        //parentController.shoppingCart.removeItem(produktItem);
//        List<ShoppingItem> hehe = parentController.shoppingCart.getItems();
//        if(hehe.isEmpty()) return;
//        for(ShoppingItem item : hehe) {
//            if(product.getClass() == item.getProduct().getClass()) {
//                if(item.getAmount() > 1) {
//                    double ammount = item.getAmount() - 1;
//                    item.setAmount(ammount);
//                }
//                else{
//                    parentController.shoppingCart.removeItem(item);
//                    quantLabel.setText("0");
//                }
//            }
//        }
        var shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        var itemOptional = shoppingCart.getItems().stream().filter(x -> x.getProduct().getProductId() == product.getProductId()).findFirst();
        if (itemOptional.isEmpty())
            return;
        var item = itemOptional.get();
        if ((int)(item.getAmount()) == 1) {
            shoppingCart.removeItem(item);
            quantLabel.setText("0");
        }
        else {
            item.setAmount(item.getAmount() - 1);
            shoppingCart.fireShoppingCartChanged(item, false);
        }

        updateQuantLabel();
    }

    public void addButtonPressed(){
        parentController.shoppingCart.addProduct(product, true);
        updateQuantLabel();
    }


}
