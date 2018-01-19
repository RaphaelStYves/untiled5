package sample.midiSound;

import sample.model.Piece;
import sample.notes.ControllerNotesBoard;

import javax.sound.midi.*;
import java.io.File;
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
    private Sequencer  seq;
    private Transmitter seqTrans;
    private Sequence    sequence;
    private Receiver synthRcvr;
    private Piece piece;
    private ControllerNotesBoard controllerNotesBoard;
    private MidiDevice device;
    int OFFSET =70; //synchronisation image with midiSound

    private int count = 0;


    public SoundMidi(ControllerNotesBoard controllerNotesBoard) {

        this.controllerNotesBoard = controllerNotesBoard;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void play() throws MidiUnavailableException, InvalidMidiDataException {

        //Create the sequence(midi)
        sequence = new Sequence(piece.getDivisionType(), piece.getResolution());
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

                ShortMessage off = new ShortMessage();
                off.setMessage(NOTE_OFF, piece.notes.get(i).getChannel(), piece.notes.get(i).getCNote(), 0);
                track.add(new MidiEvent(off, (piece.notes.get(i).getPulse16() + piece.notes.get(i).getLenght16())*(piece.getResolution()/4)));
            }

        }
        ///////////////////
        for (int i = 0; i < piece.getPieceLenght16(); i++) {

            MetaMessage t = new MetaMessage(127, new byte[6], 0);
            MidiEvent ev = new MidiEvent(t,(i*piece.getResolution()/4) + OFFSET);
            track.add(ev);
        }

        MidiDevice device;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        device = MidiSystem.getMidiDevice(infos[4]);

        if (!(device.isOpen())) {
            try {
                device.open();
            } catch (MidiUnavailableException e) {
            }
        }

        synthRcvr = device.getReceiver();
        seq = MidiSystem.getSequencer();
        seqTrans = seq.getTransmitter();
        seqTrans.setReceiver(synthRcvr);
        seq.open();

        seq.addMetaEventListener(meta -> {
            count++;
            System.out.println(count);
            controllerNotesBoard.movingBar(count);
        });

        seq.setSequence(sequence);
        seq.setTempoInBPM(piece.getBpm());
        seq.start();
    }

    public void stop(){
        seq.stop();
    }

    public void save(){
        try {
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
            MidiSystem.write(sequence,1, new File("workingDir\\files\\midi.mid"));
        } catch (IOException e) {
        }
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDevice(MidiDevice device) {
        this.device = device;
    }
}
