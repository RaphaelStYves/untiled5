package sample.notes;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.chordFinder.Beat;
import sample.chordFinder.PulseChord;
import sample.model.EChord;
import sample.model.Piece;
import sample.model.Pulse;

import java.util.List;
import java.util.Map;

import static sample.Main.*;


public class ControllerNotesBoard {

    private Piece oldPiece;
    private Piece newPiece;

    Group group = new Group();

    @FXML
    private Canvas canvas;

    @FXML
    private Pane pane;
    private int[][] array;

    @FXML
    public void loadAllComponants(){

        loadNotes(oldPiece,Color.RED, 0.35,1.35);
        loadNotes(newPiece,Color.GREEN, 1,1);
        pane.getChildren().add(canvas);


    }

    public void initialize(){




        group.setOnKeyPressed(event ->  {
            switch (event.getCode()) {
                case J: {
                    showAlgo();

                }
                break;
            }
        });

        createHorizontalLine();
        createVerticalLine();


    }

    public void deleteAllComponants(){

        group.getChildren().clear();

    }


    public void loadNotes(Piece piece, Color color, double opacity,double bigger){

        GraphicsContext gc = canvas.getGraphicsContext2D();


            for (int i = 0; i < piece.notes.size(); i++) {

                if (piece.notes.get(i).getOn() && (piece.notes.get(i).getPulse16()) < NBPULSEVISIBLE) {

                    gc.setFill(color);
                    gc.setGlobalAlpha(opacity);
                    gc.setStroke(Color.BLACK);
                    gc.strokeRoundRect(piece.notes.get(i).getPulse16() * NOTEWIDTH, (NBNOTE - notePlace(piece.notes.get(i).getCNote())) * NOTEHEIGHT, piece.notes.get(i).getLenght16() * NOTEWIDTH, NOTEHEIGHT * bigger, 10, 10);
                    gc.fillRoundRect(piece.notes.get(i).getPulse16() * NOTEWIDTH, (NBNOTE - notePlace(piece.notes.get(i).getCNote())) * NOTEHEIGHT, piece.notes.get(i).getLenght16() * NOTEWIDTH, NOTEHEIGHT * bigger, 10, 10);

                    gc.strokeText(Integer.toString(piece.notes.get(i).getPulse16()), piece.notes.get(i).getPulse16() * NOTEWIDTH, (NBNOTE - notePlace(piece.notes.get(i).getCNote())) * NOTEHEIGHT);


                }
            }
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

    private void createVerticalLine() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i <= NBPULSEVISIBLE; i++) {

            if (i%2 ==0){

                gc.setFill(Color.BLUE);
                gc.setGlobalAlpha(0.20);
                 gc.fillRoundRect(i* NOTEWIDTH, 0, 3, ((NBNOTE+1)  * NOTEHEIGHT),10,10);

            }

            if (i%16 ==0){

                gc.setFill(Color.BLUE);
                gc.setGlobalAlpha(0.20);
                gc.fillRoundRect(i* NOTEWIDTH, 0, 3, ((NBNOTE+1)  * NOTEHEIGHT),10,10);



            }

        }

    }

    public void createHorizontalLine() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

            for (int i = 0; i <= NBNOTE; i++) {

            switch (i % 7) {
                case 0:

                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT,10,10);

                   // gc.strokeText("C", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);

                    break;

                case 1:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT,10,10);

                    //gc.strokeText("D", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 2:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT,10,10);

                   // gc.strokeText("E", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;
                case 3:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT,10,10);

                   // gc.strokeText("F", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 4:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT,10,10);

                   // gc.strokeText("G", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 5:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT,10,10);

                   // gc.strokeText("A", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 6:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT,10,10);

                    //gc.strokeText("B", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

            }


        }
    }

    public void showAlgo() {

//        Group group = new Group();
//
//        ChordFinder chordFinder = new ChordFinder(oldPiece);
//        chordFinder.loadPieceInOnesInArray();
//        chordFinder.findScoreOfAllPulseAndAllChords();
//        List<Pulse> pulses = chordFinder.scoreOfEachPulseOfAllChords;
//        Text text;
//        for (int i = 0; i < pulses.size(); i++) {
//            for (int j = 0; j < pulses.get(i).getScores().size(); j++) {
//                text = new Text(Double.toString(pulses.get(i).getScores().get(j)));
//                text.setX(i*NOTEWIDTH);
//                text.setY(i*NOTEHEIGHT);
//                text.setCache(true);
//                group.getChildren().add(text);
//            }
//        }
//
//        root.getChildren().add(group);


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

    public void setAloView(int[][] array) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < NBPULSEVISIBLE; i++) {

            for (int j = 0; j < 12 ; j++) {

                if(array[i][j] == 1){
                    gc.setStroke(Color.BLACK);
                    gc.strokeText(Integer.toString(array[i][j]), i * NOTEWIDTH, (12-notePlace(j)+3)* NOTEHEIGHT);
                }
            }
        }
    }


    public void setAloViewPulseList(List<PulseChord> listPulse) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < NBPULSEVISIBLE; i++) {

            if((i % 2) ==0){
                for (int j = 0; j < listPulse.get(i).getScores().size() ; j++) {

                    gc.setStroke(Color.BLACK);
                    gc.strokeText(Double.toString(listPulse.get(i).getScores().get(j)), i * NOTEWIDTH, (50-j)* NOTEHEIGHT);

                }
            }


        }
    }


    public void setChords(List<String> findChords) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < findChords.size(); i++) {


                    gc.setStroke(Color.BLACK);
                    gc.strokeText(findChords.get(i), i * NOTEWIDTH, 1* NOTEHEIGHT);

                }



        }

    public void setbeat(Beat beat) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < NBPULSEVISIBLE; i++) {

            if((i % 2) ==0){
                for (int j = 0; j < beat.get(i).getScores().size() ; j++) {

                    gc.setStroke(Color.BLACK);
                    gc.strokeText(Double.toString(listPulse.get(i).getScores().get(j)), i * NOTEWIDTH, (50-j)* NOTEHEIGHT);

                }
            }

        }

    }
}
