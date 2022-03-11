package ViewModel.GameViewModels;

import Model.Domain.Character;
import Model.Domain.Player;
import Model.Mediator.Model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CharacterViewModel {
    private Model model;
    private DoubleProperty xPos;
    private DoubleProperty yPos;
    private Player player;

    public CharacterViewModel(Model model, Player player) {
        this.model = model;
        xPos = new SimpleDoubleProperty();
        yPos = new SimpleDoubleProperty();
        this.player = player;
    }

    public void getNewPositions() {
        double[] coordinates = model.getCoordinates(player.getId());
        xPos.set(coordinates[0]);
        yPos.set(coordinates[1]);
    }

    public void setInitialPosition() {
        xPos.set(player.getX());
        yPos.set(player.getY());
    }

    public DoubleProperty xPosProperty() {
        return xPos;
    }

    public DoubleProperty yPosProperty() {
        return yPos;
    }

    public Character getCharacter() {
        return player.getCharacter();
    }

}