package View.StatisticsViews;

import View.View;
import ViewModel.MainViewModel;
import ViewModel.StatisticsViewModels.StatisticsPanelViewModel;
import javafx.fxml.FXML;

public class StatisticsPanelView extends View {
    @FXML
    private OverallStatisticsView overallStatisticsController;

    @FXML
    private PersonalStatisticsView personalStatisticsController;

    @FXML
    private GameStatisticsView gameStatisticsController;

    private StatisticsPanelViewModel statisticsPanelViewModel;

    public void init(MainViewModel mainViewModel){
        this.statisticsPanelViewModel = mainViewModel.getStatisticsPanelViewModel();

        overallStatisticsController.init(mainViewModel.getOverallStatisticsViewModel());
        personalStatisticsController.init(mainViewModel.getPersonalStatisticsViewModel());
        gameStatisticsController.init(mainViewModel.getGameStatisticsViewModel());
    }
}
