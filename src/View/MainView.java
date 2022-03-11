package View;

import ViewModel.MainViewModel;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainView {
    private Stage primaryStage;
    private View currentView;
    private MainViewModel viewModel;

    public MainView(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openWindow("Login");
    }

    public void openWindow(String viewName) {
        currentView = ViewFactory.getView(viewName, viewModel, this);
        primaryStage.setScene(currentView.getScene());
        primaryStage.setTitle(currentView.getTitle());
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private Parent loadFXML(String viewName) {
        View returnedView = ViewFactory.getView(viewName, viewModel, this);
        if (viewName.equals("EditProfile")){
            ((EditProfileView) returnedView).resetFields();
        }
        return returnedView.getScene().getRoot();
    }

    public void setContent(String viewName) {
        ((EmbeddedView) currentView).setContent(loadFXML(viewName));
    }

    public void removeView(String key){
        ViewFactory.removeView(key);
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }
}
