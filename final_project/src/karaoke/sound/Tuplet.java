package karaoke.sound;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Music object representing multiple music objects played in succession
 */

public class Tuplet implements Music {

    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    
    private final List<Music> notes;
    
    // Abstraction function:
    //    AF(notes) = A music object of two, three, or four notes or chords played consecutively for a duration dependent on the number of notes
    //       Duplet: 2 notes in the time of 3 notes
    //       Triplet: 3 notes in the time of 2 notes
    //       Quadruplet: 4 notes in the time of 3 notes
    // Rep invariant:
    //    all music objects in notes must be either Note or Chords objects
    //    duration >= 0
    //    2 <= notes.size() <= 4
    // Safety from rep exposure:
    //    All fields are private, final, and immutable
    //    Defensive copies are made for all mutable types passed in or returned
    // Thread safety argument:
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private and final, so the field's reference is immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs 
    //       - notes points to a mutable List object, but that List is encapsulated
    //         in this object, not shared with any other object or exposed to a client
        
    /**
     * Creates a new Tuplet object
     * @param notes a list of music objects that the tuplet will play in order from left to right
     */
    public Tuplet(List<Music> notes) {
        this.notes = Collections.unmodifiableList(notes);
        checkRep();
    }
    
    /**
     * Checks the rep invariant
     */
    private void checkRep() {
        assert this.duration() >= 0;
        for (Music item : this.notes) {
            if (!(item instanceof Note || item instanceof Chord)) {
                assert false;
            }
        }
        assert notes.size() >= TWO && notes.size() <= FOUR;
    }
    
    @Override
    public double duration() {
        switch(notes.size()) {
            case TWO:
                {
                    return notes.get(0).duration() * THREE;
                }
            case THREE:
                {
                    return notes.get(0).duration() * TWO;
                }
            case FOUR:
                {
                    return notes.get(0).duration() * THREE;
                }
            default:
                {
                    throw new RuntimeException("Should not get here");
                }
        }
    }

    @Override
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) throws InterruptedException {
        double length = this.duration() / this.notes.size();
        for (Music music: this.notes) {
            music.play(player, atBeat, lyricStream, voice);
            atBeat += length;
        }
    }

    @Override
    public Music transpose(int semitonesUp) {
        List<Music> newNotes = new ArrayList<>();
        for (Music note : this.notes) {
            newNotes.add(note.transpose(semitonesUp));
        }
        checkRep();
        return new Tuplet(newNotes);
    }
    
    @Override 
    public String toString() {
        String result = "(" + this.notes.size();
        for (Music note : this.notes) {
            result += note.toString();
        }
        checkRep();
        return result + this.duration();
    }
    
    @Override
    public String[] getVoices() {
        checkRep();
        return new String[0];
    }

    @Override
    public String getLyrics(String voice) {
        return "";
    }
}
