package sample.keyBoard;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.model.EChord;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sample.Main.NBNOTE;
import static sample.Main.NOTEHEIGHT;
import static sample.Main.NOTEWIDTH;


public class ControllerKeyBoard {

    public List<Boolean> onOffBoardNotes  = new ArrayList<>();


    @FXML
    private Canvas canvas;

    @FXML
    private Pane root;

    @FXML
    private void initialize(){

        initialiseDataNoteOff();
        loadKeyBoard();
    }

    private void initialiseDataNoteOff(){

        onOffBoardNotes  = new ArrayList<>();

        //first time each note is off.
        for (int i = 0; i < NBNOTE ; i++) {
            onOffBoardNotes.add(false);
        }
    }

    @FXML
    public void loadKeyBoard(){

        root.getChildren().addAll(createKeyNotes());

    }

    public Canvas createKeyNotes() {


        GraphicsContext gc = canvas.getGraphicsContext2D();


        int TXTAJUTY = 12;
        int TXTAJUTX = NOTEWIDTH*3;
        int OFFTSET = 0;

        for (int i = 0; i < NBNOTE; i++) {

            //Draw all the With Note.

        //Draw with somme Gray if the note is selected.
            if(onOffBoardNotes.get(i) == true){
                gc.setFill(Color.GRAY);
            }else {
                gc.setFill(Color.WHITE);
            }

            gc.setStroke(Color.BLACK);
            gc.strokeRoundRect(0, ((NBNOTE - i) * NOTEHEIGHT)-OFFTSET, NOTEWIDTH * 4, NOTEHEIGHT,10,10);
            gc.fillRoundRect(0, ((NBNOTE - i) * NOTEHEIGHT)-OFFTSET, NOTEWIDTH * 4, NOTEHEIGHT,10,10);


            //Draw text ("C") on the note.
            switch (i % 7) {
                case 0: gc.strokeText("C", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
                case 1: gc.strokeText("D", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
                case 2: gc.strokeText("E", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
                case 3:gc.strokeText("F", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
                case 4: gc.strokeText("G", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
                case 5: gc.strokeText("A", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
                case 6: gc.strokeText("B", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
            }


            //Draw black note.
            if( i % 7 != 2 && i % 7 != 6 ) {
                gc.setFill(Color.BLACK);
                gc.fillRoundRect(0, (((NBNOTE - i) * NOTEHEIGHT - NOTEHEIGHT / 2))-OFFTSET, NOTEWIDTH * 2, NOTEHEIGHT,10,10);
            }
        }
        return canvas;

    }





}
