package sample.midiSound;

import javafx.scene.shape.Rectangle;
import sample.model.Piece;
import sample.notes.ControllerNotesBoard;

import javax.sound.midi.*;
import java.io.IOException;
import java.util.Map;

import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;

/**
 * A skeleton for MIDI playback
 * Uses a sequence and sequencer:
 * Go to http://docs.oracle.com/javase/8/docs/technotes/guides/sound/programmer_guide/chapter11.html
 *
 */
public class SoundMidi  {

    public static final int END_OF_TRACK = 47;

    //Tempo track located at -1
    private Map<Integer, Track> _tracks;
    private Sequence sequence;
    private Synthesizer synthesizer;
    private Sequencer sequencer;
    private Piece piece;
    private ControllerNotesBoard controllerNotesBoard;
    int OFFSET =70; //synchronisation image with midiSound

    private int count = 0;


    public SoundMidi(Piece piece, ControllerNotesBoard controllerNotesBoard) {

        this.piece = piece;
        this.controllerNotesBoard = controllerNotesBoard;

    }


    public void play() throws MidiUnavailableException, InvalidMidiDataException, IOException {

        //Create the sequence(midi)
        Sequence sequence = new Sequence(piece.getDivisionType(), piece.getResolution());
        Track track = sequence.createTrack();

        for (int i = 0; i < piece.notes.size(); i++) {

            ShortMessage sm = new ShortMessage();

            sm.setMessage(ShortMessage.PROGRAM_CHANGE, piece.notes.get(i).getChannel(), piece.notes.get(i).getInstrument(), 0);
            track.add(new MidiEvent(sm, 0));

            //loop for each note in notes//

            if (piece.notes.get(i).getOn() == true) {
                ShortMessage on = new ShortMessage();
                on.setMessage(NOTE_ON, piece.notes.get(i).getChannel(), piece.notes.get(i).getCNote(), piece.notes.get(i).getVelocity());
                track.add(new MidiEvent(on, piece.notes.get(i).getPulse16()*(piece.getResolution()/4)));
            }else {
                ShortMessage off = new ShortMessage();
                off.setMessage(NOTE_OFF, piece.notes.get(i).getChannel(), piece.notes.get(i).getCNote(), 0);
                track.add(new MidiEvent(off, piece.notes.get(i).getPulse16()*(piece.getResolution()/4)));
            }

        }
        ///////////////////
        for (int i = 0; i < piece.getPieceLenght16(); i++) {

            MetaMessage t = new MetaMessage(127, new byte[6], 0);
            MidiEvent ev = new MidiEvent(t,(i*piece.getResolution()/4) + OFFSET);
            track.add(ev);

        }

///////////////////////

        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setTempoInBPM(piece.getBpm());



        sequencer.addMetaEventListener(meta -> {

            count++;
            System.out.println(count);

            controllerNotesBoard.movingBar(count);

        });

        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();

        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(piece.getBpm());


        sequencer.getTransmitter();


        sequencer.start();

    }

    public void stop(){
        sequencer.stop();
    }

    public void setCount(int count) {
        this.count = count;
    }
}
