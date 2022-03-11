package View;

import Model.Domain.Bullet;
import Model.Domain.Player;
import Model.Mediator.ModelManager;
import View.GameViews.BulletView;
import View.GameViews.CharacterView;
import ViewModel.GameViewModel;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameView extends View implements LocalListener<Object, Object> {
    private GameViewModel gameViewModel;
    private Scene scene;
    private Pane root;
    private AnimationTimer timer;
    private ObservableList<Player> playerList;
    private HashSet<CharacterView> characterViews;
    private List<BulletView> bulletViews;

    public GameView() {
    }

    public void init(GameViewModel viewModel) {
        this.scene = getScene();
        this.gameViewModel = viewModel;
        this.root = (Pane) super.getScene().getRoot();
        characterViews = new HashSet<>();
        bulletViews = new ArrayList<>();
        playerList = FXCollections.observableArrayList();
        this.gameViewModel.addListener(this);
        Bindings.bindContentBidirectional(playerList, gameViewModel.getPlayerList());

        gameViewModel.onKeyPressedHandler(scene);
        gameViewModel.onKeyReleasedHandler(scene);

        playerList.addListener(new ListChangeListener<Player>() {
            @Override
            public void onChanged(Change<? extends Player> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        Player player = c.getAddedSubList().get(0);
                        CharacterView newPlayerView = new CharacterView(gameViewModel.newCharacterViewModel(player));
                        newPlayerView.setInitialPosition();
                        characterViews.add(newPlayerView);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(newPlayerView.getImageView());
                            }
                        });
                    }
                }
            }
        });

        loadPlayers();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ModelManager.executorService.execute(() -> {
                    gameViewModel.setCoordinates();
                });

                for (CharacterView characterView : characterViews) {
                    characterView.getNewPositions();
                }
                onUpdate();
            }
        };
        timer.start();
    }

    public void startGame() {
        timer.start();
    }

    public Scene getScene() {
        return super.getScene();
    }

    public String getTitle() {
        return super.getTitle();
    }

    private void onUpdate() {
        for (int i = 0; i < bulletViews.size(); i++) {
            bulletViews.get(i).update();
            if (bulletViews.get(i).getCoordinates()[0] < 0 - 20 || bulletViews.get(i).getCoordinates()[0] > 800 + 20 || bulletViews.get(i).getCoordinates()[1] < 0 - 20 || bulletViews.get(i).getCoordinates()[1] > 600 + 20) {
                bulletViews.remove(bulletViews.get(i));
            }
        }
    }

    public void loadPlayers() {
        gameViewModel.loadPlayers();
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        switch (event.getPropertyName()) {
            case "gameReady":
                Platform.runLater(() -> {
                    startGame();
                    openWindow("Game");
                });
                break;
            case "fire":
                BulletView bulletView = (new BulletView(gameViewModel.newBulletViewModel((Bullet) event.getValue1())));
                Platform.runLater(() -> {
                    root.getChildren().add(bulletView.getBulletView());
                });
                bulletViews.add(bulletView);
                break;
            case "killedPlayer":
                characterViews.clear();
                Platform.runLater(() -> {
                    openWindow("Winner");
                    timer.stop();
                    gameViewModel.removeListener(this);
                    removeView("Game");
                });
                break;
        }
    }
}
