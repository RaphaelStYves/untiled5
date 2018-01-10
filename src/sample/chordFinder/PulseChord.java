package sample.chordFinder;

import java.util.ArrayList;
import java.util.List;
/**
A pulse contain the
 */

public class PulseChord {

    private List<Double> scores;

    public void add(double score){
        if (scores == null)
        {
            scores = new ArrayList<>();
        }
        this.scores.add(score);
    }

    public List<Double> getScores() {
        return scores;
    }


}
