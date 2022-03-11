package Model.Domain;

public class Player {
    private int id;
    private String username;
    private Character character;
    private double x;
    private double y;
    private double[] bulletStartingPosition;
    private int[] velocity;
    //necessery in order player doesn't move we need to know his previous velocity
    private double[][] previousVelocityAndBulletStartingPosition;

    public Player(String username) {
        this.username = username;
        this.velocity = new int[2];
        bulletStartingPosition = new double[2];
        previousVelocityAndBulletStartingPosition = new double[2][2];
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double[] getCoordinates() {
        return new double[]{x, y};
    }

    public void setCoordinates(double[] coordinates) {
        x = coordinates[0];
        y = coordinates[1];
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
        setVelocity(new int[]{0, (int) getBulletSpeed()});
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int[] getVelocity() {
        return velocity;
    }

    public void setVelocity(int[] velocity) {
        this.velocity = velocity;
    }

    public double getBulletSpeed() {
        return character.getBullet().getSpeed();
    }

    public double[] getBulletStartingPosition() {
        return bulletStartingPosition;
    }

    public void setBulletStartingPosition(double[] bulletStartingPosition) {
        this.bulletStartingPosition = bulletStartingPosition;
    }

	public double[][] getPreviousVelocityAndBulletStartingPosition() {
		return previousVelocityAndBulletStartingPosition;
	}

	public void setPreviousVelocityAndBulletStartingPosition(double[][] previousVelocityAndBulletStartingPosition) {
		this.previousVelocityAndBulletStartingPosition = previousVelocityAndBulletStartingPosition;
	}
}
