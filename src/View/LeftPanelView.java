package View;

import Model.Domain.User;
import ViewModel.LeftPanelViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class LeftPanelView extends View implements LocalListener<Object, Object>{
    private LeftPanelViewModel viewModel;
    @FXML
    private Label username;
    @FXML
    private ImageView avatarImage;

    public void init(LeftPanelViewModel viewModel, MainView mainView) {
        this.viewModel = viewModel;
        viewModel.addListener(this, "avatarUpdated");
        super.setMainView(mainView);

        username.textProperty().bindBidirectional(viewModel.getUsername());
        setDataForUser();
    }

    /**
     * Sends character choice to the server and changes view for GamePicker
     */
    @FXML
    private void playGameButtonPressed() {
        viewModel.goToGameRoomSize();
        setContent("GamePicker");
    }

    /**
     * Changes view for Statistics
     */
    @FXML
    private void statisticsButtonPressed() {
        setContent("StatisticsPanel");
    }

    /**
     * Changes view for Characters
     */
    @FXML
    private void charactersButtonPressed() {
        setContent("Characters");
    }

    /**
     * Changes view for Menu
     */
    @FXML
    private void rulesButtonPressed() {
        setContent("Rules");
    }

    /**
     * Changes view for Login
     */
    @FXML
    private void logOutButtonPressed() {
        viewModel.disconnect();
        ViewFactory.clearHashMap();
//        PropertyChangeThread.closeAll();
    	openWindow("Login");
    }

    @FXML
    private void editProfileButtonPressed() {
        setContent("EditProfile");
    }

    private void setDataForUser(){
        User currentUser = viewModel.getCurrentUser();
        username.textProperty().set(currentUser.getUsername());
        avatarImage.setImage(new Image(currentUser.getAvatar()));
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        avatarImage.setImage(new Image(event.getValue1().toString()));
    }
}
