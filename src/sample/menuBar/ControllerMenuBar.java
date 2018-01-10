package sample.menuBar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import sample.notes.ControllerNotesBoard;
import sample.chordBoard.ControllerChordBoard;
import sample.keyBoard.ControllerKeyBoard;
import sample.model.Piece;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMenuBar {

    private Piece oldPiece;
    private Piece newPiece;
    private ControllerNotesBoard controllerNotes ;
    private ControllerKeyBoard controllerKeyBoard ;
    private ControllerChordBoard controllerChordBoard ;

    private File fileWithLastPath = new File("C:\\Users\\rapha\\IdeaProjects\\untitled5\\files\\lastfile.txt");


    @FXML
    private MenuBar menuBar;

    @FXML
    private void initialize() {

    }

    public void loadLastPiece() throws IOException, InvalidMidiDataException, MidiUnavailableException {

        String content = readFile(fileWithLastPath.toString(), Charset.defaultCharset());
        File pathOftheLastPiece = new File(content);
        loadDataView(pathOftheLastPiece);
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void setControllerNotes(ControllerNotesBoard controllerNotes) {
        this.controllerNotes = controllerNotes ;
    }
    public void setControllerKeyBoard(ControllerKeyBoard controllerKeyBoard) {this.controllerKeyBoard = controllerKeyBoard ; }
    public void setControllerChordBoard(ControllerChordBoard controllerChordBoard) {
        this.controllerChordBoard = controllerChordBoard ;


    }


    public void openDialog(ActionEvent actionEvent) throws MidiUnavailableException, InvalidMidiDataException, IOException {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {

            //save the path file for the next initialize.
            SaveFile(file.toString(), fileWithLastPath);

                loadDataView(file);

        }
    }

    private void loadDataView(File file) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        oldPiece.load(file);
        newPiece.load(file);
        controllerNotes.loadAllComponants();
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MenuBar.class
                    .getName()).log(Level.SEVERE, null, ex);
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

