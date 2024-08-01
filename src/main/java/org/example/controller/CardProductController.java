package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.dao.ProductDAO;
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

    public void setData(Product prodData) {
        this.prodData = prodData;

        prod_name.setText(prodData.getProductName());
        prodID = prodData.getProductID();
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
        qty = prod_spinner.getValue();
        try {
            ProductDAO productDAO = new ProductDAO();
            int checkStock =0;
            checkStock = productDAO.checkStockProduct(prodID);
            if (checkStock == 0) {
                Product entity = new Product();
                entity.setProductName(prod_name.getText());
                entity.setType(type);
                entity.setPrice(price);
                entity.setImage(prod_image);
                entity.setDate(Date.valueOf(prod_date));
                entity.setProductID(prodID);
                productDAO.updateStockProduct(entity);
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

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
