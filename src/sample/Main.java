package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.ControllerKey.Controller;
import sample.VoiceFinder.VoiceFinder;
import sample.chordBoard.ControllerChordBoard;
import sample.chordFinder.AllBeatChordsAllChordsOne32;
import sample.chordFinder.ChordFinder;
import sample.keyBoard.ControllerKeyBoard;
import sample.menuBar.ControllerMenuBar;
import sample.midiSound.SoundMidi;
import sample.notes.ControllerNotesBoard;
import sample.model.Piece;
import sample.shortCut.ImgShortCut;

public class Main extends Application {

    //Dimension
    public static final int NOTEWIDTH = 12;
    public static final int NOTEHEIGHT = 12;
    public static final int NBNOTE = 70;
    public static final int NBPULSEVISIBLE = 128;

    private Piece oldPiece;
    private Piece newPiece;

    private ControllerNotesBoard controllernotesBoard;
    private ControllerChordBoard controllerChordBoard;
    private ControllerKeyBoard controllerKeyBoard;
    private ControllerMenuBar controllerMenuBar;
    private  SoundMidi soundMidi;
    private VoiceFinder voiceFinder;
    private Stage stage;
    private ImgShortCut imgShortCut;


    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root,1700,950);

public Main(){

}
   @Override
    public void start(Stage primaryStage) throws Exception{

    this.stage = primaryStage;

        FXMLLoader loader;

        //MenuBar
        VBox vbox = new VBox();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/menuBar/menuBar.fxml"));
        vbox.getChildren().add(loader.load());
        controllerMenuBar = loader.getController();

        //KeyBoard
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/keyBoard/keyboard.fxml"));
        root.setLeft(loader.load());
        controllerKeyBoard = loader.getController();

        //ScrollPane notes
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/notes/notesBoard.fxml"));
        root.setCenter(loader.load());
        controllernotesBoard = loader.getController();
        controllernotesBoard.setScene(scene);

        //Chord Board
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/chordBoard/chordBoard.fxml"));
        vbox.getChildren().add(loader.load());
        controllerChordBoard = loader.getController();
        root.setTop(vbox);

        //add controllerNotes to controller
        controllerMenuBar.setControllerNotes(controllernotesBoard);
        controllerMenuBar.setControllerKeyBoard(controllerKeyBoard);
        controllerMenuBar.setControllerChordBoard(controllerChordBoard);
        controllerMenuBar.setMain(this);

        soundMidi = new SoundMidi(controllernotesBoard);
        soundMidi.setPiece(newPiece);

       voiceFinder = new VoiceFinder();
       voiceFinder.setPiece(newPiece);

       imgShortCut  = new ImgShortCut();

        Controller controller = new Controller();
        scene = controller.setKeyController(scene, controllerChordBoard, controllernotesBoard, soundMidi, voiceFinder, imgShortCut);

       primaryStage.setScene(scene);

       createPiece();

       primaryStage.show();

        //load the lastPiece
      // controllerMenuBar.loadLastPiece();
      // stage.setTitle(controllerMenuBar.getNameFile() + "    Number of track " + oldPiece.getTrackNumbers().size() + 1); //+1 because we start to zero


//       ChordFinder chordFinder = new ChordFinder(newPiece);
//       AllBeatChordsAllChordsOne32 allBeatChordsAllChordsOne32 = new AllBeatChordsAllChordsOne32();
//       allBeatChordsAllChordsOne32 = chordFinder.findAllBeatChordsAllChordsOne32(0);
//       controllernotesBoard.viewAllBeatChordsAllChordsAll32(allBeatChordsAllChordsOne32);

    }

    public void createPiece(){

        //Create Piece
        oldPiece = controllerMenuBar.createOldPiece();
        newPiece = controllerMenuBar.createNewPiece();


        //Sette Piece in all needed Class
        controllernotesBoard.setPiece(oldPiece, newPiece);
        controllerChordBoard.setPiece(oldPiece, newPiece);
        soundMidi.setPiece(newPiece);

        oldPiece.getTrackNumbers().size();
        stage.setTitle(controllerMenuBar.getNameFile() + "       Number of track " + oldPiece.getTrackNumbers().size() + 1); //+1 because we start to zero
    }

    public static void main(String[] args) {
        launch(args);
    }

}
