package sample.compilator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.chordBoard.ChordTile;
import sample.model.AlgoNote;
import sample.model.Piece;
import sample.notes.ControllerNotesBoard;

public class ControllerCompilatorButton  {

    private Piece oldPiece;
    private Piece newPiece;
    private ObservableList<ChordTile> oldChordTiles;
    private ObservableList<ChordTile> newChordTiles;

    private ControllerNotesBoard controllerNotes ;

    @FXML
    private Button compilatorButton;

    public void setPiece(Piece oldPiece, Piece newPiece) {
        this.oldPiece = oldPiece;
        this.newPiece = newPiece;
    }

    public void setChordTiles(ObservableList<ChordTile> oldChordTiles,ObservableList<ChordTile> newChordTiles) {
        this.oldChordTiles = oldChordTiles;
        this.newChordTiles = newChordTiles;
    }


    @FXML
    private void initialize() {

        compilatorButton.setOnMouseClicked(event -> {
            addChordInPiece();
            launchAlgo();
            reprinteNotes();
        });


   }

    public void setControllerNotes(ControllerNotesBoard controllerNotes) {
        this.controllerNotes = controllerNotes ;
    }

    private void reprinteNotes() {

        controllerNotes.deleteAllComponants();
        controllerNotes.loadAllComponants();

    }


    private void addChordInPiece(){

       for (int i = 0; i < oldPiece.chords.size(); i++) { //oldPiece and new Piece have always the same notes, then for next is valid for both.
           oldPiece.chords.get(i).setChord(oldChordTiles.get(i).getEChord());
           newPiece.chords.get(i).setChord(newChordTiles.get(i).getEChord());
       }
   }

    private void launchAlgo() {

        for (int i = 0; i < oldPiece.notes.size(); i++) {
                     int index = oldPiece.notes.get(i).getPulse16()-1; //to know where is the note
                int note = AlgoNote.changenote(oldPiece.chords.get(index).getChord(),newPiece.chords.get(index).getChord(),oldPiece.notes.get(i).getCNote());
                newPiece.notes.get(i).setCNote(note);
            }
        }

}





