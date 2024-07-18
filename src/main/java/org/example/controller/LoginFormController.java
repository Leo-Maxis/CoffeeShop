package org.example.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.dao.EmployeeDAO;
import org.example.entity.Employee;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class LoginFormController implements Initializable {
    @FXML
    private TextField fp_answer;

    @FXML
    private FontAwesomeIconView fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgot;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;


    private Alert alert;

    //register user (insert employee)
    public void regBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null || su_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            try {
                //Check if the username already recorded
                String checkUsername = su_username.getText();
                EmployeeDAO employeeDao = new EmployeeDAO();
                boolean loadUsername = employeeDao.findUsername(checkUsername);
                if (loadUsername) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password, atleast 8 characters are needed!");
                    alert.showAndWait();
                } else {
                    //insert employee
                    Employee entity = new Employee();
                    entity.setUsername(su_username.getText());
                    entity.setPassword(su_password.getText());
                    entity.setQuestion(su_question.getSelectionModel().getSelectedItem().toString());
                    entity.setAnswer(su_answer.getText());
                    EmployeeDAO dao = new EmployeeDAO();
                    entity = dao.insert(entity);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Infomation");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();
                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");
                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHave.setVisible(false);
                        side_CreateBtn.setVisible(true);
                    });
                    slider.play();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //Login
    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Username or password can't be empty!");
            alert.showAndWait();
        } else {
            try {
                String username = si_username.getText();
                String password = si_password.getText();
                EmployeeDAO dao = new EmployeeDAO();
                Employee entity = dao.userLogin(username, password);
                if (entity != null) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password!");
                    alert.showAndWait();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // load data to combox questions
    private String[] questionList = {"What is your favorite color?", "What is your  favorite food?", "What is your birthday?"};
    public void regQuestionList() {
        List<String> listQ = new ArrayList<>();
        for(String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }

    //forgot password
    public void switchForgotPassword() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        forgotPassQuestionList();
    }


    //proceed Button
    public void proceedBtn() {
        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null || fp_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            try {
                String username = fp_username.getText();
                String question = String.valueOf(fp_question.getSelectionModel().getSelectedItem());
                String answer = fp_answer.getText();
                EmployeeDAO employeeDAO = new EmployeeDAO();
                Employee entity = employeeDAO.forgotPassword(username, question, answer);
                if (entity != null) {
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //change password
    public void changePassBtn() {
        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            if(np_newPassword.getText().equals(np_confirmPassword.getText())) {
                try {
                    Employee employee = new Employee();
                    employee.setPassword(np_newPassword.getText());
                    employee.setQuestion(String.valueOf(fp_question.getSelectionModel().getSelectedItem()));
                    employee.setAnswer(fp_answer.getText());
                    employee.setUsername(fp_username.getText());
                    EmployeeDAO dao = new EmployeeDAO();
                    var result = dao.updatePassword(employee);
                    if (result) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully changed Password!");
                        alert.showAndWait();

                        si_loginForm.setVisible(true);
                        np_newPassForm.setVisible(false);

                        np_newPassword.setText("");
                        np_confirmPassword.setText("");
                        fp_question.getSelectionModel().clearSelection();
                        fp_answer.setText("");
                        fp_username.setText("");
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Can't change new password!");
                        alert.showAndWait();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Confirm Password is not match new password!");
                alert.showAndWait();
            }
        }
    }


    public void forgotPassQuestionList() {
        List<String> listQ = new ArrayList<>();
        for (String data: questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
    }

    //back button
    public void backToLoginForm() {
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }

    public void backToForgotForm() {
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    //login form <-> register form
    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
                regQuestionList();
            });
            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });
            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}