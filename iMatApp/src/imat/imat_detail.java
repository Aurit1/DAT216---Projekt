package imat;

import imat.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductDetail;

import java.io.IOException;

public class imat_detail extends AnchorPane {
    private MainViewController parentController;
    private Product product;

    @FXML
    private Label productName;
    @FXML
    private Label brandLabel;
    @FXML
    private Label originLabel;
    @FXML
    private Label contentLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Label descriptionField;

    @FXML
    private ImageView closeIcon;


    IMatDataHandler imatDataHandler;
    ProductDetail productDetail;

    public imat_detail(Product product, MainViewController mainViewController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_detail.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.parentController = mainViewController;

        imatDataHandler = IMatDataHandler.getInstance();
        productDetail = imatDataHandler.getDetail(product);

        populateContent();
    }

    private void populateContent() {
        productName.setText(product.getName());
        brandLabel.setText(productDetail.getBrand());
        originLabel.setText(productDetail.getOrigin());
        contentLabel.setText(productDetail.getContents());
        productImage.setImage(imatDataHandler.getFXImage(product));
        descriptionField.setText(productDetail.getDescription());
    }

    @FXML
    public void closeIconMouseEnter() {
        closeIcon.setImage(new Image(Image.class.getClassLoader().getResourceAsStream("imat/resources/icon_close_hover.png")));
    }

    @FXML
    public void closeIconMouseExit() {
        closeIcon.setImage(new Image(Image.class.getClassLoader().getResourceAsStream("imat/resources/icon_close.png")));
    }

}