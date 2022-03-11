package View.StatisticsViews;

import View.TableData.GeneralTableData;
import View.View;
import ViewModel.StatisticsViewModels.GameStatisticsViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class GameStatisticsView extends View {
    private GameStatisticsViewModel gameStatisticsViewModel;

    @FXML
    private TableView<GeneralTableData> statisticsTable;
    @FXML
    private TableColumn<GeneralTableData, String> gameId;
    @FXML
    private TableColumn<GeneralTableData, String> winner;
    @FXML
    private TableColumn<GeneralTableData, String> totalBulletsFired;
    @FXML
    private TableColumn<GeneralTableData, String> totalGivenDamage;
    @FXML
    private TableColumn<GeneralTableData, String> totalDistance;


    public void init(GameStatisticsViewModel gameStatisticsViewModel) {
        this.gameStatisticsViewModel = gameStatisticsViewModel;
        loadTableData();
    }

    public void loadTableData() {
        gameId.setCellValueFactory(
                cellData -> cellData.getValue().getGameProperty());
        winner.setCellValueFactory(
                cellData -> cellData.getValue().getWinnerProperty());
        totalBulletsFired.setCellValueFactory(
                cellData -> cellData.getValue().getTotalBulletsFiredProperty());
        totalGivenDamage.setCellValueFactory(
                cellData -> cellData.getValue().getTotalGivenDamageProperty());
        totalDistance.setCellValueFactory(
                cellData -> cellData.getValue().getTotalDistanceProperty());

        ArrayList<GeneralTableData> generalStatistics = gameStatisticsViewModel.getGameStatistics();

        ObservableList<GeneralTableData> tableData = FXCollections.observableArrayList();

        for (int i = 0; i < generalStatistics.size(); i++) {
            tableData.add(generalStatistics.get(i));
        }

        statisticsTable.setItems(tableData);
        statisticsTable.setEditable(false);
    }
}
