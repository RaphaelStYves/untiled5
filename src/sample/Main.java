package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.chordBoard.ChordTile;
import sample.chordBoard.ControllerChordBoard;
import sample.keyBoard.ControllerKeyBoard;
import sample.menuBar.ControllerMenuBar;
import sample.compilator.ControllerCompilatorButton;
import sample.notes.ControllerNotesBoard;
import sample.model.Piece;

public class Main extends Application {




public Main(){

}


    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane();

        Piece oldPiece = new Piece();
        Piece newPiece = new Piece();
        ObservableList<ChordTile> oldChordTiles = FXCollections.observableArrayList();
        ObservableList<ChordTile> newChordTiles = FXCollections.observableArrayList();

        FXMLLoader loader;

        //ScrollPane notes
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/notes/notesBoard.fxml"));
        root.setCenter(loader.load());
        ControllerNotesBoard controllernotes = loader.getController();
        controllernotes.setPiece(oldPiece, newPiece);

        //ScrollPane KeyBoard
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/keyBoard/keyboard.fxml"));
        root.setLeft(loader.load());
        ControllerKeyBoard controllerKeyBoard = loader.getController();

        //MenuBar
        VBox vbox = new VBox();
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/menuBar/menuBar.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerMenuBar controller = loader.getController();
        controller.setPiece(oldPiece, newPiece);


        //ScrollPane Chord Board
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/chordBoard/chordBoard.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerChordBoard controllerChordBoard = loader.getController();
        controllerChordBoard.setPiece(oldPiece, newPiece);
        controllerChordBoard.setChordTiles(oldChordTiles,newChordTiles);

        //Button Compilator
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/compilator/compilatorButton.fxml"));
        vbox.getChildren().add(loader.load());
        ControllerCompilatorButton controllerCompilatorButton = loader.getController();
        controllerCompilatorButton.setPiece(oldPiece, newPiece);
        controllerCompilatorButton.setChordTiles(oldChordTiles,newChordTiles);


        root.setTop(vbox);


        //add controllerNotes to controller
        controller.setControllerNotes(controllernotes);
        controller.setControllerKeyBoard(controllerKeyBoard);
        controller.setControllerChordBoard(controllerChordBoard);

        //add controllerNotes to ControllerCOmpilatorButton;
        controllerCompilatorButton.setControllerNotes(controllernotes);


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }




}
