package sample.VoiceFinder;

import sample.model.Piece;

import java.util.ArrayList;
import java.util.List;

public class TrackFinderVoice {

    private int channel;
    private int trackNumber;
    private int numberOfNotes;
    private double score;
    private int sumOfpulses32;
    private int[] pulses;

    public double getScore() {
        return score;
    }

    public int[] getPulses() {
        return pulses;
    }

    public int getNumberOfNotes() {
        return numberOfNotes;
    }

    public void setNumberOfNotes(int numberOfNotes) {
        this.numberOfNotes = numberOfNotes;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setPulses(int[] pulses) { this.pulses = pulses; }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getChannel() {
        return channel;
    }

    public void setSumOfpulses32(int sumOfpulses32) {
        this.sumOfpulses32 = sumOfpulses32;
    }

    public int getSumOfpulses32() {
        return sumOfpulses32;
    }


}
