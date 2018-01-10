package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.chordBoard.ChordTile;
import sample.chordBoard.ControllerChordBoard;
import sample.chordFinder.Beat;
import sample.chordFinder.ChordFinder;
import sample.chordFinder.PulseChord;
import sample.keyBoard.ControllerKeyBoard;
import sample.menuBar.ControllerMenuBar;
import sample.compilator.ControllerCompilatorButton;
import sample.model.EChord;
import sample.notes.ControllerNotesBoard;
import sample.model.Piece;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.Q;

public class Main extends Application {

    //Dimension
    public static final int NOTEWIDTH = 12;
    public static final int NOTEHEIGHT = 12;
    public static final int NBNOTE = 70;
    public static final int NBPULSEVISIBLE = 128;


public Main(){

}


    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,1700,950);

        Piece oldPiece = new Piece(); //new  Piece but empty
        Piece newPiece = new Piece(); //new  Piece but empty
        ObservableList<ChordTile> oldChordTiles = FXCollections.observableArrayList();
        ObservableList<ChordTile> newChordTiles = FXCollections.observableArrayList();

        FXMLLoader loader;

        //ScrollPane notes
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/notes/notesBoard.fxml"));
        root.setCenter(loader.load());
        ControllerNotesBoard controllernotesBoard = loader.getController();
        controllernotesBoard.setPiece(oldPiece, newPiece);

        //KeyBoard
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/keyBoard/keyboard.fxml"));
        root.setLeft(loader.load());
        ControllerKeyBoard controllerKeyBoard = loader.getController();

        //MenuBar
        VBox vbox = new VBox();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/menuBar/menuBar.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerMenuBar controllerMenuBar = loader.getController();
        controllerMenuBar.setPiece(oldPiece, newPiece);


        //Chord Board
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/chordBoard/chordBoard.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerChordBoard controllerChordBoard = loader.getController();
        controllerChordBoard.setPiece(oldPiece, newPiece);




        scene = controllerChordBoard.addKeyControle(scene);


        //Button Compilator
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/compilator/compilatorButton.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerCompilatorButton controllerCompilatorButton = loader.getController();
        controllerCompilatorButton.setPiece(oldPiece, newPiece);
        controllerCompilatorButton.setChordTiles(oldChordTiles,newChordTiles);


        root.setTop(vbox);


        //add controllerNotes to controller
        controllerMenuBar.setControllerNotes(controllernotesBoard);
        controllerMenuBar.setControllerKeyBoard(controllerKeyBoard);
        controllerMenuBar.setControllerChordBoard(controllerChordBoard);

        //add controllerNotes to ControllerCOmpilatorButton;
        controllerCompilatorButton.setControllerNotes(controllernotesBoard);


        primaryStage.setScene(scene);


        primaryStage.show();

        //load the lastPiece
        controllerMenuBar.loadLastPiece();

        ChordFinder chordFinder = new ChordFinder(newPiece);
        Beat beat = new Beat();
        beat = chordFinder.allPartOfOneBeatfor32(0, 0);
        controllernotesBoard.setbeat(beat);





//
//        //View piece  score of each pulse.
//        ChordFinder chordFinder = new ChordFinder(newPiece);
//        List<PulseChord> list = new ArrayList<>();
//        list = chordFinder.findScoreOfAllPulseAndAllChords();
//
//        controllernotesBoard.setAloViewPulseList(list);


//        //View piece  MOD12.
//        ChordFinder chordFinder = new ChordFinder(newPiece);
//        int[][] array = new int[ newPiece.getPieceLenght16()][12];
//        array = chordFinder.loadPieceInOnesInArray();
//
//        controllernotesBoard.setAloView(array);
//        controllernotesBoard.showMOD12();
    }


    public static void main(String[] args) {
        launch(args);
    }




}
