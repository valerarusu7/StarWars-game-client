package ViewModel;

import Model.Mediator.Model;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeProxy;

public class WaitingViewModel implements LocalListener<Object, Object>, LocalSubject<Object, Object> {
    private Model model;
    private PropertyChangeProxy<Object, Object> property;

    public WaitingViewModel(Model model) {
        this.model = model;
        property = new PropertyChangeProxy<>(this, true);
        model.addListener(this);
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        switch (event.getPropertyName()) {
            case "gameReady":
                property.firePropertyChange("gameReady", null, null);
                break;
            case "gameAborted":
                property.firePropertyChange("gameAborted", null, null);
        }
    }

    @Override
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener);
    }

    @Override
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener);
    }
}
