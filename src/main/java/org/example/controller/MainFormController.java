package org.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.entity.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private ImageView inventory_ImageView;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<?, ?> inventory_col_Date;

    @FXML
    private TableColumn<?, ?> inventory_col_Price;

    @FXML
    private TableColumn<?, ?> inventory_col_Status;

    @FXML
    private TableColumn<?, ?> inventory_col_idProduct;

    @FXML
    private TableColumn<?, ?> inventory_col_productName;

    @FXML
    private TableColumn<?, ?> inventory_col_stock;

    @FXML
    private TableColumn<?, ?> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TableView<?> inventory_tableView;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private TextField iventory_ProductID;

    @FXML
    private TextField iventory_ProductName;

    @FXML
    private TextField iventory_price;

    @FXML
    private ComboBox<?> iventory_status;

    @FXML
    private TextField iventory_stock;

    @FXML
    private ComboBox<?> iventory_type;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private Label username_lbl;

    private Alert alert;
    private String [] typeList = {"Meals", "Drinks"};

    public void inventoryTypeList() {
        List<String> typeL = new ArrayList<>();
        for (String data : typeList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        iventory_type.setItems(listData);
    }

    private String[] statusList = {"Available", "unavailable"};
    public void inventoryStatusList() {
        List<String> statusL = new ArrayList<>();
        for (String data : statusList) {
            statusL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(statusL);
        iventory_status.setItems(listData);
    }

    public void logOut() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to log out?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                //hide main form
                logout_btn.getScene().getWindow().hide();
                //get login form
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/coffeeshop/layout/loginLayout.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Leo Coffee Shop Login");
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void displayUsername() {
        String user = Data.getUsername();
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username_lbl.setText(user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        inventoryTypeList();
        inventoryStatusList();
    }
}
