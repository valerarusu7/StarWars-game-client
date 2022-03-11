package ViewModel;

import Model.Mediator.Model;

import View.TableData.StatisticsGameData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.util.ArrayList;

public class WinnerViewModel implements LocalListener<Object, Object> {
    private Model model;

    private StringProperty bulletsShotInGame;
    private StringProperty distance;

    public WinnerViewModel(Model model) {
        this.model = model;
        this.bulletsShotInGame = new SimpleStringProperty();
        this.distance = new SimpleStringProperty();
        model.addListener(this);
    }

    public String getWinner() {
        return model.getWinner();
    }

    public void playAgainWithTheSamePlayer(boolean doYouWantToPlay) {
    	model.playAgainWithTheSamePlayer(doYouWantToPlay);
	}

    public ArrayList<StatisticsGameData> getGameStatistics() {
        ArrayList<StatisticsGameData> gameStatistics = new ArrayList<>();
        ArrayList<Object[]> returnedValues = new ArrayList<>();
        Object[] object = new Object[3];

        object[0] = bulletsShotInGame.get();
        object[1] = distance.get();

        returnedValues.add(object);

        for (int i = 0; i < returnedValues.size(); i++) {
            Object[] row = returnedValues.get(i);
            StatisticsGameData element = new StatisticsGameData(row[0].toString(), row[1].toString());
            gameStatistics.add(element);
        }

        /*StatisticsGameData element = new StatisticsGameData(returnedValues.get(0), returnedValues.get(1));*/
        return gameStatistics;
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        if (event.getPropertyName().equals("statistics")) {
            String[] statistics = (String[]) event.getValue2();

            // statistics[0] = id
            // statistics[1] = winner (true/false)
            // statistics[2] = bullets shot in game
            // statistics[3] = distance

            bulletsShotInGame.set(statistics[2]);
            distance.set(statistics[3]);
//            getGameStatistics();
        }
    }
}
