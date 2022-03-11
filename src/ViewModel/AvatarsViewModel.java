package ViewModel;

import Model.Mediator.Model;

public class AvatarsViewModel {
    private Model model;
    private String chosenAvatarImage;

    public AvatarsViewModel(Model model) {
        this.model = model;
    }

    public void changeAvatar(){
        if (!chosenAvatarImage.equals("")) {
            model.setAvatar(chosenAvatarImage);
        }
    }

    public void setChosenAvatarImage(String chosenAvatarImage) {
        this.chosenAvatarImage = chosenAvatarImage;
    }
}
