package View;

import ViewModel.EmbeddedViewModel;
import ViewModel.MainViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class EmbeddedView extends View {
    private EmbeddedViewModel embeddedViewModel;
    @FXML
    private StackPane content;
    @FXML
    private LeftPanelView leftPanelController;
    @FXML
    private RightPanelView rightPanelController;

    public void init(MainViewModel mainViewModel, MainView mainView) {
        this.embeddedViewModel = mainViewModel.getEmbeddedViewModel();
        leftPanelController.init(mainViewModel.getLeftPanelViewModel(), mainView);
        rightPanelController.init(mainViewModel.getRightPanelViewModel(), mainView);

        Platform.runLater(() -> {
            setContent("Rules");
        });
    }

    public void setContent(Parent parent) {
        content.getChildren().clear();
        content.getChildren().add(parent);
    }
}
