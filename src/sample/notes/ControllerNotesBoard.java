package sample.notes;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.chordBoard.ControllerChordBoard;
import sample.chordFinder.*;
import sample.model.AlgoNote;
import sample.model.EChord;
import sample.model.Piece;
import sample.model.Pulse;

import java.util.List;
import java.util.Map;

import static sample.Main.*;


public class ControllerNotesBoard {

    private Scene scene;

    private Piece oldPiece;
    private Piece newPiece;

    @FXML
    private Canvas canvas;

    private Canvas canvas2 = new Canvas(1920, 900);

    @FXML
    private Pane pane;


    @FXML
    public void loadAllComponants() {

        deleteCanvas();
        createHorizontalLine();
        createVerticalLine();
        loadNotes(oldPiece, Color.RED, 0.35, 1.20);
        loadNotes(newPiece, Color.GREEN, 1, 1);
        pane.getChildren().addAll(canvas, canvas2);


    }

    public void movingBar(int count) {

        canvas2.getGraphicsContext2D().clearRect(0, 0, 2000, 2000);
        GraphicsContext bar = canvas2.getGraphicsContext2D();
        bar.setFill(Color.RED);
        bar.setGlobalAlpha(1);
        bar.fillRoundRect(count * NOTEWIDTH, 3, 3, ((NBNOTE + 1) * NOTEHEIGHT), 10, 10);


    }

    public void initialize() {

        createHorizontalLine();
        createVerticalLine();

        pane.setOnMouseClicked(event -> {

            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.BLACK);
            gc.setGlobalAlpha(0.30);
            gc.fillRoundRect(0, ((int) (event.getY() / NOTEHEIGHT)) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);
            System.out.println(event.getY() - NOTEHEIGHT / 2);

        });


    }

    public void deleteCanvas() {

        pane.getChildren().clear();
        canvas.getGraphicsContext2D().clearRect(0, 0, 2000, 2000);

    }


    public void loadNotes(Piece piece, Color color, double opacity, double bigger) {

        GraphicsContext gc = canvas.getGraphicsContext2D();


        for (int i = 0; i < piece.notes.size(); i++) {

            if (piece.notes.get(i).getOn() && (piece.notes.get(i).getPulse16()) < NBPULSEVISIBLE) {

                if (piece.notes.get(i).getTracknumber() == 0)

                    gc.setFill(color);
                gc.setGlobalAlpha(opacity);
                gc.setStroke(Color.BLACK);

                gc.strokeRoundRect(piece.notes.get(i).getPulse16() * NOTEWIDTH, (NBNOTE - notePlace(piece.notes.get(i).getCNote())) * NOTEHEIGHT, piece.notes.get(i).getLenght16() * NOTEWIDTH, NOTEHEIGHT * bigger, 10, 10);

                Color color1 = selectTheColorTrack(piece.notes.get(i).getTracknumber());
                gc.setFill(color1);

                gc.fillRoundRect(piece.notes.get(i).getPulse16() * NOTEWIDTH, (NBNOTE - notePlace(piece.notes.get(i).getCNote())) * NOTEHEIGHT, piece.notes.get(i).getLenght16() * NOTEWIDTH, NOTEHEIGHT * bigger, 10, 10);

                //  gc.strokeText(Integer.toString(piece.notes.get(i).getPulse16()), piece.notes.get(i).getPulse16() * NOTEWIDTH, (NBNOTE - notePlace(piece.notes.get(i).getCNote())) * NOTEHEIGHT);


            }
        }
    }

    private Color selectTheColorTrack(int trackNumber) {

        switch (trackNumber) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.YELLOWGREEN;
            case 2:
                return Color.RED;
            case 3:
                return Color.GRAY;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.BLUE;
            case 6:
                return Color.YELLOWGREEN;
            case 7:
                return Color.RED;
            case 8:
                return Color.GRAY;
            case 9:
                return Color.GREEN;
            case 10:
                return Color.BLUE;
            case 11:
                return Color.YELLOWGREEN;
            case 12:
                return Color.RED;
            case 13:
                return Color.GRAY;
            case 14:
                return Color.GREEN;
            case 15:
                return Color.GREEN;


        }
        return Color.BLUE;
    }


    private int notePlace(int cnote) {

        int div = cnote / 12;

        switch (cnote % 12) {
            case 0:
                return div * 7 + 0;
            case 1:
                return div * 7 + 0;
            case 2:
                return div * 7 + 1;
            case 3:
                return div * 7 + 1;
            case 4:
                return div * 7 + 2;
            case 5:
                return div * 7 + 3;
            case 6:
                return div * 7 + 3;
            case 7:
                return div * 7 + 4;
            case 8:
                return div * 7 + 4;
            case 9:
                return div * 7 + 5;
            case 10:
                return div * 7 + 5;
            case 11:
                return div * 7 + 6;


        }

        return 13;

    }

    private void createVerticalLine() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i <= NBPULSEVISIBLE; i++) {

            if (i % 2 == 0) {

                gc.setFill(Color.BLUE);
                gc.setGlobalAlpha(0.20);
                gc.fillRoundRect(i * NOTEWIDTH, 0, 3, ((NBNOTE + 1) * NOTEHEIGHT), 10, 10);

            }

            if (i % 16 == 0) {

                gc.setFill(Color.BLUE);
                gc.setGlobalAlpha(0.20);
                gc.fillRoundRect(i * NOTEWIDTH, 0, 3, ((NBNOTE + 1) * NOTEHEIGHT), 10, 10);


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
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);

                    // gc.strokeText("C", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);

                    break;

                case 1:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);

                    //gc.strokeText("D", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 2:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);

                    // gc.strokeText("E", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;
                case 3:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);

                    // gc.strokeText("F", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 4:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);

                    // gc.strokeText("G", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 5:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);

                    // gc.strokeText("A", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

                case 6:
                    gc.setFill(Color.BLUE);
                    gc.setGlobalAlpha(0.20);
                    gc.fillRoundRect(0, (NBNOTE - i) * NOTEHEIGHT, NOTEWIDTH * NBPULSEVISIBLE, NOTEHEIGHT, 10, 10);

                    //gc.strokeText("B", TXTAJUTX, (NBNOTE - i) * NOTEHEIGHT + TXTAJUTY);
                    break;

            }


        }
    }

    public void noteAlgo(ControllerChordBoard controllerChordBoard) {


        for (Map.Entry<Integer, Pulse> entry : oldPiece.pulses.entrySet()) {

            if (entry.getKey() <= NBPULSEVISIBLE) {

                for (int j = 0; j < entry.getValue().getNotes().size(); j++) {

                    int noteOld = entry.getValue().getNotes().get(j).getCNote();
                    EChord echordOld = controllerChordBoard.getMapTilesOld().get(entry.getKey());
                    EChord echordNew = controllerChordBoard.getMapTilesNew().get(entry.getKey());

                    int noteNew = AlgoNote.changenote(echordOld, echordNew, noteOld);
                    newPiece.pulses.get(entry.getKey()).getNotes().get(j).setCNote(noteNew);
                }


            }
        }
        loadAllComponants();
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

            for (int j = 0; j < 12; j++) {

                if (array[i][j] == 1) {
                    gc.setStroke(Color.BLACK);
                    gc.strokeText(Integer.toString(array[i][j]), i * NOTEWIDTH, (12 - notePlace(j) + 3) * NOTEHEIGHT);
                }
            }
        }
    }

    public void setAloViewPulseList(List<PulseChord> listPulse) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < NBPULSEVISIBLE; i++) {

            if ((i % 2) == 0) {
                for (int j = 0; j < listPulse.get(i).getScores().size(); j++) {

                    gc.setStroke(Color.BLACK);
                    gc.strokeText(Double.toString(listPulse.get(i).getScores().get(j)), i * NOTEWIDTH, (50 - j) * NOTEHEIGHT);

                }
            }


        }
    }

    public void setChords(List<String> findChords) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < findChords.size(); i++) {


            gc.setStroke(Color.BLACK);
            gc.strokeText(findChords.get(i), i * NOTEWIDTH, 1 * NOTEHEIGHT);

        }


    }

    public void viewAllBeatChordsAllChordsAll32(AllBeatChordsAllChordsOne32 allBeatChordsAllChordsOne32) {

        GraphicsContext gc = canvas.getGraphicsContext2D();


        for (int j = 0; j < allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().size(); j++) {
            for (int k = 0; k < allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(j).getFullBeatChordAllChordsOne32Map().size(); k++) {

//                    gc.setFill(Color.WHITE);
//                    gc.fillRect(j * NOTEWIDTH,1* NOTEHEIGHT,NOTEWIDTH*4,NOTEHEIGHT);

                gc.setStroke(Color.BLACK);
                double temp = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(j).getFullBeatChordAllChordsOne32Map().get(k).getScore();
                gc.strokeText(Double.toString(temp), j * NOTEWIDTH * 10 + 10, k * NOTEHEIGHT + 12);

                gc.setStroke(Color.BLUE);
                int temp3 = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(j).getFullBeatChordAllChordsOne32Map().get(k).getBestIndexChord();
                gc.strokeText(Double.toString(temp3), j * NOTEWIDTH * 10 + 20, k * NOTEHEIGHT + 120);

                String temp4 = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(j).getFullBeatChordAllChordsOne32Map().get(k).getBestEChord().name();
                gc.strokeText(temp4, j * NOTEWIDTH * 10 + 50, k * NOTEHEIGHT + 120);

                String temp5 = Integer.toString(allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(j).getFullBeatChordAllChordsOne32Map().get(k).getNumberOfPulse());
                gc.strokeText(temp5, j * NOTEWIDTH * 10 + 70, k * NOTEHEIGHT + 120);

                gc.setStroke(Color.RED);
//                        double temp2 = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(j).getFullBeatChordAllChordsOne32Map().get(k).getBestIndexChord();
//                        gc.strokeText(Double.toString(temp2), j * NOTEWIDTH *10, j*k * NOTEHEIGHT + 24);

            }
            gc.setStroke(Color.RED);
            double temp4 = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(j).getScore();
            gc.strokeText(Double.toString(temp4), j * NOTEWIDTH * 10 + 10, NOTEHEIGHT + 250);

//        double index = fullBeatChordAllChordsOne32.getBestIndexChord();
//        gc.strokeText(Double.toString(index), 5 * NOTEWIDTH, (52)* NOTEHEIGHT+12);

        }
        gc.setStroke(Color.BLACK);
        double score = allBeatChordsAllChordsOne32.getScore();
        gc.strokeText("best score    " + Double.toString(score), 1 * NOTEWIDTH, (51) * NOTEHEIGHT + 12);

        int indexBeat = allBeatChordsAllChordsOne32.getBestIndexChord();
        gc.strokeText("best BestIndexChord     " + Double.toString(indexBeat), 1 * NOTEWIDTH, (51) * NOTEHEIGHT + 24);


        for (int i = 0; i < allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(indexBeat).getFullBeatChordAllChordsOne32Map().size(); i++) {

            String temp7 = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(indexBeat).getFullBeatChordAllChordsOne32Map().get(i).getBestEChord().name();
            gc.strokeText("Echord  " + temp7, i * NOTEWIDTH * 10, (51) * NOTEHEIGHT + 40);

            String temp8 = Integer.toString(allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(indexBeat).getFullBeatChordAllChordsOne32Map().get(i).getNumberOfPulse());
            gc.strokeText("number pulse  " + temp8, i * NOTEWIDTH * 10, (51) * NOTEHEIGHT + 50);
        }

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}



