package sample.chordBoard;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.model.EChord;

public class ChordTile extends StackPane{

    private Rectangle rectangle;
    private Text tx;
    private Color color;


    public ChordTile(int x, int y, double width, double height, String text) {

        this.setTranslateX(x);
        this.setTranslateY(y);
        this.rectangle = new Rectangle(width,height);
        this.tx = new Text("N");

        rectangle.setStroke(Color.BLACK);

        getChildren().addAll(rectangle,tx);
    }

    public void setEchord(EChord echord) {
        rectangle.setFill(echordToColor(echord));
        tx.setText(echordToNumber(echord));
    }

     public String echordToNumber(EChord echord){

        String value;

        switch (echord) {
            case I: value = "I"; break;
            case i:value = "i"; break;
            case II: value = "II"; break;
            case ii:value = "ii"; break;
            case III: value = "III"; break;
            case iii: value = "iii"; break;
            case IV:value = "IV"; break;
            case iv: value = "iv";break;
            case V: value = "V"; break;
            case v: value = "v";break;
            case VI:value = "VI";break;
            case vi: value = "vi";break;
            case VII:value = "7"; break;
            case vii: value = "7."; break;
            default: value = "N"; break;
        }
        return value ;
    }

    public Color echordToColor(EChord echord){

        switch (echord) {
            case I: color = Color.CORNFLOWERBLUE;break;
            case i:color = Color.BLUEVIOLET; break;
            case II: color = Color.CORAL; break;
            case ii:color = Color.DARKSALMON;break;
            case III: color  = Color.HOTPINK; break;
            case iii: color  = Color.VIOLET; break;
            case IV:color  = Color.YELLOWGREEN; break;
            case iv: color  = Color.PALEGREEN; break;
            case V: color = Color.FIREBRICK;break;
            case v: color = Color.PALEVIOLETRED;break;
            case VI:color = Color.AQUA;break;
            case vi: color = Color.AQUAMARINE;break;
            case VII: color = Color.TAN;break;
            case vii: color = Color.BEIGE;break;
            default: color = Color.BLUE; break;
        }
        return color;
    }


}
