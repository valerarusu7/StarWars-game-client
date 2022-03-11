package ViewModel;

import Model.Mediator.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EditProfileViewModel {
    private Model model;
    private StringProperty changedUsername;
    private StringProperty changedEmail;
    private StringProperty changedPassword;
    private StringProperty changedConfirmPassword;
    private StringProperty errorLabel;
    private String changedAvatarUrl;

    public EditProfileViewModel(Model model) {
        this.model = model;

        this.changedUsername = new SimpleStringProperty();
        this.changedEmail = new SimpleStringProperty();
        this.changedPassword = new SimpleStringProperty();
        this.changedConfirmPassword = new SimpleStringProperty();
        this.errorLabel = new SimpleStringProperty();
        changedAvatarUrl = "";
    }

    public void updateUsername() {
        if (changedUsername.isNull().get()) {
            errorLabel.set("No changes detected");
            return;
        }

        try {
            model.updateUsername(changedUsername.get());
            errorLabel.set("Username updated");
        } catch (IllegalArgumentException e) {
            errorLabel.set(e.getMessage());
        }

        changedUsername.set("");
    }

    public void updatePassword() {
        if (changedPassword.isNull().get()) {
            errorLabel.set("No changes detected");
            return;
        } else if (changedConfirmPassword.isNull().get()) {
            errorLabel.set("No changes detected");
            return;
        } else if (!changedPassword.get().equals(changedConfirmPassword.get())) {
            errorLabel.set("Passwords do not match");
            return;
        } else if (changedPassword.get().length() < 6){
            errorLabel.set("Password has to be at least 6 characters");
            return;
        }

        try {
            model.updatePassword(changedPassword.get());
            errorLabel.set("Password updated");
        } catch (IllegalArgumentException e) {
            errorLabel.set(e.getMessage());
        }

        changedPassword.set("");
        changedConfirmPassword.set("");
    }

    public void updateEmail() {
        if (changedEmail.isNull().get()) {
            errorLabel.set("No changes detected");
            return;
        }

        try {
            model.updateEmail(changedEmail.get());
            errorLabel.set("Email updated");
        } catch (IllegalArgumentException e) {
            errorLabel.set(e.getMessage());
        }

        changedEmail.set("");
    }

    public void updateAvatar() {

        if (changedAvatarUrl.equals("")) {
            errorLabel.set("No changes detected");
            return;
        }

        try {
            model.updateAvatar(changedAvatarUrl);
            errorLabel.set("Avatar changed");
        } catch (IllegalArgumentException e) {
            errorLabel.set(e.getMessage());
        }
    }

    public void setChosenAvatarImage(String avatarUrl) {
        changedAvatarUrl = avatarUrl;
    }

    public void resetFields(){
        changedUsername.set("");
        changedPassword.set("");
        changedConfirmPassword.set("");
        changedEmail.set("");
        changedAvatarUrl = "";
        errorLabel.set("");
    }

    public StringProperty changedUsernameProperty() {
        return changedUsername;
    }

    public StringProperty changedEmailProperty() {
        return changedEmail;
    }

    public StringProperty changedPasswordProperty() {
        return changedPassword;
    }

    public StringProperty changedConfirmPasswordProperty() {
        return changedConfirmPassword;
    }

    public StringProperty errorLabelProperty() {
        return errorLabel;
    }
}
