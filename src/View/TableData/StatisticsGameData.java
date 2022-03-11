package View.TableData;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class StatisticsGameData implements Serializable {
    private StringProperty bulletsShotInTheGameProperty;
    private StringProperty distanceProperty;

    public StatisticsGameData(String bulletsShotInTheGame, String distance) {
        this.bulletsShotInTheGameProperty = new SimpleStringProperty(bulletsShotInTheGame);
        this.distanceProperty = new SimpleStringProperty(distance);
    }

    public StringProperty getGameBulletsProperty() {
        return bulletsShotInTheGameProperty;
    }

    public StringProperty getDistanceProperty() {
        return distanceProperty;
    }
}
