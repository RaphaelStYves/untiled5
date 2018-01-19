package sample.ControllerKey;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import sample.VoiceFinder.VoiceFinder;
import sample.chordBoard.ChordTile;
import sample.chordBoard.ControllerChordBoard;
import sample.chordFinder.ChordFinder;
import sample.midiSound.SoundMidi;
import sample.model.EChord;
import sample.notes.ControllerNotesBoard;
import sample.stageShortCut.ImgShortCut;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;

import static sample.Main.NBPULSEVISIBLE;

public class Controller {

    private ControllerChordBoard controllerChordBoard;
    private ControllerNotesBoard controllerNotesBoard;


    private boolean playing = false;
    private boolean shortCutWindowisOpen = false;



   public Scene setKeyController(Scene scene, ControllerChordBoard controllerChordBoard, ControllerNotesBoard controllerNotesBoard, SoundMidi soundMidi, VoiceFinder voiceFinder, ImgShortCut imgShortCut) {

       this.controllerChordBoard = controllerChordBoard;
       this.controllerNotesBoard = controllerNotesBoard;

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:controllerChordBoard.setChord(EChord.I); break;
                case Q: controllerChordBoard.setChord(EChord.i); break;
                case W: controllerChordBoard.setChord(EChord.II); break;
                case DIGIT2:controllerChordBoard.setChord(EChord.ii); break;
                case E: controllerChordBoard.setChord(EChord.III);break;
                case DIGIT3: controllerChordBoard.setChord(EChord.iii);break;
                case DIGIT4: controllerChordBoard.setChord(EChord.IV); break;
                case R:  controllerChordBoard.setChord(EChord.iv); break;
                case DIGIT5:controllerChordBoard.setChord(EChord.V);break;
                case T: controllerChordBoard.setChord(EChord.v); break;
                case Y: controllerChordBoard.setChord(EChord.VI); break;
                case DIGIT6: controllerChordBoard.setChord(EChord.vi); break;
                case U: controllerChordBoard.setChord(EChord.VII); break;
                case DIGIT7: controllerChordBoard.setChord(EChord.vii); break;
                case F: {
                    try {
                        controllerChordBoard.setChordTile(new ChordFinder(controllerChordBoard.oldPiece));

                        //Fill TileChord with the good color.
                        for (int i = 0; i < NBPULSEVISIBLE ; i++) {
                            ChordTile chordTileOld =  (ChordTile)controllerChordBoard.oldChordTiles.getChildren().get(i);
                            chordTileOld.setEchord(controllerChordBoard.mapTilesOld.get(i));


                            ChordTile chordTileNew =  (ChordTile)controllerChordBoard.newChordTiles.getChildren().get(i);
                            chordTileNew.setEchord(controllerChordBoard.mapTilesOld.get(i));

                        }
                        System.out.println("you press 'F' for Chord Finder");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
                case M: {
                    controllerNotesBoard.noteAlgo(controllerChordBoard);
                    System.out.println("You pressed 'M' to modifies new notes");
                }
                break;

                case C: {
                    imgShortCut.show();
                    System.out.println("You pressed 'C' to show keyboard ShortCut");
                }
                break;



                case SPACE: {
                    if(playing == false){
                        try {
                            soundMidi.play();
                            playing = true;


                        } catch (MidiUnavailableException e) {
                            e.printStackTrace();
                        } catch (InvalidMidiDataException e) {
                            e.printStackTrace();
                        }
                        System.out.println("You pressed 'Space Bar' to play midi");
                    }else {

                        soundMidi.stop();
                        soundMidi.setCount(0);
                        playing = false;
                        System.out.println("You pressed 'Space Bar' to stop midi");
                    }
                }
                break;
                case S: {
                    soundMidi.save();
                    System.out.println("You pressed 'S' to save your song");
                }
                break;



            }

        });


       return scene;
    }
}
