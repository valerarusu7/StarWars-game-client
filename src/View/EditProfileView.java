package View;

import ViewModel.EditProfileViewModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class EditProfileView extends View {
    private EditProfileViewModel viewModel;
    @FXML
    private GridPane buttons;
    @FXML
    private JFXButton changeAvatarButton;
    @FXML
    private JFXButton changeUsernameButton;
    @FXML
    private JFXButton confirmChangePasswordButton;
    @FXML
    private JFXButton changeEmailButton;
    @FXML
    private JFXTextField changeUsername;
    @FXML
    private JFXPasswordField changePassword;
    @FXML
    private JFXPasswordField confirmChangePassword;
    @FXML
    private JFXTextField changeEmail;
    @FXML
    private Label errorLabel;

    public void init(EditProfileViewModel viewModel) {
        this.viewModel = viewModel;
        setProperties();
    }

    private void setProperties() {
        changeUsername.textProperty().bindBidirectional(viewModel.changedUsernameProperty());
        changePassword.textProperty().bindBidirectional(viewModel.changedPasswordProperty());
        confirmChangePassword.textProperty().bindBidirectional(viewModel.changedConfirmPasswordProperty());
        changeEmail.textProperty().bindBidirectional(viewModel.changedEmailProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.errorLabelProperty());
    }

    public void handleButtonAction(ActionEvent event) {
        for (int i = 0; i < buttons.getChildren().size(); i++) {
            buttons.getChildren().get(i).setDisable(false);
            changeAvatarButton.requestFocus();
        }
        ((JFXButton) (event.getSource())).setDisable(true);
        setAvatarImage(((JFXButton) (event.getSource())).getId());
    }

    public void resetFields(){
        viewModel.resetFields();
    }

    @FXML
    private void changeUsernameButtonPressed() {
        viewModel.updateUsername();

    }

    @FXML
    private void changePasswordButtonPressed() {
        viewModel.updatePassword();
    }

    @FXML
    private void changeEmailButtonPressed() {
        viewModel.updateEmail();
    }

    @FXML
    private void changeAvatarButtonPressed(ActionEvent event) {
        viewModel.updateAvatar();
    }

    private void setAvatarImage(String buttonId) {
        switch (buttonId) {
            case "clone1":
                viewModel.setChosenAvatarImage("View/res/avatars/clone-1.png");
                break;
            case "clone2":
                viewModel.setChosenAvatarImage("View/res/avatars/clone-2.png");
                break;
            case "clone3":
                viewModel.setChosenAvatarImage("View/res/avatars/clone-3.png");
                break;
            case "bobaf":
                viewModel.setChosenAvatarImage("View/res/avatars/boba-fett.png");
                break;
            case "jangof":
                viewModel.setChosenAvatarImage("View/res/avatars/jango-fett.png");
                break;
            case "stormtrooper":
                viewModel.setChosenAvatarImage("View/res/avatars/stormtrooper.png");
                break;
            case "darthvader":
                viewModel.setChosenAvatarImage("View/res/avatars/darthvader.png");
                break;
            case "r2d2":
                viewModel.setChosenAvatarImage("View/res/avatars/r2-d2.png");
                break;
            case "xwing":
                viewModel.setChosenAvatarImage("View/res/avatars/x-wing.png");
                break;
            case "obiwan":
                viewModel.setChosenAvatarImage("View/res/avatars/obi-wan-saber.png");
                break;
            case "quigon":
                viewModel.setChosenAvatarImage("View/res/avatars/qui-gon-saber.png");
                break;
            case "darthmol":
                viewModel.setChosenAvatarImage("View/res/avatars/darth-mol-saber.png");
                break;
        }
    }
}



