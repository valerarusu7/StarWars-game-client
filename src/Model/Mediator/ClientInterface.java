package Model.Mediator;

import java.util.ArrayList;

public interface ClientInterface {
    /**
     * Validates login in the server database with given arguments as username and password.
     *
     * @param username Username typed in TextField in LoginView.
     * @param password Password typed in PasswordField in LoginView.
     * @return Returns true if login was successful. False if not successful.
     */
    boolean login(String username, String password);

    /**
     * Creates account in the server database with given information.
     *
     * @param username Verified unique username from TextField in RegisterView.
     * @param password Verified password from PasswordField in RegisterView.
     * @param email    Verified unique email from TextField in RegisterView.
     */
    void register(String username, String password, String email, String avatarUrl);

    /**
     * Sends information about picked character for given player to the server.
     *
     * @param id              ID of current player.
     * @param characterPicked Current player's picked character.
     */
    void pickCharacter(int id, String characterPicked);

    void sendInGamePackage(int id, double[] coordinates, int[] velocity);

    void pickGameRoomSize(int i);

    ArrayList<Object[]> getPersonalStatistics(int id);

    ArrayList<Object[]> getGameStatistics(int id);

    ArrayList<String> getOverallStatistics(int id);

    void disconnect(int id);

    ArrayList<String> getOnlineUsers();

	void playAgainWithTheSamePlayer(int id, boolean doYouWantToPlay);

	void sendChatMessage(String message);

	void exitGame(int id);

    boolean updateUsername(int id, String updatedUsername);

    boolean updatePassword(int id, String updatedPassword);

    boolean updateEmail(int id, String updatedEmail);

    boolean updateAvatar(int id, String updatedAvatarUrl);

}
