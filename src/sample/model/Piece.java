package sample.model;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Piece {

        //Property
    private int resolution;
    private float divisionType;
    private int cTranspose;
    private int pieceLenght16;
    private float bpm = 0;
    private String name = "noName";
    public List<Note> notes = new ArrayList<>();
    public ArrayList<Chord> chords = new ArrayList<>();
    public Map<Integer, Pulse> pulses = new HashMap<>();
    public Map<Integer, Tracknumber> trackNumbers = new HashMap<>();


    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public Piece() {
    }

    public Piece load(File file )throws IOException, InvalidMidiDataException, MidiUnavailableException {

        this.name = fileNameWithoutExtension(file);

        Sequence sequence = MidiSystem.getSequence(file);
        Sequencer seqr = MidiSystem.getSequencer();
        seqr.open();
        seqr.setSequence(sequence);

        bpm = seqr.getTempoInBPM();
        resolution = sequence.getResolution();
        divisionType = sequence.getDivisionType();


        int trackNumber = 0;
        for (Track track : sequence.getTracks()) {
            trackNumber++;

            for (int i = 0; i < track.size(); i++) {
                Note note = new Note();

                note.setTracknumber(trackNumber);

                MidiEvent event = track.get(i);

                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    note.setChannel(sm.getChannel());

                    if (sm.getCommand() == NOTE_ON) {

                        note.setIndex(i);
                        note.setOn(true);
                        note.setNote(sm.getData1());
                        note.setVelocity(sm.getData2());
                        note.setPulse(event.getTick());
                        note.setPulse16(event.getTick());
                        note.setOctave((sm.getData1() / 12) - 1);



                        note.setNotename(NOTE_NAMES[sm.getData1() % 12]);

                        notes.add(note);


                        if ( !pulses.containsKey(note.getPulse16())){

                            Pulse pulse = new Pulse();

                            pulses.put(note.getPulse16(), pulse);

                        }else {

                            Pulse pulse = pulses.get(note.getPulse16());
                            pulse.addNote(note);

                        }



                        if ( !trackNumbers.containsKey(note.getTracknumber())){

                            Tracknumber tracknumber = new Tracknumber();
                            tracknumber.addNote(note);
                            trackNumbers.put(note.getTracknumber(), tracknumber);

                        }else {

                            Tracknumber tracknumber = trackNumbers.get(note.getTracknumber());
                            tracknumber.addNote(note);

                        }


                    } else if (sm.getCommand() == NOTE_OFF) {

                        note.setIndex(i);
                        note.setOn(false);
                        note.setNote(sm.getData1());
                        note.setPulse(event.getTick());
                        note.setVelocity(sm.getData2());

                        note.setNotename(NOTE_NAMES[sm.getData1() % 12]);

                        notes.add(note);


                    }
                }
            }
        }
        calculateTranspose();
        addCNotes();
        findPieceLenght16();

        calculateLenght();
        ChangeToFalse();
        initChords();


        return this;
    }

    private void addCNotes() {
        for (int i = 0; i < notes.size(); i++) {
           notes.get(i).setCNote(notes.get(i).getNote() + cTranspose);
        }

    }

    public String fileNameWithoutExtension(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf('.');
        if (pos > 0 && pos < (name.length() - 1)) {
            // there is a '.' and it's not the first, or last character.
            return name.substring(0,  pos);
        }
        return name;
    }



    private void ChangeToFalse() {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getVelocity() == 0) {
                notes.get(i).setOn(false);
            }
        }
    }

    private void calculateLenght() {

        int nnote;
        int nnote2;
        int cchan;
        int cchan2;
        long mtick;
        long mtick2;


        for (int i = 0; i < notes.size(); i++) {

            if (!notes.get(i).getOn() || notes.get(i).getVelocity() == 0) {
                nnote = notes.get(i).getNote();
                cchan = notes.get(i).getChannel();
                mtick = notes.get(i).getPulse();

                for (int j = i - 1; j >= 0; j--) {
                    nnote2 = notes.get(j).getNote();
                    cchan2 = notes.get(j).getChannel();

                    if (nnote == nnote2 && cchan == cchan2) {
                        mtick2 = notes.get(j).getPulse();

                        if (mtick != mtick2) {
                            notes.get(j).setLength(mtick - mtick2);
                            notes.get(j).setLenght16(mtick - mtick2);
                        }
                        break;
                    }
                }
            }
        }


    }

    private void calculateTranspose() {

        int noteId;
        Map<Integer, Integer> mapTrans = new HashMap<>();
        //calcul chaque note pour chaque offset
        for (int offSetDT = 0; offSetDT < 12; offSetDT++) {
            int temp = 0;
            for (int i = 0; i < notes.size(); i++) {
                if (notes.get(i).getChannel() != 9) {
                    noteId = (notes.get(i).getNote() + offSetDT) % 12;
                    if (noteId == 1 || noteId == 3 || noteId == 6 || noteId == 8 || noteId == 10) {
                        temp += 1;
                    }
                }
            }
            mapTrans.put(offSetDT, temp);
        }
        //Finding Key associated with max Value in a Java Map
        cTranspose = Collections.min(mapTrans.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private void findPieceLenght16() {

        int temp = 0;

        for (Note note : notes) {
            if (note.getPulse16() + note.getLenght16() > temp) {
                temp = note.getPulse16() + note.getLenght16();
            }
        }

        pieceLenght16 = temp;

    }


    private void initChords() {

        for (int i = 0; i < getPieceLenght16(); i++) {

            Chord chord = new Chord();
            chord.setChord(EChord.nothing);

            chords.add(chord);


        }

    }

    public int getResolution() {
        return resolution;
    }

    public void setcTranspose(int cTranspose) {
        this.cTranspose = cTranspose;
    }

    public float getBpm() {
        return bpm;
    }

    public int getPieceLenght16() {
        return  pieceLenght16;
    }

    public float getDivisionType() {
        return divisionType;
    }

    public String getName() {
        return name;
    }

    public int getcTranspose() {
        return cTranspose;
    }

   public class Chord {

        private EChord chord;

        public void setChord(EChord chord) {
            this.chord = chord;
        }

        public EChord getChord() {
            return chord;
        }
    }

   public class Note  {

        private int note;
        private int cNote;
        private boolean on;
        private int velocity;
        private long pulse;
        private int pulse16;
        private int channel;
        private int tracknumber;
        private long lenght;
        private int lenght16;
        private int octave;
        private String notename;
        private int instrument;
        private int index;
        private int chordAjuste;

        //PUBLIC Properties
        public int getLenght16() { return lenght16; }

        public int getPulse16() { return pulse16; }

        public int getCNote() {
            return cNote;
        }

        public int getVelocity() {
            return velocity;
        }

        public int getChannel() {
            return channel;
        }

        public boolean getOn() {
            return on;
        }

        public int getTracknumber() {
            return tracknumber;
        }

        public void setCNote(int note) { this.cNote = note; }

        //PRIVATE Properties

        private int getNote() {
            return note;
        }

        private void setVelocity(int velocity) {
            this.velocity = velocity;
        }

        private void setChannel(int channel) {
            this.channel = channel;
        }

        private void setTracknumber(int tracknumber) {
            this.tracknumber = tracknumber;
        }

        private void setOctave(int octave) {
            this.octave = octave;
        }

        private void setNotename(String notename) {
            this.notename = notename;
        }

        private long getPulse() {
            return pulse;
        }

        private void setPulse(long pulse) {this.pulse = pulse;}

        private void setLength(long length) {
            this.lenght = length;
        }

        private void setOn(boolean on) {
            this.on = on;
        }

        private void setIndex(int index) {
            this.index = index;
        }

        private void setNote(int note) {
            this.note = note;
        }

        private void setLenght16(long lenght) {

            if (((int)((lenght / ((double)getResolution()/4))) < 1)){
                this.lenght16= 1;
            }else {
                this.lenght16 = (int)((lenght/((double)getResolution()/4))+.5);
            }
        }

        private void setPulse16(double pulse) {
            this.pulse16 = (int)((pulse/((double)getResolution()/4))+.5);
        }

    }
}