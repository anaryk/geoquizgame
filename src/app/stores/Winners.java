package app.stores;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Winners {

    private SimpleStringProperty name;

    private SimpleIntegerProperty points;

    public Winners(String name, int points) {
        this.name = new SimpleStringProperty(name);
        this.points = new SimpleIntegerProperty(points);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public int getPoints() {
        return points.get();
    }

    public void setPoints(int points) {
        this.points = new SimpleIntegerProperty(points);
    }
}
