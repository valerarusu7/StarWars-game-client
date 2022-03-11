package View.TableData;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class PersonalTableData implements Serializable {
    private StringProperty gameProperty;
    private StringProperty characterProperty;
    private StringProperty bulletsFiredProperty;
    private StringProperty givenDamageProperty;
    private StringProperty receivedDamageProperty;
    private StringProperty distanceProperty;

    public PersonalTableData(String gameId, String character, String bulletsFired, String givenDamage, String receivedDamage, String distanceTravelled) {
        gameProperty = new SimpleStringProperty(gameId);
        characterProperty = new SimpleStringProperty(character);
        bulletsFiredProperty = new SimpleStringProperty(bulletsFired);
        givenDamageProperty = new SimpleStringProperty(givenDamage);
        receivedDamageProperty = new SimpleStringProperty(receivedDamage);
        distanceProperty = new SimpleStringProperty(distanceTravelled);
    }

    public StringProperty getGameProperty() {
        return gameProperty;
    }


    public StringProperty getCharacterProperty() {
        return characterProperty;
    }

    public StringProperty getBulletsFiredProperty() {
        return bulletsFiredProperty;
    }

    public StringProperty getGivenDamageProperty() {
        return givenDamageProperty;
    }

    public StringProperty getReceivedDamageProperty() {
        return receivedDamageProperty;
    }

    public StringProperty getDistanceProperty() {
        return distanceProperty;
    }
}
