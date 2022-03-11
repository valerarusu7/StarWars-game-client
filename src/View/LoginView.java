package View;

import ViewModel.LoginViewModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginView extends View {
    private LoginViewModel viewModel;

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Label loginErrorLabel;

    public void init(LoginViewModel viewModel) {
        this.viewModel = viewModel;
        enterMenuOnEnter();
        setProperties();
        resetFields();
    }

    /**
     * Executes validateLogin() method in LoginViewModel which return boolean.
     * If login is verified, opens MainView window.
     * Else sets error label "Login or Password is incorrect!"
     */
    @FXML
    private void loginButtonPressed() {
        if (viewModel.validateLogin()) {
            openWindow("Embedded");
            resetFields();
        }
    }

    /**
     * Opens RegisterView window
     */
    @FXML
    private void registerButtonPressed() {
        openWindow("Register");
        resetFields();
    }

    public void setProperties() {
        username.setStyle("-fx-text-fill: white");
        passwordField.setStyle("-fx-text-fill: white");
        username.requestFocus();
        username.textProperty().bindBidirectional(viewModel.getUsername());
        passwordField.textProperty().bindBidirectional(viewModel.getPassword());
        loginErrorLabel.textProperty().bindBidirectional(viewModel.getErrorLabel());
    }

    public void enterMenuOnEnter() {
        super.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    loginButtonPressed();
                }
            }
        });
    }

    public void resetFields() {
        username.requestFocus();
        viewModel.resetFields();
    }

    public String getTitle() {
        return super.getTitle();
    }

    public Scene getScene() {
        return super.getScene();
    }
}
