package View;

import ViewModel.RulesViewModel;
import javafx.scene.Scene;

public class RulesView extends View {
    private RulesViewModel rulesViewModel;

    public void init(RulesViewModel viewModel) {
        this.rulesViewModel = viewModel;
    }

    public String getTitle() {
        return super.getTitle();
    }

    public Scene getScene() {
        return super.getScene();
    }
}
