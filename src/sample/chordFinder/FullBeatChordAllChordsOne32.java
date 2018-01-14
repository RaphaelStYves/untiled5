package sample.chordFinder;

import java.util.HashMap;
import java.util.Map;

public class FullBeatChordAllChordsOne32 {

    private Map<Integer,PartOfOneBeatChordAllChordsOne32> fullBeatChordAllChordsOne32Map;
    private int numberOfPulse;
    private double score;
    private int startPulse;
    private int bestIndexChord;
    private double scoreOfTheBestKey;
    private int indexBeat;


    public void put(int indexPartOfBeat,PartOfOneBeatChordAllChordsOne32 partOfOneBeatChordAllChordsOne32){
        if (fullBeatChordAllChordsOne32Map == null)
        {
            fullBeatChordAllChordsOne32Map = new HashMap<>();
        }
        this.fullBeatChordAllChordsOne32Map.put(indexPartOfBeat,partOfOneBeatChordAllChordsOne32);
    }

    public void setIndexBeat(int indexBeat) {
        indexBeat = indexBeat;
    }

    public void findTheBestIndex() {
        bestIndexChord = fullBeatChordAllChordsOne32Map.entrySet().stream().max((entry1, entry2) -> entry1.getValue().getScore() > entry2.getValue().getScore() ? 1 : -1).get().getKey();
    }

    public double getScore() {
        return score;
    }

    public void setScore() {

       for (int i = 0; i < fullBeatChordAllChordsOne32Map.size(); i++) {
            score += fullBeatChordAllChordsOne32Map.get(i).getScore();
        }

       double numbOfPartOfBeat =fullBeatChordAllChordsOne32Map.size();
       double ajusting =  1+((9-numbOfPartOfBeat)/100);


        score = score * ajusting;
    }


    public Map<Integer, PartOfOneBeatChordAllChordsOne32> getFullBeatChordAllChordsOne32Map() {
        return fullBeatChordAllChordsOne32Map;
    }
}
