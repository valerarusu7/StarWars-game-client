package ViewModel;

import Model.Domain.Bullet;
import Model.Domain.Player;
import Model.Domain.PlayerList;
import Model.Mediator.Model;
import View.GameView;
import ViewModel.GameViewModels.BulletViewModel;
import ViewModel.GameViewModels.CharacterViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeProxy;

public class GameViewModel implements LocalListener<Object, Object>, LocalSubject<Object, Object> {
    private Model model;
    private ObservableList<Player> playerList;
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean canShoot;
    private PropertyChangeProxy<Object, Object> property;
    private Player currentPlayer;
    private double[] coordinates;

    public GameViewModel(Model model) {
        this.model = model;
        model.addListener(this);
        playerList = FXCollections.observableArrayList();
        this.coordinates = new double[2];
        property = new PropertyChangeProxy<>(this, true);
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
        canShoot = true;
    }

    public ObservableList<Player> getPlayerList() {
        return playerList;
    }

    public CharacterViewModel newCharacterViewModel(Player player) {
        return new CharacterViewModel(model, player);
    }

    public BulletViewModel newBulletViewModel(Bullet bullet) {
        return new BulletViewModel(bullet);
    }

    public synchronized void setCoordinates() {
        if (isMoving()) {
            model.sendCoordinates(coordinates, null);
        }
    }

    public void onKeyPressedHandler(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
						coordinates[1] = -currentPlayer.getCharacter().getSpeed();
						movingUp = true;
                        break;
                    case DOWN:
						coordinates[1] = +currentPlayer.getCharacter().getSpeed();
						movingDown = true;
                        break;
                    case RIGHT:
						coordinates[0] = +currentPlayer.getCharacter().getSpeed();
						movingRight = true;
                        break;
                    case LEFT:
						coordinates[0] = -currentPlayer.getCharacter().getSpeed();
						movingLeft = true;
                        break;
					case SHIFT:
						currentPlayer.getCharacter().startRunning();
						break;
                    case SPACE:
                    	if(canShoot) {
							model.sendCoordinates(currentPlayer.getBulletStartingPosition(), currentPlayer.getVelocity());
							canShoot = false;
						}
						break;
                }
            }
        });
    }

    public void onKeyReleasedHandler(Scene scene) {
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        coordinates[1] = 0;
                        movingUp = false;
                        break;
                    case DOWN:
                        coordinates[1] = 0;
                        movingDown = false;
                        break;
                    case RIGHT:
                        coordinates[0] = 0;
                        movingRight = false;
                        break;
                    case LEFT:
                        coordinates[0] = 0;
                        movingLeft = false;
                        break;
					case SHIFT:
						currentPlayer.getCharacter().stopRunning();
						break;
					case SPACE:
						canShoot = true;
						break;
                }
            }
        });
    }

    public void loadPlayers() {
        PlayerList connectedPlayers = model.getConnectedPlayers();
        for (int i = 0; i < connectedPlayers.size(); i++) {
            playerList.add(connectedPlayers.get(i));
            if (playerList.get(i).getId() == model.getCurrentPlayer().getId()) {
                currentPlayer = playerList.get(i);
            }
        }
    }

    private boolean isMoving() {
        return movingDown || movingUp || movingLeft || movingRight;
    }

    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        switch (event.getPropertyName()) {
            // Initializing current user, without this, NullPointerException !!
            case "fire":
                property.firePropertyChange("fire", event.getValue1(), event.getValue2());
                break;
            case "killedPlayer":
                movingDown = false;
                movingUp = false;
                movingLeft = false;
                movingRight = false;
                property.firePropertyChange("killedPlayer", event.getValue1(), event.getValue2());
                break;
        }
    }

    @Override
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener);
    }

    @Override
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener);
    }
}
