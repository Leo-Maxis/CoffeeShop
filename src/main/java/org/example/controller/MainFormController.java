package org.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.dao.CustomerDAO;
import org.example.dao.MenuDAO;
import org.example.dao.ProductDAO;
import org.example.entity.Data;
import org.example.entity.Product;

import java.io.File;
import java.net.URL;
import java.sql.Date;
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
    private TextField menu_amount;

    @FXML
    private Button menu_btn;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<Product, String> menu_col_ProductName;

    @FXML
    private TableColumn<Product, String> menu_col_price;

    @FXML
    private TableColumn<Product, String> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<Product> menu_tableView;

    @FXML
    private Label menu_total;

    @FXML
    private Label username_lbl;

    @FXML
    private AnchorPane dashboard_form;


    private Alert alert;
    private Image image;

    private ObservableList<Product> cardListData = FXCollections.observableArrayList();

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
    private String[] statusList = {"Available", "Unavailable"};
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

    //load product card into menu
    public void menuDisplayCard() {
        try {
            ProductDAO productDAO = new ProductDAO();
            ObservableList<Product> productsList = productDAO.getProductCard();
            cardListData.clear();
            cardListData.addAll(productsList);
            int row = 0;
            int column =0;
            menu_gridPane.getChildren().clear();
            menu_gridPane.getRowConstraints().clear();
            menu_gridPane.getColumnConstraints().clear();
            for (int i = 0; i < cardListData.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/org/example/coffeeshop/layout/cardProductLayout.fxml"));
                    AnchorPane pane = loader.load();
                    CardProductController cardProductController = loader.getController();
                    cardProductController.setData(cardListData.get(i));
                    if (column == 3) {
                        column = 0;
                        row +=1;
                    }
                    menu_gridPane.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(10));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int cID;
    public void customerID() {
        try {
            CustomerDAO customerDAO = new CustomerDAO();
            cID = customerDAO.maxCustomerID();
            int checkID = 0;
            checkID = customerDAO.checkCustomerID();
            if (cID == 0) {
                cID +=1;
            }else if (cID == checkID) {
                cID +=1;
            }
            Data.setcID(cID);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
        }else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            inventoryTypeList();
            inventoryStatusList();
            inventoryProductsList();
        }else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            menuDisplayCard();
            menuDisplayOrder();
            menuDisplayTotal();
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
        Data.setPath(product.getImage());
        String path = "File:" + product.getImage();
        image = new Image(path, 120, 127, false, true);
        Data.setDate(String.valueOf(product.getDate()));
        Data.setId(product.getId());
        inventory_ImageView.setImage(image);
    }

    //update product button
    public void inventoryUpdateBtn() {
        if (iventory_ProductID.getText().isEmpty() || iventory_ProductName.getText().isEmpty() || iventory_type.getSelectionModel().getSelectedItem() == null
                || iventory_stock.getText().isEmpty() || iventory_price.getText().isEmpty() || iventory_status.getSelectionModel().getSelectedItem() == null || Data.getPath() == null
        || Data.getId() == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE PRoduct ID: " + iventory_ProductID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                try {
                    Product product = new Product();
                    product.setProductID(iventory_ProductID.getText());
                    product.setProductName(iventory_ProductName.getText());
                    product.setType((String) iventory_type.getSelectionModel().getSelectedItem());
                    product.setStock(Integer.parseInt(iventory_stock.getText()));
                    product.setPrice(Double.valueOf(iventory_price.getText()));
                    product.setStatus((String) iventory_status.getSelectionModel().getSelectedItem());
                    String path = Data.getPath();
                    path = path.replace("\\","\\\\");
                    product.setImage(path);
                    product.setDate(Date.valueOf(Data.getDate()));
                    product.setId(Data.getId());
                    ProductDAO dao = new ProductDAO();
                    var result = dao.updateProduct(product);
                    if (result) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully updated!");
                        alert.showAndWait();
                        inventoryProductsList();
                        inventoryClearBtn();
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled.");
                alert.showAndWait();
            }
        }
    }

    public void inventoryDeleteBtn() {
        if (Data.getId() == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE PRoduct ID: " + iventory_ProductID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                try {
                    Product product = new Product();
                    product.setId(Data.getId());
                    int id = Data.getId();
                    ProductDAO dao = new ProductDAO();
                    var result = dao.deleteProduct(id);
                    if (result) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("successfully Deleted!");
                        alert.showAndWait();
                        inventoryProductsList();
                        inventoryClearBtn();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled.");
                alert.showAndWait();
            }
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

    //menuShowData
    public void menuDisplayOrder() {
        try {
            MenuDAO menuDAO = new MenuDAO();
            ObservableList<Product> listMenu = menuDAO.getMenuOrder();
            menu_col_ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            menu_tableView.setItems(cardListData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Menu Display Total
    private double totalP;
    public void menuGetTotal() {
        customerID();
        try {
            MenuDAO dao = new MenuDAO();
            totalP  =  dao.menuGetTotal(Data.getcID());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("$" + totalP);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Display inventory form
        displayUsername();
        inventoryTypeList();
        inventoryStatusList();
        inventoryProductsList();

        //Display menu form
        menuDisplayCard();
        menuDisplayOrder();
        menuDisplayTotal();
    }
}
