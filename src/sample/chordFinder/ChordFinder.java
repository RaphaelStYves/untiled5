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

/**
 Definition : in a Beats we have 5 things.
 Structure ex: 16,16
 32
 8,8,8,8,8
 /////////////////////////////
 beat and beats ex:
 D **8**( D 8,Cm 16,Dm 4,Am 4)
 G **4**( G 4,G 16,Am 8,Am 4)
 A **16**( D 16,Cm 16)
 beat properties :
 core;
 bestIndex;
 startPulse;
 numberOfPulse;
 ///////////////////////////////
 **seq** and seqs :
 D **8**( D 8,Cm 16,Dm 4,Am 4)
 (only the best Seq is in seq)**seq** and seqs ex:
 seq properties :
 score;
 bestIndex;
 startPulse;
 numberOfPulse;
 ///////////////////////////////
 **pulse** and pulses :
 C **1**(1,1,1,1,1,1,1)
 Cm**1**(1,1,1,1,1,1,1)
 D **1**(1,1,1,1,1,1,1)
 pulse properties :
 score;
 bestIndex;
 */

public class ChordFinder {

    private Piece piece;

     Song bestBeatsForBestChordForAll32;

    public List<Pulse>  scoreOfEachPulseOfAllChords;

    private  List<String> refTableNameChords;
    private List<List<Double>> refTableForceChords;
    private List<List<Integer>> refTableBeatsChords;
    private Map<String, EChord> refTableNameToEchord;

    private int[][] pieceMOD12;

    public  ChordFinder(Piece piece) {
        this.piece = piece;
    }

    public List<String> findChords()  {

        //load Compare Data Tables to analyse Mod 12 piece.
        loadCompareDataTables();

//        //find voice track.
//        VoiceFinder voiceFinder = new VoiceFinder(piece);
//       trackvoice = voiceFinder.findVoice();

        //Load Piece in a Mod 12 piece.
        //for analyse

        pieceMOD12 = loadPieceInOnesInArray();

        // Step 1 ) Find each scores for all 50 chords (chordForce.csv)
        findScoreOfAllPulseAndAllChords();

        //Find the best combination all 50 chords and chordBeat.csv for each section of 32 16ime.
        bestBeatsForBestChordForAll32 = new Song();
        bestBeatsForBestChordForAll32= findBestScoreForEach32();

        List<Integer> listOfIndexChords = new ArrayList<>();
        listOfIndexChords = findListOfIndexChords(listOfIndexChords);

        List<String> listOfNameChords = new ArrayList<>();
        listOfNameChords = changeIndexToNameChord(listOfIndexChords, listOfNameChords);



        return listOfNameChords;

    }


    private List<String> changeIndexToNameChord(List<Integer> listOfIndexChords, List<String> listOfNameChords){

        for (int i = 0; i < listOfIndexChords.size() ; i++) {

            listOfNameChords.add(refTableNameChords.get(listOfIndexChords.get(i)));

        }

        return listOfNameChords;
    }

    private List<Integer> findListOfIndexChords(List<Integer> listOfIndexChords) {

        for (int i = 0; i < bestBeatsForBestChordForAll32.size(); i += 32) {

            PartOfSong partOfSong = bestBeatsForBestChordForAll32.get(i);
            int bestBeatIndex = partOfSong.getBestIndexBeat();
            Beat beat = partOfSong.get(bestBeatIndex);


            int numberofPulses = 0;
            int bestindex = 0;
            for (int j = 0; j < beat.size() ; j++) {
                PartOfBeat partOfBeat = beat.get(j);

                bestindex = partOfBeat.getBestIndex();
               numberofPulses = partOfBeat.getNumberOfPulses();

            }

            for (int j = 0; j < numberofPulses ; j++) {
                listOfIndexChords.add(bestindex);
            }
        }

        return listOfIndexChords;
    }




    private Song findBestScoreForEach32() {

        int blockOf32 = (((piece.getPieceLenght16()/32))*32);

        for (int i = 0; i < blockOf32; i += 32) {

            bestBeatsForBestChordForAll32.add(scoreOfAllBeatsAndItsBestsChords(i));
        }

        return bestBeatsForBestChordForAll32;
    }

    private PartOfSong scoreOfAllBeatsAndItsBestsChords(int startPulse)  {

        PartOfSong scoreOfAllBeatsAndItsBestsChords = new PartOfSong();

        for (int indexBeat = 0; indexBeat < refTableBeatsChords.size() ; indexBeat++) {//for each beats

            //this contain One Beat and it's score.
            scoreOfAllBeatsAndItsBestsChords.add(allPartOfOneBeat(indexBeat, startPulse));
        }

        //sum of all best score for all Beat
        scoreOfAllBeatsAndItsBestsChords.findAndSetBestIndexBeat();

        return scoreOfAllBeatsAndItsBestsChords;

    }



    private Beat allPartOfOneBeat(int indexBeat, int startPulse)  {

        Beat allPartOfOneBeatAndItsBestsChords = new Beat();
        int pulseDone =0;
        for (int i = 0; i < refTableBeatsChords.get(indexBeat).size() ; i++) { //for each seq of beat
            int numberOfPulse = refTableBeatsChords.get(indexBeat).get(i);
            allPartOfOneBeatAndItsBestsChords.add(scoreOfOnePartOfOneBeatForALLChords(startPulse + pulseDone, numberOfPulse));
            pulseDone += numberOfPulse;
        }

        allPartOfOneBeatAndItsBestsChords.setBestScore();

        return allPartOfOneBeatAndItsBestsChords;
    }

    private PartOfBeat scoreOfOnePartOfOneBeatForALLChords(int startPulse, int numberOfPulse)  {

        PartOfBeat scoreOfOnePartOfOneBeatForALLChords = new PartOfBeat();
        for (int indexChord = 0; indexChord < refTableNameChords.size() ; indexChord++) {//each force
            //addOfXPulsesForOneChord and add to the forceSeqs
            scoreOfOnePartOfOneBeatForALLChords.add(scoreOfOnePartOfOneBeatForOneChord(startPulse,numberOfPulse, indexChord));
        }
        scoreOfOnePartOfOneBeatForALLChords.findMaxScoreAndSetTheIndexChord();
        scoreOfOnePartOfOneBeatForALLChords.setNumberOfPulses(numberOfPulse);

        return scoreOfOnePartOfOneBeatForALLChords;
    }

    private double scoreOfOnePartOfOneBeatForOneChord(int startPulse,int numberOfPulse, int indexChord)  {
        double scoreOfOnePartOfOneBeatForOneChord =0;
        for (int k = 0; k < numberOfPulse ; k++) {
            scoreOfOnePartOfOneBeatForOneChord += scoreOfEachPulseOfAllChords.get(startPulse + k).getScores().get(indexChord);
        }
        return scoreOfOnePartOfOneBeatForOneChord;
    }

    public List<Pulse> findScoreOfAllPulseAndAllChords()  {

        scoreOfEachPulseOfAllChords = new ArrayList<>();

          for (int pulse = 0; pulse < pieceMOD12.length ; pulse++) {

            scoreOfEachPulseOfAllChords.add(findScoreOfOnePulseAllChords(pulse));
        }

        return scoreOfEachPulseOfAllChords;
    }

    private Pulse findScoreOfOnePulseAllChords(int indexPulse)  {

        //importe Table force chords for analyse.
        try {
            getMapofChordsForce("C:\\Users\\Raphael\\IdeaProjects\\untitled5\\src\\ChordForce.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //start analyse.
        Pulse scoreOfOnePulseForAllChords = new Pulse();
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
            if (note.getChannel() != 9 ) {
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

    private void loadCompareDataTables() {

        try {
            getMapofChordBeat("C:\\Users\\Raphael\\IdeaProjects\\untitled5\\src\\ChordBeat.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getMapofChordStringToEChord("C:\\Users\\Raphael\\IdeaProjects\\untitled5\\src\\ChordStringToDegre.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMapofChordsForce(String filePath) throws IOException {
        BufferedReader br;
        String line;

        refTableNameChords = new ArrayList<>();
        refTableForceChords = new ArrayList<>();

        br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] chord = line.split(";");
            refTableNameChords.add(chord[0]);

            List<Double> forceChord = new ArrayList<>();
            for (int i = 1; i < chord.length; i++) {
                forceChord.add(Double.parseDouble(chord[i]));
              }

            refTableForceChords.add(forceChord);
        }
    }

    public void getMapofChordBeat(String filePath) throws IOException {
        BufferedReader br;
        String line;

        refTableBeatsChords = new ArrayList<>();
        List<Integer> beats;

        br = new BufferedReader(new FileReader(filePath));
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

    private void getMapofChordStringToEChord(String filePath) throws IOException {
        BufferedReader br;
        String line;

        refTableNameToEchord = new HashMap<>();


        br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] beat = line.split(";");

            refTableNameToEchord.put(beat[0],EChord.valueOf(beat[1]));
        }
    }


}



