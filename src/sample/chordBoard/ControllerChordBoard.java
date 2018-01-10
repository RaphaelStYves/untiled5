package sample.chordBoard;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.chordFinder.ChordFinder;
import sample.model.EChord;
import sample.model.Piece;



import java.util.HashMap;
import java.util.Map;

import static sample.Main.NBPULSEVISIBLE;
import static sample.Main.NOTEHEIGHT;
import static sample.Main.NOTEWIDTH;



public class ControllerChordBoard {

    private Piece oldPiece;

    private final Map<Integer, EChord> mapTilesOld  = new HashMap<>();
    private final Map<Integer, EChord> mapTilesNew  = new HashMap<>();

    @FXML
    private Pane root;

    public EChord chord = EChord.nothing;
    public Color color = Color.BLUE;

    @FXML
    private void initialize() {

        root.getChildren().add(showTilesChords(0)); //old piece
        root.getChildren().add(showTilesChords(1)); //new piece

        root.addEventFilter(MouseEvent.DRAG_DETECTED, event -> root.startFullDrag());


    }

    public Group showTilesChords(int placeY ) {

            Group chordTiles = new Group();
            fillDataInChords();

            for (int i = 0; i < NBPULSEVISIBLE; i++) {
                ChordTile chordTile = new ChordTile(i * NOTEWIDTH,placeY*NOTEHEIGHT*2,NOTEWIDTH,NOTEHEIGHT*2);
                chordTile.setStroke(Color.BLACK);
                chordTile.setIndex(i);
                chordTile.setFill(echordToColor(mapTilesOld.get(i)));

                int finalI = i;
                if(placeY == 0) {
                    //////////////// 0 = old piece //////////////////
                    chordTile.setOnMouseDragEntered(event -> {
                        chordTile.setEChord(chord);
                        chordTile.setFill(echordToColor(chord));
                        mapTilesOld.replace(finalI, chord);
//                    System.out.println("applied chord: " + chord);
//                    System.out.println("index tiles: " + chordTile.getIndex());
//                    System.out.println("color is: " + color);
//                    System.out.println("group of tiles: " + placeY);
                    });



                }else {
                    //////////////// 1 = new piece //////////////////
                    chordTile.setOnMouseDragEntered(event -> {
                        chordTile.setEChord(chord);
                        chordTile.setFill(echordToColor(chord));
                        mapTilesNew.replace(finalI, chord);
//                    System.out.println("applied chord: " + chord);
//                    System.out.println("index tiles: " + chordTile.getIndex());
//                    System.out.println("color is: " + color);
//                    System.out.println("group of tiles: " + placeY);
                    });

                }
                chordTiles.getChildren().add(chordTile);
            }
            return chordTiles;
        }



    public void fillDataInChords() {

        for (int i = 0; i < NBPULSEVISIBLE; i++) {
            mapTilesOld.put(i, EChord.I);
        }
    }


    public void setPiece(Piece oldPiece, Piece newPiece) {
        this.oldPiece = oldPiece;

    }

    public Scene addKeyControle(Scene scene){

        scene.setOnKeyPressed(event ->  {
            switch (event.getCode()) {
                case DIGIT1:{
                    chord = EChord.I;
                    System.out.println("you chose '1' for echord.I");
                } break;
                case Q:{
                    chord = EChord.i;
                    System.out.println("you chose 'Q' for echord.i");
                }break;
                case W:{
                    chord = EChord.II;
                    System.out.println("you chose 'W' for echord.II");
                } break;
                case DIGIT2:{
                    chord = EChord.ii;
                    System.out.println("you chose '2' for echord.ii");
                } break;
                case E:{
                    chord = EChord.III;
                } break;
                case DIGIT3:{
                    chord = EChord.iii;
                } break;
                case DIGIT4: {
                    chord = EChord.IV;
                } break;
                case R: chord = EChord.iv;
                break;
                case DIGIT5:{
                    chord = EChord.V;
                } break;
                case T:{
                    chord = EChord.v;
                } break;
                case Y:{
                    chord = EChord.VI;
                } break;
                case DIGIT6:{
                    chord = EChord.vi;
                } break;
                case U:{
                    chord = EChord.VII;
                } break;
                case DIGIT7: {
                    chord = EChord.vii;
                } break;
                case F: {
                    ChordFinder chordFinder = new ChordFinder(oldPiece);
                    //chordFinder.findChords();
                }
                break;
            }

        });

        return scene;
    }

    public Color echordToColor(EChord echord){


            switch (echord) {
                case I: color = Color.CORNFLOWERBLUE;
                 break;
                case i:color = Color.BLUEVIOLET;
               break;
                case II: color = Color.CORAL;
                break;
                case ii:color = Color.DARKSALMON;
                 break;
                case III: color  = Color.HOTPINK;
                 break;
                case iii: color  = Color.VIOLET;
                break;
                case IV:color  = Color.YELLOWGREEN;
                 break;
                case iv: color  = Color.PALEGREEN;
                 break;
                case V: color = Color.FIREBRICK;
                 break;
                case v: color = Color.PALEVIOLETRED;
                 break;
                case VI:color = Color.AQUA;
                break;
                case vi: color = Color.AQUAMARINE;
                break;
                case VII: color = Color.TAN;
                 break;
                case vii: color = Color.BEIGE;
                break;
                default: color = Color.BLUE;
                    break;

            }



        return color;
    }




}
