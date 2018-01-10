package sample.keyBoard;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


import static sample.Main.NBNOTE;
import static sample.Main.NOTEHEIGHT;
import static sample.Main.NOTEWIDTH;


public class ControllerKeyBoard {


    @FXML
    private Canvas canvas;

    @FXML
    private Pane root;

    @FXML
    private void initialize(){
        loadKeyBoard();
    }

    @FXML
    public void loadKeyBoard(){

        root.getChildren().addAll(createKeyNotes());

        System.out.println();

    }

    public Canvas createKeyNotes() {


        GraphicsContext gc = canvas.getGraphicsContext2D();


        int TXTAJUTY = 12;
        int TXTAJUTX = NOTEWIDTH*3;
        int OFFTSET = 0;

        for (int i = 0; i <= NBNOTE; i++) {

            gc.setFill(Color.WHITE);
            gc.setStroke(Color.BLACK);
            gc.strokeRoundRect(0, ((NBNOTE - i) * NOTEHEIGHT)-OFFTSET, NOTEWIDTH * 4, NOTEHEIGHT,10,10);
            gc.fillRoundRect(0, ((NBNOTE - i) * NOTEHEIGHT)-OFFTSET, NOTEWIDTH * 4, NOTEHEIGHT,10,10);



            if( i % 7 != 2 && i % 7 != 6 ) {
                gc.setFill(Color.BLACK);
                gc.fillRoundRect(0, (((NBNOTE - i) * NOTEHEIGHT - NOTEHEIGHT / 2))-OFFTSET, NOTEWIDTH * 2, NOTEHEIGHT,10,10);

            }

            switch (i % 7) {
                case 0:
                    gc.setFill(Color.WHITE);
                    gc.strokeText("C", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);

                    break;

                case 1:
                    gc.setFill(Color.WHITE);
                    gc.strokeText("D", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;

                case 2:
                    gc.setFill(Color.WHITE);
                    gc.strokeText("E", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;
                case 3:
                    gc.setFill(Color.WHITE);
                    gc.strokeText("F", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;

                case 4:
                    gc.setFill(Color.WHITE);
                    gc.strokeText("G", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;

                case 5:
                    gc.setFill(Color.WHITE);
                    gc.strokeText("A", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;

                case 6:
                    gc.setFill(Color.WHITE);
                    gc.strokeText("B", TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET);
                    break;

            }

        }

        return canvas;

    }





}
