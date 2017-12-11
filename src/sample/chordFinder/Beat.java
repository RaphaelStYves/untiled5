package sample.chordFinder;

import java.util.ArrayList;
import java.util.List;

public class Beat {

    private List<PartOfBeat> allPartOfBeat;

    private double bestScoreOfAllPartOfBeat;



    public void add(PartOfBeat allPartOfOneBeatAndItsBestsChords){
        if (allPartOfBeat == null)
        {
            allPartOfBeat = new ArrayList<>();
        }
        this.allPartOfBeat.add(allPartOfOneBeatAndItsBestsChords);
    }

    public void setBestScore()  {

        for (int i = 0; i < allPartOfBeat.size() ; i++) {

            bestScoreOfAllPartOfBeat += allPartOfBeat.get(i).getBestScore();
        }

    }

    public PartOfBeat get(int i){
     return  allPartOfBeat.get(i);
    }

    public int size(){
        return allPartOfBeat.size();
    }

    public double getBestScoreOfAllPartOfBeat() {
        return bestScoreOfAllPartOfBeat;
    }
}

