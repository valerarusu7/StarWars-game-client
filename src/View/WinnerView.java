package View;

import View.TableData.StatisticsGameData;
import ViewModel.WinnerViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class WinnerView extends View {
    private WinnerViewModel winnerViewModel;

    @FXML
    private Label winnerLabel;

    @FXML
    private TableView<StatisticsGameData> afterGameStatistics;

    @FXML
    private TableColumn<StatisticsGameData, String> bulletsFiredColumn;

    @FXML
    private TableColumn<StatisticsGameData, String> distanceColumn;

    public void init(WinnerViewModel winnerViewModel) {
        super.setTitle("Winner");
        this.winnerViewModel = winnerViewModel;
        winnerLabel.setText("The winner is " + winnerViewModel.getWinner());
        afterGameStatistics.setEditable(false);
        loadStatisticsDataGame();
    }

    @FXML
    private void openMenuView() {
        openWindow("Embedded");
        removeView("Winner");
        winnerViewModel.playAgainWithTheSamePlayer(false);
    }

    public Scene getScene() {
        return super.getScene();
    }

    public String getTitle() {
        return super.getTitle();
    }

    @FXML
    private void playAgain() {
        openWindow("WaitingRoom");
        removeView("Winner");
        winnerViewModel.playAgainWithTheSamePlayer(true);
    }

    public void loadStatisticsDataGame() {
        bulletsFiredColumn.setCellValueFactory(
                cellData -> cellData.getValue().getGameBulletsProperty());
        distanceColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDistanceProperty());

        ArrayList<StatisticsGameData> gameData = winnerViewModel.getGameStatistics();

        ObservableList<StatisticsGameData> tableData = FXCollections.observableArrayList();
        for (int i = 0; i < gameData.size(); i++) {
            tableData.add(gameData.get(i));
        }

        afterGameStatistics.setItems(tableData);
    }
}