package ViewModel.StatisticsViewModels;

import Model.Mediator.Model;
import View.TableData.PersonalTableData;

import java.util.ArrayList;

public class PersonalStatisticsViewModel {
    private Model model;

    public PersonalStatisticsViewModel(Model model) {
        this.model = model;
    }

    public ArrayList<PersonalTableData> getPersonalStatistics() {
        ArrayList<PersonalTableData> personalUserStatistics = new ArrayList<>();

        ArrayList<Object[]> returnedObjects;
        try {
            returnedObjects = model.getPersonalStatistics();
        } catch (IllegalArgumentException e){
            return new ArrayList<PersonalTableData>();
        }


        for (int i = 0; i < returnedObjects.size(); i++) {
            Object[] row = returnedObjects.get(i);
            PersonalTableData element = new PersonalTableData(row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString(), row[4].toString(), row[5].toString());
            personalUserStatistics.add(element);
        }
        return personalUserStatistics;
    }
}
