package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class imat_receipt_item extends AnchorPane {
    private MainViewController parentController;
    private ShoppingItem shoppingItem;

    @FXML
    private ImageView itemImage;
    @FXML
    private Label amountLabel;
    @FXML
    private Label productLabel;
    @FXML
    private Label costLabel;

    IMatDataHandler imatDataHandler;

    public imat_receipt_item(ShoppingItem shoppingItem, MainViewController mainViewController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_receipt_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.shoppingItem = shoppingItem;
        this.parentController = mainViewController;

        imatDataHandler = IMatDataHandler.getInstance();

        populateReceiptItem();
    }

    private void populateReceiptItem() {
        productLabel.setText(shoppingItem.getProduct().getName());
        amountLabel.setText(Double.toString((int) shoppingItem.getAmount()));
        itemImage.setImage(imatDataHandler.getFXImage(shoppingItem.getProduct()));
        costLabel.setText((Double.toString(shoppingItem.getTotal())) + " kr");
    }

}
