package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.entity.Product;

import java.net.URL;
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

    private SpinnerValueFactory<Integer> spin;

    public void setData(Product prodData) {
        this.prodData = prodData;

        prod_name.setText(prodData.getProductName());
        prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 190, 94, false, true);
        prod_imageView.setImage(image);
    }

    private int qty;
    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    public void addBtn() {
        qty = prod_spinner.getValue();
        String check = "";
        String checkAvailable = "";
        if (qty == 0) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
