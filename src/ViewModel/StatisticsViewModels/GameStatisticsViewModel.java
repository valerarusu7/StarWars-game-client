package ViewModel.StatisticsViewModels;

import Model.Mediator.Model;
import View.TableData.GeneralTableData;

import java.util.ArrayList;

public class GameStatisticsViewModel {
    private Model model;

    public GameStatisticsViewModel(Model model) {
        this.model = model;
    }

    public ArrayList<GeneralTableData> getGameStatistics() {
        ArrayList<GeneralTableData> generalGameStatistics = new ArrayList<>();


        // In case user does not have any games yet, no data displayed.
        ArrayList<Object[]> returnedObjects;
        try {
            returnedObjects = model.getGameStatistics();
        } catch (IllegalArgumentException e){
            return new ArrayList<GeneralTableData>();
        }


        for (int i = 0; i < returnedObjects.size(); i++) {
            Object[] row = returnedObjects.get(i);
            GeneralTableData element = new GeneralTableData(row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString(), row[4].toString());
            generalGameStatistics.add(element);
        }
        return generalGameStatistics;
    }

}
