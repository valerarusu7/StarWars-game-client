package View.StatisticsViews;

import View.View;
import ViewModel.StatisticsViewModels.OverallStatisticsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class OverallStatisticsView extends View {
    @FXML
    private Label totalBulletsFired;
    @FXML
    private Label totalGivenDamage;
    @FXML
    private Label totalReceivedDamage;
    @FXML
    private Label totalDistance;
    @FXML
    private Label gamesPlayed;
    @FXML
    private Label gamesWon;

    private OverallStatisticsViewModel viewModel;

    public void init(OverallStatisticsViewModel viewModel) {
        this.viewModel = viewModel;
        loadStatistics();
    }

    private void loadStatistics(){
        ArrayList<String> statistics = viewModel.getOverallStatistics();

        totalBulletsFired.textProperty().set(statistics.get(0));
        totalGivenDamage.textProperty().set(statistics.get(1));
        totalReceivedDamage.textProperty().set(statistics.get(2));
        totalDistance.textProperty().set(statistics.get(3));
        gamesPlayed.textProperty().set(statistics.get(4));
        gamesWon.textProperty().set(statistics.get(5));
    }

}
