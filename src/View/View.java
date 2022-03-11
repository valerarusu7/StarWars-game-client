package View;

import ViewModel.MainViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;

public abstract class View {
    private MainView mainView;
    private Scene scene;
    private String title;
    private MainViewModel viewModel;

    public void init(MainView mainView, MainViewModel viewModel) {
        this.mainView = mainView;
        this.viewModel = viewModel;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void openWindow(String id) {
        mainView.openWindow(id);
    }

    public void setContent(String id) {
        mainView.setContent(id);
    }

    public JFXButton createButton(String username) {
        JFXButton playersOnline = new JFXButton();
        playersOnline.setPrefWidth(186);
        playersOnline.setPrefHeight(40);
        playersOnline.setText(username);
        playersOnline.setStyle("-fx-text-fill: white; -fx-background-color:  #557f43; -fx-background-radius: 0; -fx-font-size: 18;");
        playersOnline.setButtonType(JFXButton.ButtonType.RAISED);
        playersOnline.setId(username);
        playersOnline.setFocusTraversable(false);
        return playersOnline;
    }

    public void removeView(String key){
        mainView.removeView(key);
    }



}
