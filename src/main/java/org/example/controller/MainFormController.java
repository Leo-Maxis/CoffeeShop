package org.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.dao.ProductDAO;
import org.example.entity.Data;
import org.example.entity.Product;

import java.io.File;
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
    private TableColumn<Product, String> inventory_col_Date;

    @FXML
    private TableColumn<Product, String> inventory_col_Price;

    @FXML
    private TableColumn<Product, String> inventory_col_Status;

    @FXML
    private TableColumn<Product, String> inventory_col_idProduct;

    @FXML
    private TableColumn<Product, String> inventory_col_productName;

    @FXML
    private TableColumn<Product, String> inventory_col_stock;

    @FXML
    private TableColumn<Product, String> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TableView<Product> inventory_tableView;

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
    private Image image;

    //combobox type
    private String [] typeList = {"Meals", "Drinks"};
    public void inventoryTypeList() {
        List<String> typeL = new ArrayList<>();
        for (String data : typeList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        iventory_type.setItems(listData);
    }

    //combobox status
    private String[] statusList = {"Available", "unavailable"};
    public void inventoryStatusList() {
        List<String> statusL = new ArrayList<>();
        for (String data : statusList) {
            statusL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(statusL);
        iventory_status.setItems(listData);
    }

    //log out return login form
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

    //get username login to main form
    public void displayUsername() {
        String user = Data.getUsername();
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username_lbl.setText(user);
    }

    //load data product from database to table view
    public void inventoryProductsList() {
        try {
            ProductDAO productDAO = new ProductDAO();
            ObservableList<Product> productsList = productDAO.findAll();
            inventory_col_idProduct.setCellValueFactory(new PropertyValueFactory<>("productID"));
            inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            inventory_col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
            inventory_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
            inventory_col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
            inventory_tableView.setItems(productsList);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void inventoryAddBtn() {
        if (iventory_ProductID.getText().isEmpty() || iventory_ProductName.getText().isEmpty() || iventory_type.getSelectionModel().getSelectedItem() == null
        || iventory_stock.getText().isEmpty() || iventory_price.getText().isEmpty() || iventory_status.getSelectionModel().getSelectedItem() == null || Data.getPath() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            try {
                String productID = iventory_ProductID.getText();
                ProductDAO dao = new ProductDAO();
                boolean loadProductID = dao.findProductID(productID);
                if (loadProductID) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(iventory_ProductID.getText() + " is already taken");
                    alert.showAndWait();
                }else {
                    Product entity = new Product();
                    entity.setProductID(iventory_ProductID.getText());
                    entity.setProductName(iventory_ProductName.getText());
                    entity.setType((String) iventory_type.getSelectionModel().getSelectedItem());
                    entity.setStock(Integer.parseInt(iventory_stock.getText()));
                    entity.setPrice(Double.valueOf(iventory_price.getText()));
                    entity.setStatus((String) iventory_status.getSelectionModel().getSelectedItem());
                    String path = Data.getPath();
                    path = path.replace("\\","\\\\");
                    entity.setImage(path);
                    entity = dao.insertProduct(entity);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    inventoryProductsList();
                    inventoryClearBtn();
                }

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //select data from table view
    public void inventorySelectData() {
        Product product = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) return;
        iventory_ProductID.setText(product.getProductID());
        iventory_ProductName.setText(product.getProductName());
        iventory_stock.setText(String.valueOf(product.getStock()));
        iventory_price.setText(String.valueOf(product.getPrice()));
        String path = Data.setPath("File:" + product.getImage());
        image = new Image(path, 120, 127, false, true);
        inventory_ImageView.setImage(image);
    }

    //update product button
    public void inventoryUpdateBtn() {
        if (iventory_ProductID.getText().isEmpty() || iventory_ProductName.getText().isEmpty() || iventory_type.getSelectionModel().getSelectedItem() == null
                || iventory_stock.getText().isEmpty() || iventory_price.getText().isEmpty() || iventory_status.getSelectionModel().getSelectedItem() == null || Data.getPath() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
    }

    public void inventoryClearBtn() {

        iventory_ProductID.setText("");
        iventory_ProductName.setText("");
        iventory_type.getSelectionModel().clearSelection();
        iventory_stock.setText("");
        iventory_price.setText("");
        iventory_status.getSelectionModel().clearSelection();
        Data.setPath("");
        inventory_ImageView.setImage(null);

    }

    //make a behavior for import btn
    public void inventoryImportBtn() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            Data.setPath(file.getAbsolutePath());
            image = new Image(file.toURI().toString(),120, 134, false, true);
            inventory_ImageView.setImage(image);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        inventoryTypeList();
        inventoryStatusList();
        inventoryProductsList();
    }
}
