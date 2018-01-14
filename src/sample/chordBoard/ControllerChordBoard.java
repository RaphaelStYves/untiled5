package sample.chordBoard;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.chordFinder.AllBeatChordsAllChordsAll32;
import sample.chordFinder.AllBeatChordsAllChordsOne32;
import sample.chordFinder.ChordFinder;
import sample.model.EChord;
import sample.model.Piece;

import java.util.HashMap;
import java.util.Map;

import static sample.Main.NBPULSEVISIBLE;
import static sample.Main.NOTEHEIGHT;
import static sample.Main.NOTEWIDTH;



public class ControllerChordBoard {

    public Piece oldPiece;
    public Piece newPiece;

   public final Map<Integer, EChord> mapTilesOld  = new HashMap<>();
    public final Map<Integer, EChord> mapTilesNew  = new HashMap<>();

    public Map<Integer, EChord> getMapTilesNew() {
        return mapTilesNew;
    }

    public Map<Integer, EChord> getMapTilesOld() {
        return mapTilesOld;
    }

    public Group oldChordTiles = new Group();
    public Group newChordTiles = new Group();

    @FXML
    private Pane root;

    private EChord chord = EChord.nothing;

    public void setChord(EChord chord) {
        this.chord = chord;
    }

    @FXML
    private void initialize() {

        showTilesChords(0);
        showTilesChords(1);

        root.getChildren().add(oldChordTiles); //old piece
        root.getChildren().add(newChordTiles); //new piece

        root.addEventFilter(MouseEvent.DRAG_DETECTED, event -> root.startFullDrag());
    }

    public void showTilesChords(int placeY ) {

            fillDataInChords();

            for (int i = 0; i < NBPULSEVISIBLE; i++) {
                ChordTile chordTile = new ChordTile(i*NOTEWIDTH,placeY*NOTEHEIGHT*2,NOTEWIDTH,NOTEHEIGHT*2, chord.toString());
                chordTile.setEchord(mapTilesOld.get(i));

                int finalI = i;
                if(placeY == 0) {
                    //////////////// 0 = old piece //////////////////
                    chordTile.setOnMouseDragEntered(event -> {
                        chordTile.setEchord(chord);
                        mapTilesOld.replace(finalI, chord);

                    });

                    oldChordTiles.getChildren().add(chordTile);

                }
                else {
                    //////////////// 1 = new piece //////////////////
                    chordTile.setOnMouseDragEntered(event -> {
                        chordTile.setEchord(chord);
                        mapTilesNew.replace(finalI, chord);
                    });

                    newChordTiles.getChildren().add(chordTile);
                }
            }
        }


    public void fillDataInChords() {

        for (int i = 0; i < NBPULSEVISIBLE; i++) {
            mapTilesOld.put(i, EChord.nothing);
            mapTilesNew.put(i, EChord.nothing);
        }
    }


    public void setPiece(Piece oldPiece, Piece newPiece) {
        this.oldPiece = oldPiece;
        this.newPiece = newPiece;

    }


    public void setChordTile(ChordFinder chordFinder) {
        AllBeatChordsAllChordsAll32 allBeatChordsAllChordsAll32;
        allBeatChordsAllChordsAll32 = chordFinder.findAllBeatChordsAllChordsAll32();

        for (int i = 0; i < allBeatChordsAllChordsAll32.getAllBeatChordsAllChordsAll32().size() ; i++) {


            //Each i, is a block
            int block = i*32;
        AllBeatChordsAllChordsOne32 allBeatChordsAllChordsOne32= allBeatChordsAllChordsAll32.getAllBeatChordsAllChordsAll32().get(block);


        int indexBeat = allBeatChordsAllChordsOne32.getBestIndexChord();
        int index = 0;
        for (int j = 0; j < allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(indexBeat).getFullBeatChordAllChordsOne32Map().size() ; j++) {
            EChord eChord = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(indexBeat).getFullBeatChordAllChordsOne32Map().get(j).getBestEChord();
            int numbOfPulse = allBeatChordsAllChordsOne32.getAllBeatChordsAllChordsOne32Map().get(indexBeat).getFullBeatChordAllChordsOne32Map().get(j).getNumberOfPulse();

            for (int k = 0; k < numbOfPulse ; k++) {
                mapTilesOld.replace(block + index, eChord);
                mapTilesNew.replace(block + index, eChord);
                index +=1;
            }
        }

        }
    }
}
