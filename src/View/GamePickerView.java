package View;

import ViewModel.GamePickerViewModel;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class GamePickerView extends View {
    private GamePickerViewModel viewModel;

    public void init(GamePickerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onevsonePlay() {
        openWindow("WaitingRoom");
        viewModel.pickGameRoomSize(2);
    }

    @FXML
    private void twovstwoPlayer() {
        openWindow("WaitingRoom");
		viewModel.pickGameRoomSize(3);
    }

    @FXML
    private void alonePlay() {
        openWindow("WaitingRoom");
        viewModel.pickGameRoomSize(1);
    }

    public Scene getScene() {
        return super.getScene();
    }

    public String getTitle() {
        return super.getTitle();
    }
}
