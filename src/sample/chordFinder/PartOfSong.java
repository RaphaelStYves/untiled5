package sample.chordFinder;

import java.util.ArrayList;
import java.util.List;

public class PartOfSong {

    private List<Beat> partOfSong;

    private double bestScoreBeat;
    private int bestIndexBeat;


    public void add(Beat beat) {
        if (partOfSong == null) {
            partOfSong = new ArrayList<>();
        }
        this.partOfSong.add(beat);
    }

    public void findAndSetBestIndexBeat() {

        for (int i = 0; i < this.partOfSong.size(); i++) {
            Beat beat = partOfSong.get(i);
            if (beat.getBestScoreOfAllPartOfBeat() > bestScoreBeat) {
                bestScoreBeat = beat.getBestScoreOfAllPartOfBeat();
                bestIndexBeat = i;

            }

        }
    }

    public Beat get(int i){
       return partOfSong.get(i);
    }

    public int getBestIndexBeat() {
        return bestIndexBeat;
    }

}
