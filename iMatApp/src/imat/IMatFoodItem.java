package imat;

import javafx.event.ActionEvent;
import javafx.event.Event;
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

    @FXML
    private AnchorPane foodPane;

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
        itemPrice.setText(Double.toString(product.getPrice()) + " " + product.getUnit());
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
    public void purchaseButtonPressed(Event event) {
        purchaseButton.toBack();
        purchaseButton.setVisible(false);

        addButtonPressed(event);

        event.consume();
    }

    protected void updateQuantLabel(){
        List<ShoppingItem> items = parentController.shoppingCart.getItems();
        if (items.isEmpty()) {
            quantLabel.setText("0");
            purchaseButton.setVisible(true);
            purchaseButton.toFront();
        }
        for(ShoppingItem item : items) {
            if(product.getProductId() == item.getProduct().getProductId()) {
                String ammountText = Integer.toString((int) item.getAmount());
                quantLabel.setText(ammountText);
                return;
            }
        }
        quantLabel.setText("0");
        purchaseButton.setVisible(true);
        purchaseButton.toFront();

    }

    @FXML
    public void subButtonPressed(Event event){
        event.consume();
        var shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
        var itemOptional = shoppingCart.getItems().stream().filter(x -> x.getProduct().getProductId() == product.getProductId()).findFirst();
        if (itemOptional.isEmpty())
            return;
        var item = itemOptional.get();
        if ((int)(item.getAmount()) == 1) {
            shoppingCart.removeItem(item);
            purchaseButton.setVisible(true);
            purchaseButton.toFront();
        }
        else {
            item.setAmount(item.getAmount() - 1);
            shoppingCart.fireShoppingCartChanged(item, false);
        }

        parentController.updateShoppingCart();
        parentController.updateQuantLabels();

    }

    @FXML
    public void addButtonPressed(Event event){
        event.consume();
        parentController.shoppingCart.addProduct(product, true);
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
    public void openProductDetailView() {
        System.out.println("haha");
        parentController.OpenProductDetailView(product);
    }
}
