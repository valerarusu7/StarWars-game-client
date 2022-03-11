package Model.Mediator;

import Model.Domain.Player;
import Model.Domain.PlayerList;
import Model.Domain.User;
import utility.observer.subject.LocalSubject;

import java.util.ArrayList;

public interface Model extends LocalSubject<Object, Object> {

    /**
     * Validates login in the server database with given arguments as username and password.
     *
     * @param username Username typed in TextField in LoginView.
     * @param password Password typed in PasswordField in LoginView.
     * @return Returns true if login was successful. False if not successful.
     */
    boolean validateLogin(String username, String password);

    /**
     * Creates account in the server database with given information.
     *
     * @param username Verified unique username from TextField in RegisterView.
     * @param password Verified password from PasswordField in RegisterView.
     * @param email    Verified unique email from TextField in RegisterView.
     * @param avatarUrl
     */
    void register(String username, String password, String email, String avatarUrl);

    /**
     * Notifies the ModelManager login was verified.
     *
     * @param user
     */
    void loginValidated(User user);

    /**
     * Sends current character choice to the server.
     */
    void sendPickedCharacterToTheServer();

    /**
     * Sets picked character for current User.
     * @param chosenCharacter String passed from CharacterListView based on which character user has chosen.
     */
    void pickCharacter(String chosenCharacter);

    void gameReady();

    void addPlayer(Player player);

    void setCoordinates(int playerId, double[] coordinates);

    double[] getCoordinates(int playerId);

    User getCurrentUser();

    void setId(int id);

    void sendCoordinates(double[] coordinates, int[] velocity);

    void pickGameRoomSize(int i);

    int getRoomSize();

    void newBullet(int id, double[] coordinates, int[] velocity);

    PlayerList getConnectedPlayers();

    Player getCurrentPlayer();

    ArrayList<Object[]> getPersonalStatistics();

    ArrayList<Object[]> getGameStatistics();

    ArrayList<String> getOverallStatistics();

    void killedPlayer(double killedID, double winnerID);

    String getWinner();

    void closeApplication();

    void userConnected(String username);

    void userDisconnected(String username);

    void playAgain();

    void playAgainWithTheSamePlayer(boolean doYouWantToPlay);

    void updateStatistics(int id, boolean winner, int bulletsShotInTheGame, int distance);

    ArrayList<String> loadOnlineUsers();

    void gameAbortedGoToMainMenu();

    void sendMessageOnChat(String message);

    void newMessageOnTheChat(String message);

    void setAvatar(String url);

    void logOff();

    boolean updateUsername(String updatedUsername);

    boolean updatePassword(String updatedPassword);

    boolean updateEmail(String updatedEmail);

    boolean updateAvatar(String updatedAvatarUrl);
}
