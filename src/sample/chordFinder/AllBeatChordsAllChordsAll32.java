package sample.chordFinder;

import java.util.HashMap;
import java.util.Map;

public class AllBeatChordsAllChordsAll32 {

    private Map<Integer,AllBeatChordsAllChordsOne32> allBeatChordsAllChordsAll32;

    public void put(int indexBeat,AllBeatChordsAllChordsOne32 allBeatChordsAllChordsOne32){

        if (allBeatChordsAllChordsAll32 == null)
        {
            allBeatChordsAllChordsAll32 = new HashMap<>();
        }
        this.allBeatChordsAllChordsAll32.put(indexBeat,allBeatChordsAllChordsOne32);
    }

    public Map<Integer, AllBeatChordsAllChordsOne32> getAllBeatChordsAllChordsAll32() {
        return allBeatChordsAllChordsAll32;
    }
}
