package ViewModel;

import Model.Mediator.Model;

public class GamePickerViewModel {

    private Model model;

    public GamePickerViewModel(Model model) {
        this.model = model;
    }

    public void pickGameRoomSize(int i) {
        model.pickGameRoomSize(i);
    }
}
