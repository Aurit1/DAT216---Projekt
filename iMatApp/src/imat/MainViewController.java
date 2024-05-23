
package imat;

import java.net.URL;
import java.util.*;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.PopupWindow;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable {

    private Map<String, IMatFoodItem> iMatFoodItemMap = new HashMap<String, IMatFoodItem>();
    private Map<String, IMatCartItem> iMatCartItemMap = new HashMap<String, IMatCartItem>();
    private Hashtable<String, ProductCategory> categoryDict = new Hashtable<>();
    private String searchWord;
    @FXML
    private Label pathLabel;
    @FXML
    private AnchorPane mainHeader;
    @FXML
    private ImageView headerCart;
    @FXML
    private ImageView headerLogo;
    @FXML
    private ImageView headerLogo_checkoutView;
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private TextField headerSearchBar;
    @FXML
    private Label cartNumberLabel;
    @FXML
    private AnchorPane cartNumberPane;

    @FXML
    private FlowPane cartFlowPane;
    @FXML
    private AnchorPane cartPane;

    @FXML
    private AnchorPane checkoutAnchorPane;
    @FXML
    private FlowPane checkoutFlowPane;
    @FXML
    private Label errorLabel;
    @FXML
    private AnchorPane completedPane;
    @FXML
    private ImageView closeIconCheckout;


    //KASSA
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private TextField adressTextField;
    @FXML
    private TextField cardTextField;
    @FXML
    private TextField csvTextField;
    @FXML
    private TextField expirationMonthTextField;
    @FXML
    private TextField expirationYearTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField postalTextField;
    @FXML
    private Button orderButton;
    @FXML
    private FlowPane detailPane;

    @FXML
    private FlowPane receiptFlowPane;
    @FXML
    private AnchorPane receiptViewPane;
    @FXML
    private ImageView headerLogo_receipt;
    @FXML
    private FlowPane receiptItemFlowPane;
    @FXML
    private AnchorPane receiptItemViewPane;
    @FXML
    private AnchorPane cartNumberPane1;
    @FXML
    private Label cartNumberLabel1;


    @FXML
    private ImageView closeIcon;

    @FXML
    private Label totCostLabel;

    private int quantLabelValue;
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private ProductCategory currentProductCategory;

    public ShoppingCart shoppingCart;
    public Customer customer;
    public CreditCard creditCard;

    public List<Order> orderList = new ArrayList<>();

    private List<IMatFoodItem> foodItemList = new ArrayList<IMatFoodItem>();
    private List<IMatCartItem> cartItemList = new ArrayList<IMatCartItem>();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();
        shoppingCart = IMatDataHandler.getInstance().getShoppingCart();

        pathLabel.setText(iMatDirectory);

        for (Product product : iMatDataHandler.getProducts()) {
            IMatFoodItem foodItem = new IMatFoodItem(product, this);
            iMatFoodItemMap.put(product.getName(), foodItem);
        }

        for (Product product : iMatDataHandler.getProducts()) {
            IMatCartItem cartItem = new IMatCartItem(product, this);
            iMatCartItemMap.put(product.getName(), cartItem);
        }

        updateItemList();
        //updateItemListCategory();
        populateDic();
        updateShoppingCart();

        customer = iMatDataHandler.getCustomer();
        creditCard = iMatDataHandler.getCreditCard();

        LoadCustomerDetails();
        //iMatDataHandler.reset();
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
        headerLogo_checkoutView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/logo_hover.png")));
        headerLogo_receipt.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/logo_hover.png")));
    }

    @FXML
    public void MouseLogoExit(){
        headerLogo.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/logo.png")));
        headerLogo_checkoutView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/logo.png")));
        headerLogo_receipt.setImage(new Image(getClass().getClassLoader().getResourceAsStream("imat/resources/figma/logo.png")));
    }

    @FXML
    private void updateItemList() {
        List<Product> products = iMatDataHandler.getProducts();

        mainFlowPane.getChildren().clear();
        for(Product product : products){
            IMatFoodItem foodItem = iMatFoodItemMap.get(product.getName());
            mainFlowPane.getChildren().add(foodItem);
            foodItemList.add(foodItem);
        }

      headerSearchBar.setPromptText("Sök i alla kategorier...");
    }

    private void populateDic() {
        categoryDict.put("Baljväxter", ProductCategory.POD);
        categoryDict.put("Bär", ProductCategory.BERRY);
        categoryDict.put("Grönsaker", ProductCategory.VEGETABLE_FRUIT);
        categoryDict.put("Kål", ProductCategory.CABBAGE);
        categoryDict.put("Meloner", ProductCategory.MELONS);
        categoryDict.put("Rotfrukter", ProductCategory.ROOT_VEGETABLE);
        categoryDict.put("Örtkryddor", ProductCategory.HERB);
        categoryDict.put("Exotiska frukter", ProductCategory.EXOTIC_FRUIT);
        categoryDict.put("Stenfrukter", ProductCategory.FRUIT);
        categoryDict.put("Citrusfrukter", ProductCategory.CITRUS_FRUIT);
        categoryDict.put("Kött", ProductCategory.MEAT);
        categoryDict.put("Fisk", ProductCategory.FISH);
        categoryDict.put("Bröd", ProductCategory.BREAD);
        categoryDict.put("Mejeriprodukter", ProductCategory.DAIRIES);
        categoryDict.put("Mjöl,Socker,Salt", ProductCategory.FLOUR_SUGAR_SALT);
        categoryDict.put("Nötter och frön", ProductCategory.NUTS_AND_SEEDS);
        categoryDict.put("Pasta", ProductCategory.PASTA);
        categoryDict.put("Potatis och ris", ProductCategory.POTATO_RICE);
        categoryDict.put("Drycker Varma", ProductCategory.HOT_DRINKS);
        categoryDict.put("Drycker Kalla", ProductCategory.COLD_DRINKS);
        categoryDict.put("Sötsaker", ProductCategory.SWEET);
    }

    @FXML
    private void updateItemListCategory() {
        List<Product> products = iMatDataHandler.getProducts(currentProductCategory);

        mainFlowPane.getChildren().clear();
        for(Product product : products){
            IMatFoodItem foodItem = iMatFoodItemMap.get(product.getName());
            mainFlowPane.getChildren().add(foodItem);
        }

   //     for (String key : categoryDict.keySet()) {
     //       if(categoryDict.get(key) == currentProductCategory){
       //         headerSearchBar.setPromptText("Sök i " + key +  "...");
         //   }
        //}
    }

    @FXML
    public void categoryButtonPressed(ActionEvent event) {
        Button activeButton = (Button) event.getSource();

        String category = activeButton.getText();
        currentProductCategory = categoryDict.get(category);
        updateItemListCategory();
        IMatDataHandler.getInstance().shutDown();
    }

    @FXML
    public void searchProducts() {
        searchWord = headerSearchBar.getText();
        System.out.println(searchWord);
        mainFlowPane.getChildren().clear();
        for(Product product : iMatDataHandler.findProducts(searchWord)) {
            IMatFoodItem foodItem = iMatFoodItemMap.get(product.getName());
            mainFlowPane.getChildren().add(foodItem);
        }
    }

    @FXML
    public void closeShoppingCartPreview() {
        cartPane.toBack();
        cartPane.setVisible(false);
    }

    @FXML
    public void openShoppingCartPreview() {
        updateShoppingCart();
        cartPane.toFront();
        cartPane.setVisible(true);
    }

    protected void updateShoppingCart() {
        List<ShoppingItem> items = iMatDataHandler.getShoppingCart().getItems();
        cartFlowPane.getChildren().clear();
        cartItemList.clear();
        if(items.isEmpty()) {
            return;
        }

        for(ShoppingItem item : items) {
            IMatCartItem cartItem = iMatCartItemMap.get(item.getProduct().getName());
            cartFlowPane.getChildren().add(cartItem);
            cartItem.updateQuantLabel();
            cartItemList.add(cartItem);
        }
    }

    @FXML
    public void MouseTrap(Event event) {
        event.consume();
    }

    @FXML
    public void BackToMainView() {
        updateItemList();

        checkoutAnchorPane.toBack();
        checkoutAnchorPane.setVisible(false);
        checkoutFlowPane.getChildren().clear();

        receiptViewPane.toBack();
        receiptViewPane.setVisible(false);
        receiptFlowPane.getChildren().clear();
    }

    @FXML
    public void OpenCheckoutView() {
        PopulateCheckoutFlowPane();
        checkoutAnchorPane.toFront();
        checkoutAnchorPane.setVisible(true);
        cartPane.toBack();
        cartPane.setVisible(false);
        orderButton.setText("Skicka beställning");

        mainFlowPane.getChildren().clear();
    }

    protected void PopulateCheckoutFlowPane() {
        System.out.println("Inside PopulateCheckoutFlowPane");
        List<ShoppingItem> items = iMatDataHandler.getShoppingCart().getItems();
        checkoutFlowPane.getChildren().clear();

        for(ShoppingItem item : items) {
            IMatCartItem cartItem = new IMatCartItem(item.getProduct(), this);
            checkoutFlowPane.getChildren().add(cartItem);
        }
    }

    @FXML
    public void OpenProductDetailView(Product product) {
        detailPane.toFront();
        detailPane.setVisible(true);
        detailPane.getChildren().add(new imat_detail(product, this));

    }

    @FXML
    public void closeDetailView() {
        detailPane.getChildren().clear();
        detailPane.setVisible(false);
        detailPane.toBack();
    }

    private void LoadCustomerDetails() {
        adressTextField.setText(customer.getAddress());
        mailTextField.setText(customer.getEmail());
        firstNameTextField.setText(customer.getFirstName());
        surnameTextField.setText(customer.getLastName());
        phoneTextField.setText(customer.getPhoneNumber());
        postalTextField.setText(customer.getPostAddress());
        adressTextField.setText(customer.getAddress());

        cardTextField.setText(creditCard.getCardNumber());
        csvTextField.setText(Integer.toString(creditCard.getVerificationCode()));
        expirationYearTextField.setText(Integer.toString(creditCard.getValidYear()));
        expirationMonthTextField.setText(Integer.toString(creditCard.getValidMonth()));
    }

    @FXML
    public void placeOrder() {
        try {
            customer.setAddress(adressTextField.getText());
            customer.setEmail(mailTextField.getText());
            customer.setFirstName(firstNameTextField.getText());
            customer.setLastName(surnameTextField.getText());
            customer.setPhoneNumber(phoneTextField.getText());
            customer.setPostCode(postalTextField.getText());
            customer.setPostAddress(adressTextField.getText());

            creditCard.setCardNumber(cardTextField.getText());
            creditCard.setHoldersName(firstNameTextField.getText() + surnameTextField.getText());
            creditCard.setVerificationCode(Integer.parseInt(csvTextField.getText()));
            creditCard.setValidYear(Integer.parseInt(expirationYearTextField.getText()));
            creditCard.setValidMonth(Integer.parseInt(expirationMonthTextField.getText()));


            if(iMatDataHandler.isCustomerComplete()) {
                errorLabel.setVisible(false);
                iMatDataHandler.placeOrder();

                iMatDataHandler.shutDown();
                orderButton.setText("Skickat");
                checkoutFlowPane.getChildren().clear();


            }
            else {
                System.out.println("Order is not complete");
            }
        } catch (Exception e) {
            System.out.println(e);
            errorLabel.setVisible(true);
        }

        updateShoppingCart();
        updateQuantLabels();
        //orderList.add()

        completedPane.setVisible(true);
    }

    protected void updateQuantLabels() {

        for(IMatFoodItem item: foodItemList) {
            item.updateQuantLabel();
        }
        if (!cartItemList.isEmpty()) {
            System.out.println(cartItemList.size());
            for(IMatCartItem item: cartItemList) {
                item.updateQuantLabel();
            }
        }
        //PopulateCheckoutFlowPane();

        int ammountOfItems = shoppingCart.getItems().size();
        if(ammountOfItems > 0) {
            ammountOfItems = 0;
            for(ShoppingItem item: shoppingCart.getItems()) {
                ammountOfItems += item.getAmount();
            }
            cartNumberLabel.setText(Integer.toString(ammountOfItems));
            cartNumberLabel1.setText(Integer.toString(ammountOfItems));
            cartNumberPane.setVisible(true);
            cartNumberPane1.setVisible(true);
        }
        else {
            cartNumberPane.setVisible(false);
            cartNumberPane1.setVisible(false);
        }
        totCostLabel.setText(Double.toString(shoppingCart.getTotal()) + "kr");
    }

    private void updateReceiptFlowPane() {
        receiptFlowPane.getChildren().clear();

        for(Order order: iMatDataHandler.getOrders()) {
            receiptFlowPane.getChildren().add(new imat_receipts(order, this));
        }
    }

    @FXML
    public void openReceiptView() {
        receiptViewPane.toFront();
        receiptViewPane.setVisible(true);
        updateReceiptFlowPane();


    }

    private void populateReceiptItemFlowPane(Order order) {
        receiptItemFlowPane.getChildren().clear();

        for (ShoppingItem item: order.getItems()) {
            receiptItemFlowPane.getChildren().add(new imat_receipt_item(item, this));
        }
    }

    @FXML
    public void openReceiptItemView(Order order) {
        populateReceiptItemFlowPane(order);
        receiptItemViewPane.toFront();
        receiptItemViewPane.setVisible(true);
    }

    @FXML
    public void closeReceiptItemView() {
        receiptItemViewPane.setVisible(false);
    }

    @FXML
    public void closeIconMouseEnter() {
        closeIcon.setImage(new Image(Image.class.getClassLoader().getResourceAsStream("imat/resources/icon_close_hover.png")));
    }

    @FXML
    public void closeIconMouseExit() {
        closeIcon.setImage(new Image(Image.class.getClassLoader().getResourceAsStream("imat/resources/icon_close.png")));
    }

    @FXML
    public void checkoutCloseIconMouseEnter() {
        closeIconCheckout.setImage(new Image(Image.class.getClassLoader().getResourceAsStream("imat/resources/icon_close_hover.png")));
    }

    @FXML
    public void checkoutCloseIconMouseExit() {
        closeIconCheckout.setImage(new Image(Image.class.getClassLoader().getResourceAsStream("imat/resources/icon_close.png")));
    }

    @FXML
    public void closeCheckoutPopup() {
        completedPane.setVisible(false);
    }

    @FXML
    public void returnButtonPopup() {
        completedPane.setVisible(false);
        checkoutAnchorPane.toBack();
        checkoutAnchorPane.setVisible(false);
    }
}

