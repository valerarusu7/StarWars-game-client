package ViewModel.StatisticsViewModels;

import Model.Mediator.Model;

import java.util.ArrayList;

public class OverallStatisticsViewModel {
    private Model model;

    public OverallStatisticsViewModel(Model model) {
        this.model = model;
    }

    public ArrayList<String> getOverallStatistics(){
        try {
            return model.getOverallStatistics();
        } catch (IllegalArgumentException e){
            // If user has no data in database, his overall statistics are all 0.
            ArrayList<String> noData = new ArrayList<>();
            for (int i = 0; i < 6; i++){
                noData.add("0");
            }
            return noData;
        }
    }

}
