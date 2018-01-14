package sample.model;

import sample.model.Piece;

import java.util.ArrayList;

public class Pulse {

    private  ArrayList<Piece.Note> notes = new ArrayList<>();
    private int index;


    public void addNote(Piece.Note note){
        this.notes.add(note);
    }

    public ArrayList<Piece.Note> getNotes() {
        return notes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
