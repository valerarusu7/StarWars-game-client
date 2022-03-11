package Testing;


import Model.Domain.User;
import Model.Mediator.Model;
import Model.Mediator.ModelManager;
import ViewModel.LoginViewModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

public class LoginViewModelTest {
    String databaseURL;
    String user;
    String password;
    Connection connection;
    Statement statement;
    private Model model;
    private LoginViewModel loginViewModel;


    // First run ModelManagerTest

    @Before
    public void setup() {
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

        model = new ModelManager();
        loginViewModel = new LoginViewModel(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateAllNullLogin() {
        boolean result;
        result = model.validateLogin(null, null);
        Assert.assertEquals(result, false);
    }

    @Test
    public void validateValidLogin() {
        boolean result;
        result = model.validateLogin("someNewTest", "123456");
        Assert.assertEquals(true, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateNullPasswordLogin() {
        model.validateLogin("someNewTest", null);
    }

    @Test
    public void validateWrongPasswordLogin() {
        boolean result;
        result = model.validateLogin("someNewTest", "166548");
        Assert.assertEquals(result, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateWrongPasswordNullUsernameLogin() {
        boolean result;
        result = model.validateLogin(null, "166548");
        Assert.assertEquals(result, false);
    }

    @Test
    public void validateWringUsernameWrongPasswordLogin() {
        boolean result;
        result = model.validateLogin("someNewTest", "166548");
        Assert.assertEquals(result, false);
    }

    @Test
    public void loginValidated() {
        User user = new User(99, "someNewTest", "Client/View/res/avatars/stormtrooper.png");
        model.loginValidated(user);
        assertTrue(model.getCurrentUser() == user);
        Assert.assertEquals(user.getUsername(), model.getCurrentPlayer().getUsername());
        Assert.assertEquals(user.getID(), model.getCurrentPlayer().getId());
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