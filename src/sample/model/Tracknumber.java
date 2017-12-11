package sample.model;

import sample.model.Piece;

import java.util.ArrayList;

public class Tracknumber {
    private ArrayList<Piece.Note> notes = new ArrayList<>();

    public void addNote(Piece.Note note){
        this.notes.add(note);
    }

    public ArrayList<Piece.Note> getNotes() {
        return notes;
    }
}
