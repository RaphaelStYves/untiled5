package sample.notes;

import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.chordFinder.ChordFinder;
import sample.chordFinder.Pulse;
import sample.model.Piece;

import java.util.List;

public class ControllerNotesBoard {

    private Piece oldPiece;
    private Piece newPiece;

    AnchorPane root = new AnchorPane();

    //Dimension
    public static final int NOTEWIDTH = 15;
    public static final int NOTEHEIGHT = 15;
    public static final int BEATHEIGHT = 15;

    public static final int NBNOTE = 70;

    @FXML
    private ScrollPane mainScrollPane;

    public ScrollPane getMainScrollPane() {
        return mainScrollPane;
    }

    @FXML
    public void loadAllComponants(){

        root.getChildren().addAll(createVerticalLine(),createHorizontalLine(),loadNotes(oldPiece,Color.RED, 0.35,1.20),loadNotes(newPiece,Color.GREEN, 1,1));
        mainScrollPane.setContent(root);


    }

    public void initialize(){
        mainScrollPane.setOnKeyPressed(event ->  {
            switch (event.getCode()) {
                case J: {
                    showAlgo();

                }
                break;
            }
        });
    }

    public void deleteAllComponants(){

        root.getChildren().clear();

    }


    public Group loadNotes(Piece piece, Color color, double opacity,double bigger){

        Group group = new Group();
        for (int i = 0; i < piece.notes.size(); i++) {
            piece.notes.get(i).getCNote();
            if (piece.notes.get(i).getOn()) {
                Rectangle rectangle = new Rectangle(piece.notes.get(i).getPulse16()*NOTEWIDTH, (NBNOTE- notePlace(piece.notes.get(i).getCNote()))*NOTEHEIGHT, piece.notes.get(i).getLenght16()*NOTEWIDTH, NOTEHEIGHT*bigger);

                rectangle.setFill(color);
                rectangle.setOpacity(opacity);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(8);
                rectangle.setArcHeight(8);
                rectangle.setCache(true); //upgrade performance

//
//                Text text = new Text();
//                text.setText(Integer.toString(piece.notes.get(i).getPulse16()));
//                text.setX(piece.notes.get(i).getPulse16()*NOTEWIDTH);
//                text.setY((NBNOTE- notePlace(piece.notes.get(i).getCNote()))*NOTEHEIGHT);

                group.getChildren().addAll(rectangle);

            }
        }

        return group;
    }

    private int notePlace (int cnote){

        int div = cnote/12;

        switch (cnote%12) {
            case 0:  return div*7 + 0;
            case 1:  return div*7 + 0;
            case 2:  return div*7 + 1;
            case 3:  return div*7 + 1;
            case 4: return div*7 + 2;
            case 5: return div*7 + 3;
            case 6: return div*7 + 3;
            case 7: return div*7 + 4;
            case 8: return div*7 + 4;
            case 9: return div*7 + 5;
            case 10: return div*7 + 5;
            case 11: return div*7 + 6;


        }

        return 13;

    }

    private Group createVerticalLine() {
        Group group = new Group();

        for (int i = 0; i < oldPiece.getPieceLenght16(); i++) {

            if (i%1 ==0){

                Rectangle line1 = new Rectangle(i* NOTEWIDTH, 0, 3, (NBNOTE  * NOTEHEIGHT));
                line1.setFill(Color.BLUE);
                line1.setOpacity(.20);
                line1.setCache(true); //upgrade performance


                group.getChildren().add(line1);

            }

            if (i%16 ==0){

                Rectangle line16 = new Rectangle(i* NOTEWIDTH, 0, 3, (NBNOTE  * NOTEHEIGHT));
                line16.setOpacity(.20);
                line16.setCache(true); //upgrade performance
                line16.setCacheHint(CacheHint.SPEED);//upgrade performance

                group.getChildren().add(line16);

            }

        }
        return group;
    }

    public Group createHorizontalLine() {

        Group group = new Group();

        int TXTAJUTY = 15;
        int TXTAJUTX = 10;

            for (int i = 0; i <= NBNOTE; i++) {

            Rectangle rect1 = new Rectangle(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * oldPiece.getPieceLenght16(), NOTEHEIGHT);
            rect1.setCache(true); //upgrade performance

            Text text = new Text(TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY, null);
            text.setCache(true); //upgrade performance



            switch (i % 7) {
                case 0:
                    rect1.setFill(Color.BLUE);
                    text.setText("C");
                    break;

                case 1:
                    rect1.setFill(Color.GRAY);
                    text.setText("D");
                    break;

                case 2:
                    rect1.setFill(Color.GRAY);
                    text.setText("E");
                    break;
                case 3:
                    rect1.setFill(Color.GRAY);
                    text.setText("F");
                    break;

                case 4:
                    rect1.setFill(Color.GRAY);
                    text.setText("G");
                    break;

                case 5:
                    rect1.setFill(Color.GRAY);
                    text.setText("A");
                    break;

                case 6:
                    rect1.setFill(Color.GRAY);
                    text.setText("B");
                    break;

            }
            rect1.setOpacity(.20);
            text.setFont(new Font("Helvetica", 9));

            group.getChildren().addAll(rect1, text);

        }

        return group;

    }

    public void showAlgo() {

        Group group = new Group();

        ChordFinder chordFinder = new ChordFinder(oldPiece);
        chordFinder.loadPieceInOnesInArray();
        chordFinder.findScoreOfAllPulseAndAllChords();
        List<Pulse> pulses = chordFinder.scoreOfEachPulseOfAllChords;
        Text text;
        for (int i = 0; i < pulses.size(); i++) {
            for (int j = 0; j < pulses.get(i).getScores().size(); j++) {
                text = new Text(Double.toString(pulses.get(i).getScores().get(j)));
                text.setX(i*NOTEWIDTH);
                text.setY(i*NOTEHEIGHT);
                text.setCache(true);
                group.getChildren().add(text);
            }
        }

        root.getChildren().add(group);


    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param oldPiece
     */
    public void setPiece(Piece oldPiece, Piece newPiece) {
        this.oldPiece = oldPiece;
        this.newPiece = newPiece;
    }
}
