package Model.Mediator;

import Model.Domain.Character;
import Model.Domain.Packages.InGamePackage;
import Model.Domain.Player;
import Model.Domain.User;
import RMIinterfaces.RmiClient;
import RMIinterfaces.RmiServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RemoteClient implements RmiClient, ClientInterface {
    private final static String SERVER_IP = "localhost";
    private Socket socket;
    private Receiver receiver;
    private BufferedReader in;
    private PrintWriter out;
    private Model model;
    private RmiServer server;
    private int gameRoomMembers;
    private boolean inGame;

    public RemoteClient(Model model) {
        this.model = model;
        try {
            server = (RmiServer) Naming.lookup("rmi://" + SERVER_IP + ":1099/StarWarsGame");
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
        gameRoomMembers = 0;
        inGame = false;
    }

    /**
     * Validates login in the server database with given arguments as username and password.
     *
     * @param username Username typed in TextField in LoginView.
     * @param password Password typed in PasswordField in LoginView.
     * @return Returns result of validateLogin(username, password, RemoteClient)
     * @see RMIinterfaces.RmiServer#validateLogin(String, String, RmiClient)
     */
    @Override
    public boolean login(String username, String password) {
        try {
            return server.validateLogin(username, password, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Creates account in the server database with given information.
     *
     * @param username Verified unique username from TextField in RegisterView.
     * @param password Verified password from PasswordField in RegisterView.
     * @param email    Verified unique email from TextField in RegisterView.
     * @param avatarUrl
     * @see RMIinterfaces.RmiServer#register(String, String, String, String)
     */
    @Override
    public void register(String username, String password, String email, String avatarUrl) {
        try {
            server.register(username, password, email, avatarUrl);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls method in ModelManager.
     *
     * @param user
     * @see ModelManager#loginValidated(User)
     */
    @Override
    public void loginValidated(User user) {
        model.loginValidated(user);
    }

    /**
     * Sends ID and chosen character of current player to the server.
     *
     * @param id              ID of current player.
     * @param characterPicked Current player's picked character.
     */
    @Override
    public void pickCharacter(int id, String characterPicked) {
        try {
            server.pickCharacter(id, characterPicked);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Called when players are ready to play. After receiving information about all players (ID of the player, his username, character choice and starting location) informs ModelManager that the game is ready.
	 *
	 * @param id
	 * 		ID of the player who is ready to play
	 * @param username
	 * 		Username of the player who is ready to play.
	 * @param characterChoice
	 * 		Character choice of the player who is ready to play.
	 * @param location
	 * 		Starting location of the player who is ready to play.
	 */

	@Override
    public void prepareGame(int id, String username, Character characterChoice, int[] location) {
        Player player = new Player(username);
        player.setId(id);
        player.setCharacter(characterChoice);
        player.setCoordinates(new double[]{location[0], location[1]});
        player.setBulletStartingPosition(new double[]{location[0] + 20, location[1] + 75});
        model.addPlayer(player);
        gameRoomMembers++;
        if (gameRoomMembers == model.getRoomSize()) {
            model.gameReady();
            gameRoomMembers = 0;
        }
    }

	/**
	 * Sends package with information about action taken by the player during the game. If velocity is null it is a movement package, otherwise it is a package with information about the bullet.
	 * @param id
	 * 		ID of the player who has taken an action.
	 * @param coordinates
	 * 		Coordinates of the player who has taken an action.
	 * @param velocity
	 * 		Velocity of bullet that belongs to the player who has taken an action.
	 */

	@Override
    public void sendInGamePackage(int id, double[] coordinates, int[] velocity) {
        if (velocity == null) {
        } else {
        }
        ModelManager.executorService.execute(new PackageWriter(out, new InGamePackage(id, coordinates, velocity)));
    }

	/**
	 * Receives package with information about action taken by the player during the game. If velocity is null it is a movement package, otherwise it is a package with information about the bullet.
	 * @param parcel
	 * 		Package that contains all the information.
	 */

	public void receivedPackage(InGamePackage parcel) {
    	ModelManager.executorService.execute(new Runnable() {
			@Override
			public void run() {
				if (parcel.getVelocity() == null) {
					model.setCoordinates(parcel.getId(), parcel.getCoordinates());
				}
				else {
					model.newBullet(parcel.getId(), parcel.getCoordinates(), parcel.getVelocity());
				}
			}
    	});
    }

	/**
	 * Informs server that client is ready to establish TCP connection with the server. After successful connection informs server about desired size of the game room. Starts a thread responsible for receiving all
	 * information during the game.
	 *
	 * @param i
	 * 		Desired size of the game room.
	 */
	@Override
    public void pickGameRoomSize(int i) {
        try {
            ModelManager.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        server.startServerSocket(model.getCurrentPlayer().getId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            socket = new Socket(SERVER_IP, 6666);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            receiver = new Receiver(in, this);
            Thread receiverThread = new Thread(receiver);
            server.pickGameRoomSize(model.getCurrentPlayer().getId(), i);
            inGame = true;
            receiverThread.start();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Object[]> getPersonalStatistics(int id) {
        try {
            return server.getPersonalStatistics(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Object[]> getGameStatistics(int id) {
        try {
            return server.getGameStatistics(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getOverallStatistics(int id) {
        try {
            return server.getOverallStatistics(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * Information about player who was shot in the game and player who shot him.
	 *
	 * @param playerWhoIsHitID
	 * 		Player who was hit in the game.
	 * @param playerWhoShotID
	 * 		Player who shot other player.
	 */

	@Override
    public void playerDied(int playerWhoIsHitID, int playerWhoShotID) {
        model.killedPlayer(playerWhoIsHitID, playerWhoShotID);
    }

    @Override
    public void sendGameStatistics(int id, boolean winner, int bulletsShotInTheGame, int distance) {
        model.updateStatistics(id, winner, bulletsShotInTheGame, distance);
    }

	/**
	 * Returns boolean informing weather the player is still in the game or is game finished already.
	 *
	 * @return inGame
	 */

	public boolean isInGame() {
        return inGame;
    }

    @Override
    public void disconnectFromTheGame() {
        finishGame();
    }

	/**
	 * Closes TCP connection with the server.
	 */

	private void finishGame() {
        try {
        	if(socket != null && !socket.isClosed()) {
				inGame = false;
				socket.close();
				in.close();
				out.close();
				gameRoomMembers = 0;
			}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<String> getOnlineUsers() {
        try {
            return server.getOnlineUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * Informs about user who disconnected from the game;
	 *
	 * @param username
	 * 		User who disconnected from the game.
	 */

	@Override
    public void userDisconnected(String username) {
        model.userDisconnected(username);
    }

	/**
	 * Informs about user who just connected to the game.
	 *
	 * @param username
	 * 		Username of the suer who connected to the game.
	 */

	@Override
    public void userConnected(String username) {
        model.userConnected(username);
    }

	/**
	 * Disconnect from the game.
	 *
	 * @param id
	 * 		ID of the player who wants to disconnect from the game.
	 */

	@Override
    public void disconnect(int id) {
        try {
            server.disconnect(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Called when other player/players don't want to play again while you still want to play.
	 */

	@Override
	public void gameAborted() {
		model.gameAbortedGoToMainMenu();
	}

	/**
	 * Sends information to the server weather you want to play again with the same players.
	 *
	 * @param id
	 * 		ID of the player who is taking decision.
	 * @param doYouWantToPlay
	 * 		Boolean informing if you want to play again or not.
	 */
	@Override
	public void playAgainWithTheSamePlayer(int id, boolean doYouWantToPlay) {
		try {
			server.playAgain(id, doYouWantToPlay);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send message on the chat.
	 *
	 * @param message
	 * 		Message that you want to send on the chat.
	 */

	@Override
	public void sendChatMessage(String message) {
		try {
			server.newMessageOnChat(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Informs about new message on the chat.
	 *
	 * @param message
	 * 		New message on the chat.
	 */

	@Override
	public void newMessageOnChat(String message) {
		model.newMessageOnTheChat(message);
	}

	/**
	 * Closes all possible connections with the server.
	 *
	 * @param id
	 * 		ID of player who wants to disconnect from the server.
	 */

	@Override
	public void exitGame(int id) {
		finishGame();
		disconnect(id);
	}

    @Override
    public boolean updateUsername(int id, String updatedUsername) {
        try {
            return server.updateUsername(id, updatedUsername);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(int id, String updatedPassword) {
        try {
            return server.updatePassword(id, updatedPassword);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEmail(int id, String updatedEmail) {
        try {
            return server.updateEmail(id, updatedEmail);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAvatar(int id, String updatedAvatarUrl) {
        try {
            return server.updateAvatar(id, updatedAvatarUrl);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }
}
