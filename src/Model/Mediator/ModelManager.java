package Model.Mediator;

import Model.Domain.Bullet;
import Model.Domain.Player;
import Model.Domain.PlayerList;
import Model.Domain.User;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeProxy;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModelManager implements Model {
    public static ExecutorService executorService;
    private PlayerList connectedPlayers;

    private Player currentPlayer;
    private User currentUser;

    private ClientInterface client;
    private String characterPicked = "Stormtrooper";
    private int roomSize;
    private String winnerUsername;
    private PropertyChangeProxy<Object, Object> property;

    public ModelManager() {
        property = new PropertyChangeProxy<>(this, true);
        connectedPlayers = new PlayerList();
        client = new RemoteClient(this);
        executorService = Executors.newFixedThreadPool(13);
    }

    /**
     * Validates login in the server database with given arguments as username and password.
     *
     *
     * @param username Username typed in TextField in LoginView.
     * @param password Password typed in PasswordField in LoginView.
     * @return Returns result of login(username, password) method in RemoteClient.
     * @see RemoteClient#login(String, String)
     */
    @Override
    public boolean validateLogin(String username, String password) {
        return client.login(username, password);
    }

    /**
     * Creates account in the server database with given arguments as username and password.
     *
     * @param username  Verified unique username from TextField in RegisterView.
     * @param password  Verified password from PasswordField in RegisterView.
     * @param email     Verified unique email from TextField in RegisterView.
     * @param avatarUrl
     * @see RemoteClient#register(String, String, String, String)
     */
    @Override
    public void register(String username, String password, String email, String avatarUrl) {
        client.register(username, password, email, avatarUrl);
    }

    /**
     * Initialize currentPlayer instance variable with given information from the server so Client can recognize which
     * player is he. Sets ID for current Player.
     *
     * @param user
     */
    @Override
    public void loginValidated(User user) {
        currentUser = user;
        currentPlayer = new Player(user.getUsername());
        currentPlayer.setId(user.getID());
    }

    /**
     * Initialize instance variable with name of chosen character.
     *
     * @param chosenCharacter String passed from CharacterListView based on which character user has chosen.
     */
    @Override
    public void pickCharacter(String chosenCharacter) {
        this.characterPicked = chosenCharacter;
    }

    /**
     * Executes method in RemoteClient. Sending current player ID and picked character as arguments.
     *
     * @see RemoteClient#pickCharacter(int, String)
     */
    @Override
    public void sendPickedCharacterToTheServer() {
        client.pickCharacter(currentPlayer.getId(), characterPicked);
    }


	/**
	 * Adds a player to list of players connected to the game.
	 *
	 * @param player
	 * 		Player who is added to connected players.
	 * @see PlayerList#add(Player)
	 */
	@Override
    public void addPlayer(Player player) {
        connectedPlayers.add(player);
    }

	/**
	 * Using observer pattern sends information to GameViewModel with information about player who was killed. Sets variable winnerUsername with username of player who shot the loser. Clears list of all players
	 * connected to the game.
	 * @param killedID
	 * @param winnerID
	 * @see PlayerList#clear()
	 */
	@Override
    public void killedPlayer(double killedID, double winnerID) {
        this.winnerUsername = connectedPlayers.getPlayerWithID((int) winnerID).getUsername();
        property.firePropertyChange("killedPlayer", killedID, null);
        connectedPlayers.clear();
    }

	/**
	 * Using observer pattern informs GameViewModel that all players are ready to play.
	 */
	@Override
    public void gameReady() {
        property.firePropertyChange("gameReady", null, null);
    }

	/**
	 * Sends information about player's action in the game. If velocity is null it is a movement action, otherwise it is information about bullet shot by the player.
	 *
	 * @param coordinates
	 * 		Player coordinates if velocity is null, bullet starting coordinates if velocity is not null.
	 * @param velocity
	 * 		Velocity of the bullet.
	 * @see RemoteClient#sendInGamePackage(int, double[], int[])
	 */
	@Override
    public void sendCoordinates(double[] coordinates, int[] velocity) {
        client.sendInGamePackage(currentPlayer.getId(), coordinates, velocity);
    }

    @Override
    public void setId(int id) {
        currentPlayer.setId(id);
    }

	/**
	 * Set coordinates of player with ID playerId as coordinates in list of connected players.
	 *
	 * @param playerId
	 * 		ID of the player with new coordinates.
	 * @param coordinates
	 *		New coordinates.
	 * @see PlayerList#setCoordinates(int, double[])
	 */
	@Override
    public void setCoordinates(int playerId, double[] coordinates) {
        connectedPlayers.setCoordinates(playerId, coordinates);
    }

	/**
	 * Get coordinates of player with ID playerId.
	 *
	 * @param playerId
	 * 		ID of the player
	 * @return	coordinates
	 */
	@Override
    public double[] getCoordinates(int playerId) {
        return connectedPlayers.getCoordinates(playerId);
    }

	/**
	 * Calls pickGameRoomSize in RemoteClient. Sends information to the server about picked game room size.
	 *
	 * @param i
	 * 		Desired game room size.
	 * @see RemoteClient#pickGameRoomSize(int)
	 */
	@Override
    public void pickGameRoomSize(int i) {
        this.roomSize = i;
        client.pickGameRoomSize(i);
    }

	/**
	 * Gets desired game room size.
	 *
	 * @return roomSize
	 */
	@Override
    public int getRoomSize() {
        return roomSize;
    }

	/**
	 * Gets information about type of bullet (which is depending on the character choice of the player) and using observer pattern informs GameViewModel about new bullet in the game.
	 * @param id
	 * @param coordinates
	 * @param velocity
	 */

	@Override
    public void newBullet(int id, double[] coordinates, int[] velocity) {
        Bullet bullet = connectedPlayers.getBulletForPlayer(id);
        bullet.setCoordinates(coordinates);
        bullet.setVelocity(velocity);
        property.firePropertyChange("fire", bullet, null);
    }

	/**
	 * Gets all connected players.
	 *
	 * @return	connectedPlayers
	 */
	@Override
    public PlayerList getConnectedPlayers() {
        return connectedPlayers;
    }

	/**
	 * Gets Player who controls character on the screen.
	 * @return
	 */

	@Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

	/**
	 * Gets username of player who won the game.
	 *
	 * @return	winnerUsername
	 */
	@Override
    public String getWinner() {
        return winnerUsername;
    }

    @Override
    public void playAgain() {
        sendPickedCharacterToTheServer();
        pickGameRoomSize(roomSize);
    }

    /**
     * Calls disconnect from  with current player ID in order to disconnect from the game.
     * @see RemoteClient#disconnect(int)
     */
    @Override
    public void closeApplication() {
    	if(currentPlayer != null) {
			client.disconnect(currentPlayer.getId());
		}
    }

	/**
	 * Using observer pattern updates personal statistics of the player with amount of bullets shot in the game, distance that he made and information weather he is a winner or not.
	 * @param id
	 * 		ID of the player whose statistics should be updated.
	 * @param winner
	 * 		Boolean informing weather player is a winner or not.
	 * @param bulletsShotInTheGame
	 * 		Amount of bullets shot in the game by the player.
	 * @param distance
	 * 		Distane made in the game by the player.
	 */
    @Override
    public void updateStatistics(int id, boolean winner, int bulletsShotInTheGame, int distance) {
        String[] statistics = new String[4];

        statistics[0] = String.valueOf(id);
        statistics[1] = String.valueOf(winner);
        statistics[2] = String.valueOf(bulletsShotInTheGame);
        statistics[3] = String.valueOf(distance);
        property.firePropertyChange("statistics", null, statistics);
    }

	/**
	 * Using observer pattern informs RightPanelViewModel about new player connected to the system.
	 *
	 * @param username
	 * 		Username of the player who connected to the system.
	 */

	@Override
    public void userConnected(String username) {
        property.firePropertyChange("userConnected", username, null);
    }

	/**
	 * Using observer pattern informs RightPanelViewModel about player who disconnected from the system.
	 *
	 * @param username
	 * 		Username of player who disconnected from the system.
	 */

	@Override
    public void userDisconnected(String username) {
        property.firePropertyChange("userDisconnected", username, null);
    }

	/**
	 * Calls getOnlineUsers from RemoteClient. Gets List of all users connected to the system.
	 *
	 * @return onlineUsers
	 * @see RemoteClient#getOnlineUsers()
	 */
	@Override
    public ArrayList<String> loadOnlineUsers() {
        return client.getOnlineUsers();
    }

	/**
	 * Calls getPersonalStatistics from RemoteClient. Gets personal statistics of the player.
	 *
	 * @return personalStatistics
	 * @see RemoteClient#getPersonalStatistics(int)
	 */

	@Override
    public ArrayList<Object[]> getPersonalStatistics() {
        return client.getPersonalStatistics(currentPlayer.getId());
    }

	/**
	 * Calls getGameStatistics from RemoteClient. Gets game statistics of the player.
	 *
	 * @return gameStatistics
	 * @see RemoteClient#getGameStatistics(int)
	 */

	@Override
    public ArrayList<Object[]> getGameStatistics() {
        return client.getGameStatistics(currentPlayer.getId());
    }

	/**
	 * Calls getOverallStatistics from RemoteClient. Gets overall statistics of the player.
	 *
	 * @return overallStatistics
	 * @see RemoteClient#getOverallStatistics(int)
	 */
	@Override
    public ArrayList<String> getOverallStatistics() {
        return client.getOverallStatistics(currentPlayer.getId());
    }

	/**
	 * Calls playAgainWithTheSamePlayer from RemoteClient with information weather player wants to play again or not and his ID.
	 *
	 * @param doYouWantToPlay
	 * 		Boolean informing weather user wants to play again or not.
	 */
    @Override
    public void playAgainWithTheSamePlayer(boolean doYouWantToPlay) {
        client.playAgainWithTheSamePlayer(currentPlayer.getId(), doYouWantToPlay);
    }

    /**
     * Using observer pattern informs view model that other player/players didn't want to play again in order to redirect player to the main menu.
     */
	@Override
	public void gameAbortedGoToMainMenu() {
		property.firePropertyChange("gameAborted", null, null);
	}

    /**
     * Calls sendChatMessage from RemoteClient. Sends username of the author and desired message.
     *
     * @param message
     * 		Message that has to be send.
     */
	@Override
	public void sendMessageOnChat(String message) {
    	String msg = currentPlayer.getUsername() + ": " + message;
		client.sendChatMessage(msg);
	}

    /**
     * Using observer pattern informs view model about new message on the chat.
     *
     * @param message
     * 		New message on the chat.
     */
	@Override
	public void newMessageOnTheChat(String message) {
		property.firePropertyChange("newMessage", message, null);
	}

    @Override
    public void setAvatar(String url) {
        property.firePropertyChange("setAvatar", url, null);
    }

    /**
     * Calls exitGame in RemoteClient. Logs of the game.
     * @see RemoteClient#exitGame(int)
     */
    @Override
	public void logOff() {
		client.exitGame(currentPlayer.getId());
	}

    /**
     * Calls updateUsername in RemoteClient. Updates username for actual user if is valid.
     *
     * @param updatedUsername username passed from EditProfileViewModel
     * @return true if username was updated, false if not.
     * @throws IllegalArgumentException with message what went wrong.
     */
    @Override
    public boolean updateUsername(String updatedUsername) {
        boolean usernameUpdated = client.updateUsername(currentUser.getID(), updatedUsername);
        if (usernameUpdated) {
            property.firePropertyChange("usernameUpdated", updatedUsername, null);
        }
        return usernameUpdated;
    }

    /**
     * Calls updatePassword in RemoteClient. Updates password for actual user if is valid.
     *
     * @param updatedPassword password passed from EditProfileViewModel
     * @return true if password was updated, false if not.
     * @throws IllegalArgumentException with message what went wrong.
     */
    @Override
    public boolean updatePassword(String updatedPassword) {
        return client.updatePassword(currentUser.getID(), updatedPassword);
    }

    /**
     * Calls updateEmail in RemoteClient. Updates email for actual user if is valid.
     *
     * @param updatedEmail email passed from EditProfileViewModel
     * @return true if email was updated, false if not.
     * @throws IllegalArgumentException with message what went wrong.
     */
    @Override
    public boolean updateEmail(String updatedEmail) {
        return client.updateEmail(currentUser.getID(), updatedEmail);
    }

    /**
     * Calls updateAvatar in RemoteClient. Updates avatar for actual user if is valid.
     *
     * @param updatedAvatarUrl avatarUrl passed from EditProfileViewModel
     * @return true if avatar was updated, false if not.
     * @throws IllegalArgumentException with message what went wrong.
     */
    @Override
    public boolean updateAvatar(String updatedAvatarUrl) {
        boolean avatarUpdated = client.updateAvatar(currentUser.getID(), updatedAvatarUrl);
        if (avatarUpdated){
            property.firePropertyChange("avatarUpdated", updatedAvatarUrl, null);
        }
        return avatarUpdated;
    }


    /**
     * Returning logged in user.
     *
     * @return current user
     */
    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Add listener to the class.
     *
     * @param listener
     * 		Class that is being listened to.
     * @param propertyNames
     * 		Name of property.
     * @return Boolean weather the operation was successful.
     */
    @Override
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener);
    }

    /**
     * Remove listener from the class.
     *
     * @param listener
     * 		Class that is being listened to.
     * @param propertyNames
     * 		Name of property.
     * @return Boolean weather the operation was successful.
     */
    @Override
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener);
    }
}