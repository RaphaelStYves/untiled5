package sample.chordBoard;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import sample.chordFinder.ChordFinder;
import sample.model.EChord;
import sample.model.Piece;

import static sample.notes.ControllerNotesBoard.NOTEHEIGHT;
import static sample.notes.ControllerNotesBoard.NOTEWIDTH;



public class ControllerChordBoard {

    private Piece oldPiece;
    private Piece newPiece;
    private Color color = Color.BLUE;
    public EChord chord = EChord.nothing;


    private ObservableList<ChordTile> oldChordTiles;
    private ObservableList<ChordTile> newChordTiles;

    @FXML
    private ScrollPane chordBoardScrollPane;

    public ScrollPane getChordBoardScrollPane() {
        return chordBoardScrollPane;
    }

    @FXML
    private void initialize() {
        chordBoardScrollPane.addEventFilter(MouseEvent.DRAG_DETECTED,event -> {
            chordBoardScrollPane.startFullDrag();
        });


        chordBoardScrollPane.setOnKeyPressed(event ->  {
               switch (event.getCode()) {
                    case DIGIT1:{
                        color = Color.CORNFLOWERBLUE;
                        chord = EChord.I;
                    } break;
                    case Q:{
                        color = Color.BLUEVIOLET;
                        chord = EChord.i;
                    }
                    case W:{
                        color = Color.CORAL;
                        chord = EChord.II;
                    } break;
                    case DIGIT2:{
                        color = Color.DARKSALMON;
                        chord = EChord.ii;
                    } break;
                    case E:{
                        color  = Color.HOTPINK;
                        chord = EChord.III;
                    } break;
                    case DIGIT3:{
                        color  = Color.VIOLET;
                        chord = EChord.iii;
                    } break;
                    case DIGIT4: {
                        color  = Color.YELLOWGREEN;
                        chord = EChord.IV;
                    } break;
                    case R:{
                        color  = Color.PALEGREEN;
                        chord = EChord.iv;
                    } break;
                    case DIGIT5:{
                        color = Color.FIREBRICK;
                        chord = EChord.V;
                    } break;
                    case T:{
                        color = Color.PALEVIOLETRED;
                        chord = EChord.v;
                    } break;
                    case Y:{
                        color = Color.AQUA;
                        chord = EChord.VI;
                    } break;
                    case DIGIT6:{
                        color = Color.AQUAMARINE;
                        chord = EChord.vi;
                    } break;
                    case U:{
                        color = Color.TAN;
                        chord = EChord.VII;
                    } break;
                    case DIGIT7: {
                        color = Color.BEIGE;
                        chord = EChord.vii;
                    } break;
               }
        });

        chordBoardScrollPane.setOnKeyPressed(event ->  {
            switch (event.getCode()) {
                case F: {
                    ChordFinder chordFinder = new ChordFinder(oldPiece);
                    chordFinder.findChords();

                }
                break;
            }
        });
    }

    @FXML
    public void loadChordBoard() {

        Group allChords = new Group();
        Group oldChords = new Group();
        Group newChords = new Group();

        oldChords.getChildren().addAll(createTilesChords(0, oldChordTiles));
        newChords.getChildren().addAll(createTilesChords(1, newChordTiles));
        allChords.getChildren().addAll(oldChords,newChords);
        chordBoardScrollPane.setContent(allChords);
        chordBoardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chordBoardScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //Disable mouse action
        chordBoardScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            event.consume();
        });

    }

    public Group createTilesChords(int YPlace, ObservableList<ChordTile> chordTiles ) {

        Group group = new Group();

        for (int i = 0; i < oldPiece.getPieceLenght16(); i++) {
            ChordTile chordTile = new ChordTile(i * NOTEWIDTH,YPlace*NOTEHEIGHT*2,NOTEWIDTH,NOTEHEIGHT*2);
            chordTile.setOnMouseDragEntered(event -> {
                chordTile.setFill(color);
                chordTile.setEChord(chord);

            });
            chordTile.setStroke(Color.BLACK);
            chordTile.setFill(Color.GRAY);
            chordTile.setCache(true); //upgrade performance
            chordTiles.addAll(chordTile);
            group.getChildren().add(chordTile);
        }


        return group;

    }



    public void setPiece(Piece oldPiece, Piece newPiece) {
        this.oldPiece = oldPiece;
        this.newPiece = newPiece;
    }

    public void setChordTiles(ObservableList<ChordTile> oldChordTiles,ObservableList<ChordTile> newChordTiles) {
        this.oldChordTiles = oldChordTiles;
        this.newChordTiles = newChordTiles;
    }


}
