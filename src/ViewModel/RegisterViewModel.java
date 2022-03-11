package ViewModel;

import Model.Mediator.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeProxy;

public class RegisterViewModel implements LocalSubject<Object, Object>, LocalListener<Object, Object> {
    private Model model;
    private StringProperty username;
    private StringProperty password;
    private StringProperty passwordConfirmation;
    private StringProperty email;
    private StringProperty avatarUrl;
    private StringProperty errorLabel;

    private PropertyChangeProxy<Object, Object> property;

    public RegisterViewModel(Model model) {
        this.model = model;
        model.addListener(this, "setAvatar");
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        passwordConfirmation = new SimpleStringProperty();
        email = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
        avatarUrl = new SimpleStringProperty();

        property = new PropertyChangeProxy<>(this, true);
    }

    public boolean register() {
        if(!validEntries()) {
            errorLabel.set("Empty fields");
            return false;
        }

        if (validatePassword()) {
            try {
                model.register(username.get(), password.get(), email.get(), avatarUrl.get());
                return true;
            } catch (Exception e){
                errorLabel.set(e.getMessage());
            }
        }
        return false;
    }

    private boolean validatePassword() {
        if (password.get().length() < 6){
            errorLabel.set("Password has to be at least 6 characters");
            return false;
        }

        boolean samePass = password.get().equals(passwordConfirmation.get());
        if (samePass) {
            return true;
        } else {
            errorLabel.set("Passwords do not match");
            return false;
        }
    }

    private boolean validEntries(){
        return (username.isNotNull().get() && email.isNotNull().get() && password.isNotNull().get() && passwordConfirmation.isNotNull().get());
    }

    public void resetFields(){
        username.set("");
        email.set("");
        password.set("");
        passwordConfirmation.set("");
        errorLabel.set("");
    }

    public void setAvatarImage(String url){
        avatarUrl.set(url);
    }

    public StringProperty getUsername() {
        return username;
    }

    public StringProperty getPassword() {
        return password;
    }

    public StringProperty getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getErrorLabel() {
        return errorLabel;
    }

    @Override
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener);
    }

    @Override
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener);
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        if (event.getPropertyName().equals("setAvatar")){
            property.firePropertyChange("setAvatar", event.getValue1(), event.getValue2());
        }
    }
}
