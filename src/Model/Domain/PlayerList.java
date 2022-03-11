package Model.Domain;

import java.util.ArrayList;

public class PlayerList {
    private ArrayList<Player> connectedPlayers;

    public PlayerList() {
        connectedPlayers = new ArrayList<>();
    }

    public void add(Player player) {
        connectedPlayers.add(player);
    }

    public int size() {
        return connectedPlayers.size();
    }

    public Player get(int index) {
        return connectedPlayers.get(index);
    }

    public void setCoordinates(int id, double[] coordinates) {
        for (Player player : connectedPlayers) {
            if (player.getId() == id && coordinates != null) {
                calculateDirection(player, coordinates);
                player.setX(player.getX() + coordinates[0]);
                player.setY(player.getY() + coordinates[1]);
            }
        }
    }

    public double[] getCoordinates(int id) {
        double[] coordinates = new double[2];

        for (Player player : connectedPlayers) {
            if (player.getId() == id) {
                coordinates[0] = player.getX();
                coordinates[1] = player.getY();
            }
        }
        return coordinates;
    }

    public String getType(int id) {
        for (Player player : connectedPlayers) {
            if (player.getId() == id) {
                return player.getCharacter().getCharacterName();
            }
        }
        return "error";
    }

    public Bullet getBulletForPlayer(int id){
        for (Player player : connectedPlayers) {
            if (player.getId() == id) {
                return player.getCharacter().getBullet();
            }
        }
        return new Bullet(1, 10, "FF0000");
    }

    public Player getPlayerWithID(int id) {
    	for(Player player : connectedPlayers) {
    		if(player.getId() == id) {
    			return player;
			}
		}
		return null;
	}

    private void calculateDirection(Player player, double[] coordinates) {
        double changeX = coordinates[0];
        double changeY = coordinates[1];
        int velocityOfBullet = (int) player.getBulletSpeed();

        if (changeX > 0 && changeY > 0) {
            player.setVelocity(new int[]{velocityOfBullet, velocityOfBullet});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] + 55, player.getCoordinates()[1] + 75});
            player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{player.getBulletSpeed(), player.getBulletSpeed()}, {player.getCoordinates()[0] + 55, player.getCoordinates()[1] + 75}});
        } else if (changeX == 0 && changeY > 0) {
            player.setVelocity(new int[]{0, velocityOfBullet});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] + 20, player.getCoordinates()[1] + 75});
			player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{0, player.getBulletSpeed()}, {player.getCoordinates()[0] + 20, player.getCoordinates()[1] + 75}});
        } else if (changeX == 0 && changeY < 0) {
            player.setVelocity(new int[]{0, -velocityOfBullet});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] + 20, player.getCoordinates()[1] - 15});
			player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{0, -player.getBulletSpeed()}, {player.getCoordinates()[0] + 20, player.getCoordinates()[1] - 15}});
        } else if (changeX > 0 && changeY < 0) {
            player.setVelocity(new int[]{velocityOfBullet, -velocityOfBullet});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] + 55, player.getCoordinates()[1] - 15});
			player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{player.getBulletSpeed(), -player.getBulletSpeed()}, {player.getCoordinates()[0] + 55, player.getCoordinates()[1] - 15}});
        } else if (changeX > 0 && changeY == 0) {
            player.setVelocity(new int[]{velocityOfBullet, 0});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] + 55, player.getCoordinates()[1] + 30});
			player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{player.getBulletSpeed(), 0}, {player.getCoordinates()[0] + 55, player.getCoordinates()[1] + 30}});
        } else if (changeX < 0 && changeY > 0) {
            player.setVelocity(new int[]{-velocityOfBullet, velocityOfBullet});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] - 15, player.getCoordinates()[1] + 75});
			player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{-player.getBulletSpeed(), player.getBulletSpeed()}, {player.getCoordinates()[0] - 15, player.getCoordinates()[1] + 75}});
        } else if (changeX < 0 && changeY < 0) {
            player.setVelocity(new int[]{-velocityOfBullet, -velocityOfBullet});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] - 15, player.getCoordinates()[1] - 15});
			player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{-player.getBulletSpeed(), -player.getBulletSpeed()}, {player.getCoordinates()[0] - 15, player.getCoordinates()[1] - 15}});
        } else if (changeX < 0 && changeY == 0) {
            player.setVelocity(new int[]{-velocityOfBullet, 0});
            player.setBulletStartingPosition(new double[]{player.getCoordinates()[0] - 15, player.getCoordinates()[1] + 30});
			player.setPreviousVelocityAndBulletStartingPosition(new double[][] {{-player.getBulletSpeed(), 0}, {player.getCoordinates()[0] - 15, player.getCoordinates()[1] + 30}});
        } else if (changeX == 0 && changeY == 0){
			player.setVelocity(new int[]{(int)player.getPreviousVelocityAndBulletStartingPosition()[0][0], (int)player.getPreviousVelocityAndBulletStartingPosition()[0][1]});
			player.setBulletStartingPosition(player.getPreviousVelocityAndBulletStartingPosition()[1]);
        } else {
            player.setVelocity(new int[]{0, velocityOfBullet});
        }
    }

    public void clear(){
        connectedPlayers.clear();
    }
}
