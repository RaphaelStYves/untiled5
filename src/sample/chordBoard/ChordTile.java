package sample.chordBoard;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.model.EChord;

public class ChordTile extends Rectangle{

    private EChord eChord;
    private int index;
    private ObjectProperty<Color> color ;
    private ObjectProperty<EChord> echord;

    public ChordTile(double x, double y, double width, double height) {
        super( x,  y,  width,  height);
    }

    public EChord getEChord() {
        return eChord;
    }

    public void setEChord(EChord eChord) {
        this.eChord = eChord;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }




    public ObjectProperty<Color> colorObjectProperty() {
        return color;
    }

    public ObservableValue<EChord> eChordObjectProperty(){
        return echord;
    }


}
