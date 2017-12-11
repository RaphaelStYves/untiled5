package sample.keyBoard;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static sample.notes.ControllerNotesBoard.NBNOTE;
import static sample.notes.ControllerNotesBoard.NOTEHEIGHT;
import static sample.notes.ControllerNotesBoard.NOTEWIDTH;

public class ControllerKeyBoard {

    @FXML
    private ScrollPane keyBoardScrollPane;

    public ScrollPane getKeyBoardScrollPane() {
        return keyBoardScrollPane;
    }


    @FXML
    private void initialize(){
        loadKeyBoard();
    }

    @FXML
    public void loadKeyBoard(){

        AnchorPane root = new AnchorPane();

        root.getChildren().addAll(createLineNotes());
        keyBoardScrollPane.setContent(root);
        keyBoardScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        keyBoardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //Disable mouse action
        keyBoardScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            event.consume();
        });

    }

    public Group createLineNotes() {

        Group group = new Group();

        int TXTAJUTY = 12;
        int TXTAJUTX = NOTEWIDTH*3;
        int OFFTSET = -2;

        for (int i = 0; i <= NBNOTE; i++) {

            Rectangle whiteNote = new Rectangle(0, ((NBNOTE - i) * NOTEHEIGHT)-OFFTSET, NOTEWIDTH * 4, NOTEHEIGHT);
            whiteNote.setCache(true); //upgrade performance

            Text text = new Text(TXTAJUTX, ((NBNOTE - i) * NOTEHEIGHT + TXTAJUTY)-OFFTSET, null);
            text.setCache(true); //upgrade performance


            Rectangle blackNote;
            if( i % 7 != 2 && i % 7 != 6 ) {
                blackNote = new Rectangle(0, (((NBNOTE - i) * NOTEHEIGHT - NOTEHEIGHT / 2))-OFFTSET, NOTEWIDTH * 2, NOTEHEIGHT);
                blackNote.setFill(Color.BLACK);
                blackNote.setCache(true); //upgrade performance

            } else {
                blackNote = new Rectangle(0, ((NBNOTE - i) * NOTEHEIGHT - NOTEHEIGHT / 2)-OFFTSET, NOTEWIDTH * 2, NOTEHEIGHT);
                blackNote.setFill(Color.WHITE);
                blackNote.setCache(true); //upgrade performance

            }

            switch (i % 7) {
                case 0:
                    whiteNote.setFill(Color.WHITE);
                    text.setText("C");
                    break;

                case 1:
                    whiteNote.setFill(Color.WHITE);
                    text.setText("D");
                    break;

                case 2:
                    whiteNote.setFill(Color.WHITE);
                    text.setText("E");
                    break;
                case 3:
                    whiteNote.setFill(Color.WHITE);
                    text.setText("F");
                    break;

                case 4:
                    whiteNote.setFill(Color.WHITE);
                    text.setText("G");
                    break;

                case 5:
                    whiteNote.setFill(Color.WHITE);
                    text.setText("A");
                    break;

                case 6:
                    whiteNote.setFill(Color.WHITE);
                    text.setText("B");
                    break;

            }
            whiteNote.setStroke(Color.BLACK);
            text.setFont(new Font("Helvetica", 9));

            group.getChildren().addAll(whiteNote,blackNote, text);

        }

        return group;

    }





}
