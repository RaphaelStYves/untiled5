package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.ControllerKey.Controller;
import sample.chordBoard.ChordTile;
import sample.chordBoard.ControllerChordBoard;
import sample.keyBoard.ControllerKeyBoard;
import sample.menuBar.ControllerMenuBar;
import sample.midiSound.SoundMidi;
import sample.notes.ControllerNotesBoard;
import sample.model.Piece;

public class Main extends Application {

    //Dimension
    public static final int NOTEWIDTH = 12;
    public static final int NOTEHEIGHT = 12;
    public static final int NBNOTE = 70;
    public static final int NBPULSEVISIBLE = 128;

    private Piece oldPiece;
    private Piece newPiece;

public Main(){

}
   @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1700,950);

        FXMLLoader loader;

        //MenuBar
        VBox vbox = new VBox();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/menuBar/menuBar.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerMenuBar controllerMenuBar = loader.getController();

        //KeyBoard
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/keyBoard/keyboard.fxml"));
        root.setLeft(loader.load());
        ControllerKeyBoard controllerKeyBoard = loader.getController();

        //Create Piece
        oldPiece = controllerMenuBar.createOldPiece();
        newPiece = controllerMenuBar.createNewPiece();

        //ScrollPane notes
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/notes/notesBoard.fxml"));
        root.setCenter(loader.load());
        ControllerNotesBoard controllernotesBoard = loader.getController();
        controllernotesBoard.setPiece(oldPiece, newPiece);
        controllernotesBoard.setScene(scene);


        //Chord Board
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/chordBoard/chordBoard.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerChordBoard controllerChordBoard = loader.getController();
        controllerChordBoard.setPiece(oldPiece, newPiece);
        root.setTop(vbox);

        //add controllerNotes to controller
        controllerMenuBar.setControllerNotes(controllernotesBoard);
        controllerMenuBar.setControllerKeyBoard(controllerKeyBoard);
        controllerMenuBar.setControllerChordBoard(controllerChordBoard);


        Controller controller = new Controller();
        scene = controller.setKeyController(scene, controllerChordBoard, controllernotesBoard, new SoundMidi(newPiece, controllernotesBoard ));

       primaryStage.setScene(scene);

        primaryStage.show();

        //load the lastPiece
        controllerMenuBar.loadLastPiece();

    }


    public static void main(String[] args) {
        launch(args);
    }




}
