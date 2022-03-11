package View;

import ViewModel.CharacterListViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class CharacterListView extends View {
    @FXML
    AnchorPane characters;

    private CharacterListViewModel viewModel;

    @FXML
    private JFXButton darthVader;
    @FXML
    private JFXButton stormtrooper;
    @FXML
    private JFXButton yoda;
    @FXML
    private JFXButton hanSolo;
    @FXML
    private JFXButton chewbacca;
    @FXML
    private JFXButton enfys;
    @FXML
    private GridPane buttons;
    @FXML
    private ImageView avatar;
    @FXML
    private VBox activePlayers;

    public void init(CharacterListViewModel characterListViewModel) {
        this.viewModel = characterListViewModel;
    }

    /**
     * Taking the user event to consideration. Sets current character of the user based on which button he
     * clicked. Executes method in CharacterListViewModel.
     *
     * @param event Taking ID of the clicked button.
     * @see CharacterListViewModel#setCurrentCharacter(String)
     */
    @FXML
    private void chooseCharacter(ActionEvent event) {
        for (int i = 0; i < buttons.getChildren().size(); i++) {
            if (event.getSource() == darthVader) {
                viewModel.setCurrentCharacter("Darth Vader");
                buttons.getChildren().get(i).setDisable(false);
            } else if (event.getSource() == hanSolo) {
                viewModel.setCurrentCharacter("Han Solo");
                buttons.getChildren().get(i).setDisable(false);
            } else if (event.getSource() == stormtrooper) {
                viewModel.setCurrentCharacter("Stormtrooper");
                buttons.getChildren().get(i).setDisable(false);
            } else if (event.getSource() == chewbacca) {
                viewModel.setCurrentCharacter("Chewbacca");
                buttons.getChildren().get(i).setDisable(false);
            } else if (event.getSource() == yoda) {
                viewModel.setCurrentCharacter("Yoda");
                buttons.getChildren().get(i).setDisable(false);
            } else if (event.getSource() == enfys) {
                viewModel.setCurrentCharacter("Enfys");
                buttons.getChildren().get(i).setDisable(false);
            }
            ((JFXButton) (event.getSource())).setDisable(true);
        }
    }

    /**
     * Returns current view Title from superclass;
     *
     * @return Title;
     */
    public String getTitle() {
        return super.getTitle();
    }

    /**
     * Return current view Scene from superclass.
     *
     * @return Scene
     */
    public Scene getScene() {
        return super.getScene();
    }
}
