package Testing;

import Model.Domain.Player;
import Model.Domain.User;
import Model.Mediator.ModelManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModelManagerTest {

    Connection connection;
    Statement statement;
    private ModelManager model;
    private String wrongUsername;
    private String wrongPassword;
    private String databaseURL;
    private String user;
    private String password;



    // Make sure to start server before testing
    @Before
    public void setup() {
        //REMEMBER TO TURN ON THE SERVER BEFORE TESTING OTHERWISE EXPECT: java.rmi.ConnectException, java.net.ConnectException
        model = new ModelManager();
        wrongUsername = "notValidUsername";
        wrongPassword = "notValidPassword";

        // Change for your data from GameDatabase
        try {
            this.databaseURL = "jdbc:postgresql://localhost:5432/semesterproject";
            this.user = "postgres";
            this.password = "123456";
            this.connection = DriverManager.getConnection(databaseURL, user, password);
            this.statement = connection.createStatement();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String insertUserSQL = "INSERT INTO sep2_game.game_user (username, password, email, avatar_image_url) VALUES ('someNewTest', '123456', 'somenewtester@test.com', 'Client/View/res/avatars/stormtrooper.png');";
            statement.execute(insertUserSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void registerLegalAccount() {
        try {
            String deleteUserSQL = "DELETE FROM sep2_game.game_user WHERE username = 'someNewTest';";
            statement.execute(deleteUserSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            model.register("someNewTest", "123456", "somenewtester@test.com", "Client/View/res/avatars/stormtrooper.png");

            String selectUsernameSQL = "SELECT username FROM sep2_game.game_user WHERE username = 'someNewTest';";
            ResultSet resultSet = statement.executeQuery(selectUsernameSQL);
            resultSet.next();
            String username = resultSet.getString(1);

            assertEquals("someNewTest", username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithExistingEmail() {
        model.register("someNewTest1", "123456", "somenewtester@test.com", "Client/View/res/avatars/stormtrooper.png");
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithExistingUsername() {
        model.register("someNewTest", "123456", "david@test.com", "Client/View/res/avatars/stormtrooper.png");
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithNullUsername() {
        model.register(null, "123456", "david@test.com", "Client/View/res/avatars/stormtrooper.png");
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithNullEmail() {
        model.register("testing", "123456", null, "Client/View/res/avatars/stormtrooper.png");

    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithNullUsernameAndEmail() {
        model.register(null, "123456", null, "Client/View/res/avatars/stormtrooper.png");

    }

    @Test
    public void validateValidLogin() {
        boolean result;
        result = model.validateLogin("someNewTest", "123456");
        assertEquals(true, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateAllNullLogin() {
        model.validateLogin(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateNullPasswordLogin() {
        model.validateLogin("testing", null);
    }

    @Test
    public void validateWrongPasswordLogin() {
        boolean result;
        result = model.validateLogin("testing", wrongPassword);
        assertEquals(result, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateWrongPasswordNullUsernameLogin() {
        model.validateLogin(null, wrongPassword);
    }

    @Test
    public void validateWringUsernameWrongPasswordLogin() {
        boolean result;
        result = model.validateLogin(wrongUsername, wrongPassword);
        assertEquals(result, false);
    }

    @Test
    public void loginValidated() {
        User user = new User(99, "tester", "Client/View/res/avatars/stormtrooper.png");
        model.loginValidated(user);
        assertTrue(model.getCurrentUser() == user);
        assertEquals(user.getUsername(), model.getCurrentPlayer().getUsername());
        assertEquals(user.getID(), model.getCurrentPlayer().getId());
    }

    @Test
    public void addPlayer() {
        for (int i = 0; i < 10; i++) {
            Player player = new Player("player" + i);
            model.addPlayer(player);
        }

        assertEquals(10, model.getConnectedPlayers().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void register() {
        String newUsername = "userr";
        String newUsernameWithNumbers = "user12345";
        String newUsernameWithSpecialSigns = "user1234.,<.>";
        String newPassword = "passwordd";
        String newPasswordWithNumbers = "password12345";
        String newPasswordWithSpecialSigns = "/.,..,";
        String faultyEmail = "emaill";
        String unfinishedEmail = "something@somethin";
        String email = "email@email.emaill";
        //I MADE THOSE SPECIAL SIGNS BUT SINCE WE ACCEPT ANYTHING IT DOESN'T REALLY MAKE SENSE
        boolean result;
        model.register(null, null, faultyEmail, null);
        result = model.validateLogin(null, null);
        assertEquals(result, false);
        model.register(null, null, null, null);
        result = model.validateLogin(null, null);
        assertEquals(result, false);
        model.register(newUsernameWithSpecialSigns, null, null, null);
        result = model.validateLogin(newUsernameWithSpecialSigns, null);
        assertEquals(result, false);
        model.register(null, newPasswordWithSpecialSigns, null, null);
        result = model.validateLogin(null, newPasswordWithSpecialSigns);
        assertEquals(result, false);
        model.register(newPasswordWithSpecialSigns, null, email, null);
        result = model.validateLogin(newPasswordWithSpecialSigns, null);
        assertEquals(result, false);
        model.register(newUsernameWithSpecialSigns, newPasswordWithSpecialSigns, unfinishedEmail, null);
        result = model.validateLogin(newUsernameWithSpecialSigns, newPasswordWithSpecialSigns);
        assertEquals(result, false);
        model.register(newUsernameWithNumbers, newPasswordWithNumbers, email, null);
        result = model.validateLogin(newUsernameWithNumbers, newPasswordWithNumbers);
        assertTrue(result);
    }

    @After
    public void after() {
        try {
            String deleteUserSQL = "DELETE FROM sep2_game.game_user WHERE username = 'someNewTest';";
            statement.execute(deleteUserSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}