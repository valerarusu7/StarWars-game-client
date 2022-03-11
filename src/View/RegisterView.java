package View;

import ViewModel.RegisterViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class RegisterView extends View implements LocalListener<Object, Object> {
    private RegisterViewModel viewModel;

    @FXML
    private JFXButton createButton;
    @FXML
    private JFXButton avatarsButton;
    @FXML
    private JFXButton returnButton;
    @FXML
    private TextField username;
    @FXML
    private ImageView avatarImage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField email;
    @FXML
    private Label errorLabel;

    public void init(RegisterViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addListener(this);
        setProperties();
    }

    public void createButtonPressed() {
        boolean accountCreated = viewModel.register();
        if (accountCreated) {
            removeView("Register");
            openWindow("Login");
            viewModel.resetFields();
        }
    }

    public void changeView(ActionEvent actionEvent) {
        if (actionEvent.getSource() == avatarsButton) {
            openWindow("Avatars");
        } else if (actionEvent.getSource() == returnButton) {
            openWindow("Login");
            removeView("Register");
            viewModel.resetFields();
        }
    }

    public void setProperties() {
        username.requestFocus();
        username.textProperty().bindBidirectional(viewModel.getUsername());
        confirmPassword.textProperty().bindBidirectional(viewModel.getPasswordConfirmation());
        email.textProperty().bindBidirectional(viewModel.getEmail());
        errorLabel.textProperty().bindBidirectional(viewModel.getErrorLabel());
        passwordField.textProperty().bindBidirectional(viewModel.getPassword());

        viewModel.setAvatarImage("View/res/avatars/stormtrooper.png");
    }

    public Scene getScene() {
        return super.getScene();
    }

    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        avatarImage.setImage(new Image(event.getValue1().toString()));
        viewModel.setAvatarImage(event.getValue1().toString());
    }
}
