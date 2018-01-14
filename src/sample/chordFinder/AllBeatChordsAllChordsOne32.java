package sample.chordFinder;

import java.util.HashMap;
import java.util.Map;

public class AllBeatChordsAllChordsOne32 {

    private Map<Integer,FullBeatChordAllChordsOne32> allBeatChordsAllChordsOne32Map;
    private int bestIndexChord;
    private int startPulse;
    private int score;

    public void put(int indexBeat,FullBeatChordAllChordsOne32 fullBeatChordAllChordsOne32){

        if (allBeatChordsAllChordsOne32Map == null)
        {
            allBeatChordsAllChordsOne32Map = new HashMap<>();
        }
        this.allBeatChordsAllChordsOne32Map.put(indexBeat,fullBeatChordAllChordsOne32);
    }

    public void findTheBestIndex() {
        bestIndexChord = allBeatChordsAllChordsOne32Map.entrySet().stream().max((entry1, entry2) -> entry1.getValue().getScore() > entry2.getValue().getScore() ? 1 : -1).get().getKey();
    }

    public double getScore() {
        return allBeatChordsAllChordsOne32Map.get(bestIndexChord).getScore();
    }


    public void setStartPulse(int startPulse) {
        this.startPulse = startPulse;
    }

    public Map<Integer, FullBeatChordAllChordsOne32> getAllBeatChordsAllChordsOne32Map() {
        return allBeatChordsAllChordsOne32Map;
    }

    public int getBestIndexChord() {
        return bestIndexChord;
    }
}
