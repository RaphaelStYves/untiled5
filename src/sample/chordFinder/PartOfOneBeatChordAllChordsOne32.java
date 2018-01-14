package sample.chordFinder;

import sample.model.EChord;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartOfOneBeatChordAllChordsOne32 {

    private  List<EChord> refTableEChords;

    private Map<Integer,PartOfOneBeatChordOneChordOne32> partOfOneBeatChordAllChordsOne32Map;
    private int numberOfPulse;
    private double score;
    private int bestIndexChord;
    private EChord bestEChord;

    public void put(int indexChord,PartOfOneBeatChordOneChordOne32 partOfOneBeatChordOneChordOne32){
        if (partOfOneBeatChordAllChordsOne32Map == null)
        {
            partOfOneBeatChordAllChordsOne32Map = new HashMap<>();
        }
        this.partOfOneBeatChordAllChordsOne32Map.put(indexChord,partOfOneBeatChordOneChordOne32);
    }

    public void setNumberOfPulse(int numberOfPulse) {
        this.numberOfPulse = numberOfPulse;
    }

    public void findTheBestIndex() {
        bestIndexChord = partOfOneBeatChordAllChordsOne32Map.entrySet().stream().max((entry1, entry2) -> entry1.getValue().getScore() > entry2.getValue().getScore() ? 1 : -1).get().getKey();
    }

    public double getScore() {
        return partOfOneBeatChordAllChordsOne32Map.get(bestIndexChord).getScore();
    }

    public Map<Integer, PartOfOneBeatChordOneChordOne32> getPartOfOneBeatChordAllChordsOne32Map() {
        return partOfOneBeatChordAllChordsOne32Map;
    }

    public int getBestIndexChord() {
        return bestIndexChord;
    }

    public void setRefTableEChords(List<EChord> refTableEChords) {
        this.refTableEChords = refTableEChords;
    }

    public EChord getBestEChord() {
        return refTableEChords.get(bestIndexChord);
    }

    public int getNumberOfPulse() {
        return numberOfPulse;
    }
}
