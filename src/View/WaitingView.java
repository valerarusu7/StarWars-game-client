package View;

import ViewModel.WaitingViewModel;
import javafx.application.Platform;
import javafx.scene.Scene;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class WaitingView extends View implements LocalListener<Object, Object> {
    private WaitingViewModel waitingViewModel;

    public void init(WaitingViewModel waitingViewModel) {
        this.waitingViewModel = waitingViewModel;
        this.waitingViewModel.addListener(this);
    }

    public Scene getScene() {
        return super.getScene();
    }

    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        switch (event.getPropertyName()) {
            case "gameReady":
                Platform.runLater(() -> {
                    openWindow("Game");
                });
                break;
            case "gameAborted":
                Platform.runLater(() -> {
                    openWindow("Embedded");
                });
        }
    }
}
