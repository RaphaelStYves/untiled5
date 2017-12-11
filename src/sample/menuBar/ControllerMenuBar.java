package sample.menuBar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import sample.notes.ControllerNotesBoard;
import sample.chordBoard.ControllerChordBoard;
import sample.keyBoard.ControllerKeyBoard;
import sample.model.Piece;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.io.IOException;

public class ControllerMenuBar {

    private Piece oldPiece;
    private Piece newPiece;
    private ControllerNotesBoard controllerNotes ;
    private ControllerKeyBoard controllerKeyBoard ;
    private ControllerChordBoard controllerChordBoard ;

    @FXML
    private MenuBar menuBar;

    @FXML
    private void initialize(){

    }

    public void setControllerNotes(ControllerNotesBoard controllerNotes) {
        this.controllerNotes = controllerNotes ;
    }
    public void setControllerKeyBoard(ControllerKeyBoard controllerKeyBoard) {this.controllerKeyBoard = controllerKeyBoard ; }
    public void setControllerChordBoard(ControllerChordBoard controllerChordBoard) {this.controllerChordBoard = controllerChordBoard ; }

    public void openDialog(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {

            try {
                oldPiece.load(file);
                newPiece.load(file);
                controllerNotes.loadAllComponants();
                controllerChordBoard.loadChordBoard();


                controllerNotes.getMainScrollPane().vvalueProperty().bindBidirectional(controllerKeyBoard.getKeyBoardScrollPane().vvalueProperty());
                controllerNotes.getMainScrollPane().hvalueProperty().bindBidirectional(controllerChordBoard.getChordBoardScrollPane().hvalueProperty());

            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

