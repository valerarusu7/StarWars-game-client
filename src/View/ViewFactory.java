package View;

import View.StatisticsViews.StatisticsPanelView;
import ViewModel.MainViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;

public class ViewFactory {
    private static HashMap<String, View> viewHashMap = new HashMap<>();

    public static View getView(String viewName, MainViewModel mainViewModel, MainView mainView) {
        View currentView = viewHashMap.get(viewName);

        if (currentView == null) {
            switch (viewName) {
                case "Login":
                    currentView = load("res/FXML/Login.fxml", "Login", mainView);
                    ((LoginView) currentView).init(mainViewModel.getLoginViewModel());
                    break;
                case "Register":
                    currentView = load("res/FXML/Register.fxml", "Register", mainView);
                    ((RegisterView) currentView).init(mainViewModel.getRegisterViewModel());
                    break;
                case "Rules":
                    currentView = load("res/FXML/Rules.fxml", "Rules", mainView);
                    ((RulesView) currentView).init(mainViewModel.getRulesViewModel());
                    break;
                case "Avatars":
                    currentView = load("res/FXML/Avatars.fxml", "Avatars", mainView);
                    ((AvatarsView) currentView).init(mainViewModel.getAvatarsViewModel());
                    break;
                case "GamePicker":
                    currentView = load("res/FXML/GamePicker.fxml", "Game Picker", mainView);
                    ((GamePickerView) currentView).init(mainViewModel.getGamePickerViewModel());
                    break;
                case "WaitingRoom":
                    currentView = load("res/FXML/Waiting.fxml", "Waiting Room", mainView);
                    ((WaitingView) currentView).init(mainViewModel.getWaitingViewModel());
                    break;
                case "Game":
                    currentView = load("res/FXML/Game.fxml", "Game", mainView);
                    ((GameView) currentView).init(mainViewModel.getGameViewModel());
                    break;
                case "StatisticsPanel":
                    currentView = load("res/FXML/Statistics/StatisticsPanel.fxml", "Statistics", mainView);
                    ((StatisticsPanelView) currentView).init(mainViewModel);
                    break;
                case "Characters":
                    currentView = load("res/FXML/CharacterList.fxml", "Characters", mainView);
                    ((CharacterListView) currentView).init(mainViewModel.getCharacterListViewModel());
                    break;
                case "Winner":
                    currentView = load("res/FXML/Winner.fxml", "Winner", mainView);
                    ((WinnerView) currentView).init(mainViewModel.getWinnerViewModel());
                    break;
                case "Embedded":
                    currentView = load("res/FXML/Embedded.fxml", "Menu", mainView);
                    ((EmbeddedView) currentView).init(mainViewModel, mainView);
                    break;
                case "EditProfile":
                    currentView = load("res/FXML/EditProfile.fxml", "Edit Profile", mainView);
                    ((EditProfileView) currentView).init(mainViewModel.getEditProfileViewModel());
                    break;

            }
            viewHashMap.put(viewName, currentView);
        }
        return currentView;
    }

    private static View load(String FXMLFile, String title, MainView mainView) {
        View view = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewFactory.class.getResource(FXMLFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            view = loader.getController();
            view.setScene(scene);
            view.setMainView(mainView);
            view.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    public static void removeView(String key){
        viewHashMap.remove(key);
    }

    public static void clearHashMap(){
        viewHashMap.clear();
    }
}