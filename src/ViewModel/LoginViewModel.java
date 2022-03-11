package ViewModel;

import Model.Mediator.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel {
    private Model model;
    private StringProperty username;
    private StringProperty password;
    private StringProperty errorLabel;

    public LoginViewModel(Model model) {
        this.model = model;

        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
    }

    /**
     * Executes validateLogin in Model with two arguments username and password.
     *
     * @return true if login was successful. False if not successful.
     */
    public boolean validateLogin() {
        boolean successfulLogin;
        try {
            successfulLogin = model.validateLogin(username.get(), password.get());
        } catch (IllegalArgumentException e){
            errorLabel.set(e.getMessage());
            return false;
        }

        if (!successfulLogin){
            errorLabel.set("Wrong username or password");
            return false;
        }
        return true;
    }

    public void resetFields(){
        username.set("");
        password.set("");
        errorLabel.set("");
    }

    public StringProperty getUsername() {
        return username;
    }

    public StringProperty getPassword() {
        return password;
    }

    public StringProperty getErrorLabel(){
        return errorLabel;
    }
}
