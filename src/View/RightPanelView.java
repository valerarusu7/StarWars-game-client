package View;

import ViewModel.RightPanelViewModel;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import java.util.ArrayList;

public class RightPanelView extends View implements LocalListener<Object, Object> {
    private RightPanelViewModel viewModel;
    private String messages;
    @FXML
    private VBox activePlayers;
    @FXML
	private TextField message;
    @FXML
	private JFXTextArea chatArea;

    public void init(RightPanelViewModel viewModel, MainView mainView) {
        this.viewModel = viewModel;
        super.setMainView(mainView);
        viewModel.addListener(this);
        loadOnlineUsers();
        onEnter();
        messages = "";
    }

    private void onEnter() {
        message.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    sendMessage();
                }
            }
        });
    }

    private void addOnlineUserButton(String username) {
        Platform.runLater(() -> {
            activePlayers.getChildren().add(createButton(username));
        });
    }

    private void removeOnlineUserButton(String username) {
        Platform.runLater(() -> {
            activePlayers.getChildren().remove(activePlayers.lookup("#" + username));
        });
    }

    private void loadOnlineUsers() {
        ArrayList<String> onlineUsers = viewModel.loadOnlineUsers();
        for (String user : onlineUsers) {
            addOnlineUserButton(user);
        }
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        switch (event.getPropertyName()) {
            case "userConnected":
                addOnlineUserButton(event.getValue1().toString());
                break;
            case "userDisconnected":
                removeOnlineUserButton(event.getValue1().toString());
                break;
			case "newMessage":
				updateChat((String)event.getValue1());
				break;
        }
    }

   @FXML
	public void sendMessage() {
    	if(message.getText() != null && !(message.getText().equals(""))) {
    		String msg = message.getText().replace("\n", "");
    		viewModel.sendMessage(msg);
    		message.setText("");
	   }
   }

   private void updateChat(String message) {
    	messages += message + "\n";
    	chatArea.setText(messages);
   }
}
