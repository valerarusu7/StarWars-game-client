package View;

import ViewModel.AvatarsViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class AvatarsView extends View {
    private AvatarsViewModel viewModel;

    @FXML
    private GridPane buttons;

    public void init(AvatarsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        for (int i = 0; i < buttons.getChildren().size(); i++) {
            buttons.getChildren().get(i).setDisable(false);
        }
        ((JFXButton) (event.getSource())).setDisable(true);
        String clickedButtonId = ((JFXButton) (event.getSource())).getId();
        setAvatarImage(clickedButtonId);
    }

    public Scene getScene() {
        return super.getScene();
    }

    public String getTitle() {
        return super.getTitle();
    }

    private void setAvatarImage(String buttonId){
        switch (buttonId){
            case "clone1":
                viewModel.setChosenAvatarImage("View/res/avatars/clone-1.png");
                break;
            case "clone2":
                viewModel.setChosenAvatarImage("View/res/avatars/clone-2.png");
                break;
            case "clone3":
                    viewModel.setChosenAvatarImage("View/res/avatars/clone-3.png");
                break;
            case "bobaf":
                viewModel.setChosenAvatarImage("View/res/avatars/boba-fett.png");
                break;
            case "jangof":
                viewModel.setChosenAvatarImage("View/res/avatars/jango-fett.png");
                break;
            case "stormtrooper":
                viewModel.setChosenAvatarImage("View/res/avatars/stormtrooper.png");
                break;
            case "darthvader":
                viewModel.setChosenAvatarImage("View/res/avatars/darthvader.png");
                break;
            case "r2d2":
                viewModel.setChosenAvatarImage("View/res/avatars/r2-d2.png");
                break;
            case "xwing":
                viewModel.setChosenAvatarImage("View/res/avatars/x-wing.png");
                break;
            case "obiwan":
                viewModel.setChosenAvatarImage("View/res/avatars/obi-wan-saber.png");
                break;
            case "quigon":
                viewModel.setChosenAvatarImage("View/res/avatars/qui-gon-saber.png");
                break;
            case "darthmol":
                viewModel.setChosenAvatarImage("View/res/avatars/darth-mol-saber.png");
                break;
        }
        viewModel.changeAvatar();
        openWindow("Register");
    }
}
