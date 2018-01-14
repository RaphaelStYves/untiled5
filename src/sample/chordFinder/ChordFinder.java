package sample.chordFinder;



import sample.model.EChord;
import sample.model.Piece;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

public class ChordFinder {

    private Piece piece;

    private  List<EChord> refTableEChords;
    private List<List<Double>> refTableForceChords;
    private List<List<Integer>> refTableBeatsChords;

    public List<PulseChord>  scoreOfEachPulseOfAllChords = new ArrayList<>();;

    private int[][] pieceMOD12;

    public  ChordFinder(Piece piece) throws IOException {
        this.piece = piece;

        getMapofChordBeat();
        getMapofChordsForce();
        findScoreOfAllPulseAndAllChords();

        findAllBeatChordsAllChordsAll32();
    }

    public AllBeatChordsAllChordsAll32 findAllBeatChordsAllChordsAll32() {

            AllBeatChordsAllChordsAll32 allBeatChordsAllChordsAll32 = new AllBeatChordsAllChordsAll32();

        for (int blockOf32 = 0; blockOf32 < (((piece.getPieceLenght16()/32))*32); blockOf32 += 32) {

            allBeatChordsAllChordsAll32.put(blockOf32,findAllBeatChordsAllChordsOne32(blockOf32));
        }

        return allBeatChordsAllChordsAll32;
    }

    public AllBeatChordsAllChordsOne32 findAllBeatChordsAllChordsOne32(int startPulse)  {

        AllBeatChordsAllChordsOne32 allBeatChordsAllChordsOne32 = new AllBeatChordsAllChordsOne32();

        for (int indexBeat = 0; indexBeat < refTableBeatsChords.size() ; indexBeat++) {//for each beats

            allBeatChordsAllChordsOne32.put(indexBeat,findFullBeatChordAllChordsOne32(indexBeat, startPulse));
        }
            allBeatChordsAllChordsOne32.findTheBestIndex();
            allBeatChordsAllChordsOne32.setStartPulse(startPulse);

        return allBeatChordsAllChordsOne32;

    }

    public FullBeatChordAllChordsOne32 findFullBeatChordAllChordsOne32(int indexBeat, int startPulse)  {

         FullBeatChordAllChordsOne32 fullBeatChordAllChordsOne32 = new FullBeatChordAllChordsOne32();
        int pulseDone =0;
        for (int indexPartOfBeat = 0; indexPartOfBeat < refTableBeatsChords.get(indexBeat).size() ; indexPartOfBeat++) { //for each seq of beat
            int numberOfPulse = refTableBeatsChords.get(indexBeat).get(indexPartOfBeat);
            fullBeatChordAllChordsOne32.put(indexPartOfBeat,findPartOfOneBeatChordAllChordsOne32(startPulse + pulseDone, numberOfPulse));
            pulseDone += numberOfPulse;
        }

        fullBeatChordAllChordsOne32.setScore();
        fullBeatChordAllChordsOne32.setIndexBeat(indexBeat);

        return fullBeatChordAllChordsOne32;
    }

    public PartOfOneBeatChordAllChordsOne32 findPartOfOneBeatChordAllChordsOne32(int startPulse, int numberOfPulse)  {

        PartOfOneBeatChordAllChordsOne32 partOfOneBeatChordAllChordsOne32 = new PartOfOneBeatChordAllChordsOne32();

        for (int indexChord = 0; indexChord < refTableForceChords.size() ; indexChord++) {//each force

            partOfOneBeatChordAllChordsOne32.put(indexChord,findPartOfOneBeatChordOneChordOne32(startPulse,numberOfPulse, indexChord));
        }

        partOfOneBeatChordAllChordsOne32.findTheBestIndex();
        partOfOneBeatChordAllChordsOne32.setNumberOfPulse(numberOfPulse);
        partOfOneBeatChordAllChordsOne32.setRefTableEChords(refTableEChords);

        return partOfOneBeatChordAllChordsOne32;
    }

    public PartOfOneBeatChordOneChordOne32 findPartOfOneBeatChordOneChordOne32(int startPulse,int numberOfPulse, int indexChord)  {

        PartOfOneBeatChordOneChordOne32 partOfOneBeatChordOneChordOne32 = new PartOfOneBeatChordOneChordOne32();

        double temp = 0;
        for (int indexPulse = 0; indexPulse < numberOfPulse ; indexPulse++) {
            temp += scoreOfEachPulseOfAllChords.get(startPulse + indexPulse).getScores().get(indexChord);
        }
        partOfOneBeatChordOneChordOne32.setScore(temp);
        partOfOneBeatChordOneChordOne32.setIndexeChord(indexChord);

        return partOfOneBeatChordOneChordOne32;
    }

    public List<PulseChord> findScoreOfAllPulseAndAllChords()  {

        //load MOD12
        pieceMOD12 = loadPieceInOnesInArray();

        scoreOfEachPulseOfAllChords = new ArrayList<>();

       for (int pulseChord = 0; pulseChord < pieceMOD12.length ; pulseChord++) {

            scoreOfEachPulseOfAllChords.add(findScoreOfOnePulseAllChords(pulseChord));
        }

        return scoreOfEachPulseOfAllChords;
    }

    private PulseChord findScoreOfOnePulseAllChords(int indexPulse)  {


        //start analyse.
        PulseChord scoreOfOnePulseForAllChords = new PulseChord();
        for (int i = 0; i < refTableForceChords.size() ; i++) {
            scoreOfOnePulseForAllChords.add(multiplyForceByNoteForOneChord(indexPulse,i));
        }
        return scoreOfOnePulseForAllChords;
    }

    private double multiplyForceByNoteForOneChord(int indexPulse,int indexChord)  {
        double[] colonne16 =   new double [12];
        for (int k = 0; k < pieceMOD12[0].length; k++) {
            colonne16[k]=(pieceMOD12[indexPulse][k] * refTableForceChords.get(indexChord).get(k));
        }
        return DoubleStream.of(colonne16).sum();

    }

    public int[][] loadPieceInOnesInArray() {
        pieceMOD12 = new int[ piece.getPieceLenght16()][12];
        //mettre le song sous forme de MOD12 pour en faire l'analyse'
        // ne pas prendre en compte les channels  9 , le drum, il ne doit pas etre concidéré dans l'analyse des accords
        for (Piece.Note note : piece.notes) {
            if (note.getChannel() != 16 ) {
                for (int j = 0; j < note.getLenght16(); j++) {
                    pieceMOD12[note.getPulse16() + j][note.getCNote() % 12] = 1;
                }
            }
        }
        fillEmpty16ieme();
        return pieceMOD12;
    }

    private void fillEmpty16ieme() {
        //Remplir les trous d'accords du type aucune note dans 1/16 de temps. Dans ce cas repété les 1 du dernier 1/16.
        // //Ceci permettra de mettre de l,avant une meilleur logique d'évaluation des accords.
        if (!hasfirstColonneEmpty(pieceMOD12)) {
            pieceMOD12[0][0] = 1;
        }

        for (int i = 0; i < piece.getPieceLenght16(); i++) {
            int tempo = 0;
            for (int j = 0; j < 12; j++) {
                tempo = +pieceMOD12[i][j];
                if (tempo != 0) {
                    break;
                }
            }
            if (tempo == 0) {
                for (int n = 0; n < 12; n++) {
                    pieceMOD12[i][n] = pieceMOD12[i - 1][n];
                }
            }
        }
    }

    private boolean hasfirstColonneEmpty(int[][] arraymod12notes) {

        int tempo;
        for (int i = 0; i < 12; i++) {
            tempo = arraymod12notes[0][i];
            if (tempo != 0) {
                return true;
            }
        }
        return false;
    }

    public void getMapofChordsForce() throws IOException {
        BufferedReader br;
        String line;

        refTableEChords = new ArrayList<>();
        refTableForceChords = new ArrayList<>();

        br = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource("ChordForce3.csv").getPath()));
        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] chord = line.split(";");
            refTableEChords.add(EChord.valueOf(chord[0]));

            List<Double> forceChord = new ArrayList<>();
            for (int i = 1; i < chord.length; i++) {
                forceChord.add(Double.parseDouble(chord[i]));
              }

            refTableForceChords.add(forceChord);
        }
    }

    public void getMapofChordBeat() throws IOException {
        BufferedReader br;
        String line;

        refTableBeatsChords = new ArrayList<>();
        List<Integer> beats;
        br = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource("ChordBeat.csv").getPath()));

        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] beat = line.split(";");


            beats = new ArrayList<>();
            for (int i = 0; i < beat.length ; i++) {
                if (Integer.parseInt(beat[i]) > 0) {

                    beats.add(Integer.parseInt(beat[i]));
                }
            }
            refTableBeatsChords.add(beats);
        }
    }
}



