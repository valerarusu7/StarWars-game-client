package ViewModel;

import Model.Domain.User;
import Model.Mediator.Model;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeProxy;

public class LeftPanelViewModel implements LocalListener<Object, Object>, LocalSubject<Object, Object> {
    private Model model;
    private StringProperty username;
    private PropertyChangeProxy<Object, Object> property;

    public LeftPanelViewModel(Model model) {
        this.model = model;
        model.addListener(this, "usernameUpdated", "avatarUpdated");
        username = new SimpleStringProperty();
        property = new PropertyChangeProxy<>(this, true);
    }

    public void goToGameRoomSize() {
        model.sendPickedCharacterToTheServer();
    }

    public void disconnect() {
    	model.logOff();
    }

    public User getCurrentUser(){
        return model.getCurrentUser();
    }

    public StringProperty getUsername() {
        return username;
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
        switch (event.getPropertyName()){
            case "usernameUpdated":
                Platform.runLater(() -> {
                    username.set(event.getValue1().toString());
                });
                break;
            case "avatarUpdated":
                property.firePropertyChange("avatarUpdated", event.getValue1(), event.getValue2());
                break;
        }
    }
}
