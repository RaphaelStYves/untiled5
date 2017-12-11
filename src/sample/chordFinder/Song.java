package sample.chordFinder;

import java.util.ArrayList;
import java.util.List;

public class Song {

    private List<PartOfSong> song;



    public void add(PartOfSong partOfSong) {
        if (song == null) {
            song = new ArrayList<>();
        }
        this.song.add(partOfSong);
    }

    public int size(){

        return song.size();
    }

    public PartOfSong get(int i){
        return song.get(i);
    }
}
