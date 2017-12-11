package sample.chordFinder;

import java.util.ArrayList;
import java.util.List;

public class PartOfBeat {

    private List<Double> scores;

    private double bestScore;
    private int bestIndex;
    private int numberOfPulses;

    public void add(double score){
        if (scores == null)
        {
            scores = new ArrayList<>();
        }
        this.scores.add(score);
    }

    public double getBestScore() {
        return bestScore;
    }

    public void findMaxScoreAndSetTheIndexChord()  {


        for (int i = 0; i < this.scores.size(); i++ )
        {
            if ( this.scores.get(i) > bestScore )
            {
                this.bestScore = this.scores.get(i);
                this.bestIndex = i;
            }
        }
    }

    public int getBestIndex() {
        return bestIndex;
    }


    public void setNumberOfPulses(int numberOfPulses) {
        this.numberOfPulses = numberOfPulses;
    }

    public int getNumberOfPulses() {
        return numberOfPulses;
    }
}


