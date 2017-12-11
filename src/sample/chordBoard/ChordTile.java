package sample.chordBoard;

import javafx.scene.shape.Rectangle;
import sample.model.EChord;

public class ChordTile extends Rectangle{

    private EChord eChord;

    public ChordTile(double x, double y, double width, double height){
          super(x,y,width,height);

    }

    public EChord getEChord() {
        return eChord;
    }

    public void setEChord(EChord eChord) {
        this.eChord = eChord;
    }
}
