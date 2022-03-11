package View.StatisticsViews;

import View.TableData.PersonalTableData;
import View.View;
import ViewModel.StatisticsViewModels.PersonalStatisticsViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class PersonalStatisticsView extends View {
    private PersonalStatisticsViewModel personalStatisticsViewModel;

    @FXML
    private TableView<PersonalTableData> statisticsTable;
    @FXML
    private TableColumn<PersonalTableData, String> game_id_column;
    @FXML
    private TableColumn<PersonalTableData, String> character_column;
    @FXML
    private TableColumn<PersonalTableData, String> bullets_fired_column;
    @FXML
    private TableColumn<PersonalTableData, String> given_damage_column;
    @FXML
    private TableColumn<PersonalTableData, String> received_damage_column;
    @FXML
    private TableColumn<PersonalTableData, String> distance_column;

    public void init(PersonalStatisticsViewModel personalStatisticsViewModel) {
        this.personalStatisticsViewModel = personalStatisticsViewModel;
        loadTableData();
    }

    public void loadTableData() {
        game_id_column.setCellValueFactory(
                cellData -> cellData.getValue().getGameProperty());
        character_column.setCellValueFactory(
                cellData -> cellData.getValue().getCharacterProperty());
        bullets_fired_column.setCellValueFactory(
                cellData -> cellData.getValue().getBulletsFiredProperty());
        given_damage_column.setCellValueFactory(
                cellData -> cellData.getValue().getGivenDamageProperty());
        received_damage_column.setCellValueFactory(
                cellData -> cellData.getValue().getReceivedDamageProperty());
        distance_column.setCellValueFactory(
                cellData -> cellData.getValue().getDistanceProperty());

        ArrayList<PersonalTableData> personalStatistics = personalStatisticsViewModel.getPersonalStatistics();

        ObservableList<PersonalTableData> personalTableData = FXCollections.observableArrayList();
        for (int i = 0; i < personalStatistics.size(); i++) {
            personalTableData.add(personalStatistics.get(i));
        }
        statisticsTable.setItems(personalTableData);
        statisticsTable.setEditable(false);
    }
}
