package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class imat_receipts extends AnchorPane {
    private MainViewController parentController;
    private Order order;

    @FXML
    private Label dateLabel;
    @FXML
    private Button openButton;
    @FXML
    private Button addButton;
    @FXML
    private Label costLabel;

    public imat_receipts(Order order, MainViewController mainViewController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_receipts.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.order = order;
        this.parentController = mainViewController;
        populateOldReceipt();
    }

    private void populateOldReceipt() {
        dateLabel.setText(order.getDate().toString());

        float sum = 0;
        for(ShoppingItem item: order.getItems()) {
            sum += (float) item.getTotal();
        }

        costLabel.setText(String.valueOf(sum));
    }

    @FXML
    public void openReceiptItemViewPane() {
        parentController.openReceiptItemView(order);
    }

    @FXML
    public void addButtonPressed() {
        ShoppingCart cart = IMatDataHandler.getInstance().getShoppingCart();
        List<ShoppingItem> items = order.getItems();
        for (ShoppingItem item : items) {
            cart.addItem(item);
        }

        parentController.updateQuantLabels();
    }

}
