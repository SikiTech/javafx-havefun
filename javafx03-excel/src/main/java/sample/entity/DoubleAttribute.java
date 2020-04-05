package sample.entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DoubleAttribute extends RecursiveTreeObject<DoubleAttribute> {
    StringProperty first;
    StringProperty second;

    public DoubleAttribute(String first, String second) {
        this.first = new SimpleStringProperty(first);
        this.second = new SimpleStringProperty(second);
    }

    public String getFirst() {
        return first.get();
    }

    public StringProperty firstProperty() {
        return first;
    }

    public void setFirst(String first) {
        this.first.set(first);
    }

    public String getSecond() {
        return second.get();
    }

    public StringProperty secondProperty() {
        return second;
    }

    public void setSecond(String second) {
        this.second.set(second);
    }
}
