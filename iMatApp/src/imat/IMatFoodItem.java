package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

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
        updateItem();
    }
    private void updateItem() {
        itemImage.setImage(IMatDataHandler.getInstance().getFXImage(product));
        itemName.setText(product.getName());
        itemPrice.setText(Double.toString(product.getPrice()));
        //itemDiff.setText(product.getUnit());
    }
}
