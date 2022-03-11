package View.GameViews;

import ViewModel.GameViewModels.CharacterViewModel;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterView {

    private CharacterViewModel characterViewModel;
    private ImageView imageView;

    public CharacterView(CharacterViewModel characterViewModel) {
        this.characterViewModel = characterViewModel;
        this.imageView = new ImageView(new Image(characterViewModel.getCharacter().getCharacterImageUrl()));

        Bindings.bindBidirectional(imageView.xProperty(), characterViewModel.xPosProperty());
        Bindings.bindBidirectional(imageView.yProperty(), characterViewModel.yPosProperty());
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void getNewPositions() {
        characterViewModel.getNewPositions();
    }

    public void setInitialPosition() {
        characterViewModel.setInitialPosition();
    }
}
