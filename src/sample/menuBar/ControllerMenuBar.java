package sample.menuBar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import sample.Main;
import sample.notes.ControllerNotesBoard;
import sample.chordBoard.ControllerChordBoard;
import sample.keyBoard.ControllerKeyBoard;
import sample.model.Piece;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
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
    private Main main;


    String workingDir = System.getProperty("user.dir");
    private File fileWithLastPath = new File(workingDir + "\\files\\lastfile.txt");
    private String nameFile;

    @FXML
    private MenuBar menuBar;


    @FXML
    private Menu filedevice;



    @FXML
    private void initialize() {


        MidiDevice device = null;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        CheckMenuItem checkMenuItem = null;

        for (int i = 0; i < infos.length; i++) {
            try {
                device = MidiSystem.getMidiDevice(infos[i]);

                checkMenuItem = new CheckMenuItem(device.getDeviceInfo().getName());

            } catch (MidiUnavailableException e) {
                // Handle or throw exception...
            }

            filedevice.getItems().add(checkMenuItem);
        }



    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void loadLastPiece() throws IOException, InvalidMidiDataException, MidiUnavailableException {

        String content = readFile(fileWithLastPath.toString(), Charset.defaultCharset());
        File pathOftheLastPiece = new File(content);
        loadDataView(pathOftheLastPiece);
        nameFile = pathOftheLastPiece.getName();
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


        String workingDir = System.getProperty("user.dir");
        File initialPath = new File(workingDir + "\\midifile");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(initialPath);
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {


            //save the path file for the next initialize.
            SaveFile(file.toString(), fileWithLastPath);



            main.createPiece();
            loadDataView(file);

        }
    }
    public Piece createOldPiece(){
         return oldPiece = new Piece();
    }

    public Piece createNewPiece(){
        return newPiece = new Piece();
    }

    private void loadDataView(File file) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        oldPiece.load(file);
        newPiece .load(file);
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

    private void nameFileInPath(){

    }


    public String getNameFile() {
        return nameFile;
    }
}

