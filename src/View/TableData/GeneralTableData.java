package View.TableData;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GeneralTableData {
    private StringProperty gameProperty;
    private StringProperty winnerProperty;
    private StringProperty totalBulletsFiredProperty;
    private StringProperty totalGivenDamageProperty;
    private StringProperty totalDistanceProperty;

    public GeneralTableData(String game, String winner, String totalBullets, String totalDamage, String totalDistance){
        this.gameProperty = new SimpleStringProperty(game);
        this.winnerProperty = new SimpleStringProperty(winner);
        this.totalBulletsFiredProperty = new SimpleStringProperty(totalBullets);
        this.totalGivenDamageProperty = new SimpleStringProperty(totalDamage);
        this.totalDistanceProperty = new SimpleStringProperty(totalDistance);
    }

    public StringProperty getGameProperty() {
        return gameProperty;
    }

    public StringProperty getWinnerProperty() {
        return winnerProperty;
    }

    public StringProperty getTotalBulletsFiredProperty() {
        return totalBulletsFiredProperty;
    }

    public StringProperty getTotalGivenDamageProperty() {
        return totalGivenDamageProperty;
    }

    public StringProperty getTotalDistanceProperty() {
        return totalDistanceProperty;
    }
}
