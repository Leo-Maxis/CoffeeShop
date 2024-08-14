package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.dao.CustomerDAO;
import org.example.dao.ProductDAO;
import org.example.entity.Customer;
import org.example.entity.Data;
import org.example.entity.Product;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class CardProductController implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    private Product prodData;
    private Image image;

    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;
    private Double price;

    private Alert alert;

    private SpinnerValueFactory<Integer> spin;
    private double totalP;

    public void setData(Product prodData) {
        this.prodData = prodData;

        prodID = prodData.getProductID();
        prod_name.setText(prodData.getProductName());
        type = prodData.getType();
        prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 190, 94, false, true);
        prod_imageView.setImage(image);
        price = prodData.getPrice();
        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
    }

    private int qty;
    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    public void addBtn() {
        MainFormController mainFormController = new MainFormController();
        mainFormController.customerID();

        qty = prod_spinner.getValue();
        try {
            ProductDAO productDAO = new ProductDAO();
            int checkStock = 0;
            checkStock = productDAO.checkStockProduct(prodID);
            if (checkStock == 0) {
                Product productEntity = new Product();
                productEntity.setProductName(prod_name.getText());
                productEntity.setType(type);
                productEntity.setPrice(price);
                productEntity.setImage(prod_image);
                productEntity.setDate(Date.valueOf(prod_date));
                productEntity.setProductID(prodID);
                productDAO.updateStockProduct(productEntity);
            }
            String checkAvailable = "";
            checkAvailable = productDAO.checkAvailableProduct(prodID);
            if (!checkAvailable.equals("Available") || qty == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong!!");
                alert.showAndWait();
            } else {
                if (checkStock < qty) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid. This product is Out of Stock!!");
                    alert.showAndWait();
                } else {
                    String correctedPath = prod_image.replace("\\", "\\\\");
                    Customer customerEntity = new Customer();
                    customerEntity.setCustomerID(Integer.parseInt(String.valueOf(Data.getcID())));
                    customerEntity.setProductID(prodID);
                    customerEntity.setProductName(prod_name.getText());
                    customerEntity.setType(type);
                    customerEntity.setQuantity(qty);
                    totalP = (qty * price);
                    customerEntity.setPrice(totalP);
                    customerEntity.setImage(correctedPath);
                    customerEntity.setEm_username(Data.getUsername());
                    CustomerDAO customerDAO = new CustomerDAO();
                    customerEntity = customerDAO.insertCustomer(customerEntity);
                    int upStock = checkStock - qty;
                    System.out.println("Date: " + prod_date);
                    System.out.println("Image: " + prod_image);
                    Product productEntity = new Product();
                    productEntity.setProductName(prod_name.getText());
                    productEntity.setType(type);
                    productEntity.setStock(upStock);
                    productEntity.setPrice(price);
                    productEntity.setStatus(checkAvailable);
                    productEntity.setImage(correctedPath);
                    productEntity.setDate(Date.valueOf(prod_date));
                    productEntity.setProductID(prodID);
                    productDAO.updateStockProductPlus(productEntity);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

//                    mainFormController.menuDisplayOrder();
//                    mainFormController.menuDisplayTotal();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();
    }
}
