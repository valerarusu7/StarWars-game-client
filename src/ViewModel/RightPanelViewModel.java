package ViewModel;

import Model.Mediator.Model;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeProxy;

import java.util.ArrayList;

public class RightPanelViewModel implements LocalListener<Object, Object>, LocalSubject<Object, Object> {
    private Model model;
    private PropertyChangeProxy<Object, Object> property;

    public RightPanelViewModel(Model model) {
        this.model = model;
        this.model.addListener(this);
        property = new PropertyChangeProxy<>(this, true);
    }

    public ArrayList<String> loadOnlineUsers() {
        return model.loadOnlineUsers();
    }

	/**
	 * Part of observer pattern. This method is called every time an event occurs on the other side of observer pattern.
	 * If property is named newMessage informs view that it has to update chat with new message.
	 *
	 * @param event
	 * 		Event that occurred. Contains name of the event and two objects connected to the event called Value1 and Value2.
	 */

	@Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        switch (event.getPropertyName()) {
            case "userConnected":
                property.firePropertyChange("userConnected", event.getValue1(), event.getValue2());
                break;
            case "userDisconnected":
                property.firePropertyChange("userDisconnected", event.getValue1(), event.getValue2());
                break;
			case "newMessage":
				property.firePropertyChange("newMessage", event.getValue1(), event.getValue2());
				break;
        }
    }

	/**
	 * Calls sendMessageOnChat from ModelManager. Sends desired message to the chat.
	 *
	 * @param message
	 * 		Message that has to be send.
	 */

	public void sendMessage(String message) {
    	model.sendMessageOnChat(message);
	}

	/**
	 * Add listener to the class.
	 *
	 * @param listener
	 * 		Class that is being listened to.
	 * @param propertyNames
	 * 		Name of property.
	 * @return Boolean weather the operation was successful.
	 */

	@Override
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener);
    }

	/**
	 * Remove listener from the class.
	 *
	 * @param listener
	 * 		Class that is being listened to.
	 * @param propertyNames
	 * 		Name of property.
	 * @return Boolean weather the operation was successful.
	 */

    @Override
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener);
    }

}
