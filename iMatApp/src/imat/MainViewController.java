
package imat;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class MainViewController implements Initializable {

    @FXML
    Label pathLabel;
    @FXML
    AnchorPane MainHeader;
    @FXML
    ImageView headerCart;
    @FXML
    ImageView headerLogo;


    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        pathLabel.setText(iMatDirectory);

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
}