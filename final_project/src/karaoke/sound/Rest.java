package karaoke.sound;

import java.io.OutputStream;

/**
 * Music object representing a single rest in music or a time when no sound is played 
 */

public class Rest implements Music {

    private final double duration;
    
    // Abstraction function:
    //    AF(duration) = a Rest object that plays nothing for the given amount of time duration
    // Rep invariant:
    //    duration >= 0
    // Safety from rep exposure:
    //    All fields are private, final, and immutable
    //    All objects passed in to constructor are immutable 
    // Thread safety argument:
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private, final, and immutable, so both the field's object type and reference is immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs
        
    /**
     * Creates a new rest object
     * @param duration How long the rest will play for 
     */
    public Rest(double duration) {
        this.duration = duration;
        checkRep();
    }
    
    /**
     * Checks the rep invariant
     */
    private void checkRep() {
        assert this.duration() >= 0;
    }
    
    @Override
    public double duration() {
        return this.duration;
    }

    @Override
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) {}

    @Override
    public Music transpose(int semitonesUp) {
        checkRep();
        return new Rest(this.duration);
    }
    
    @Override
    public String toString() {
        checkRep();
        return "z" + this.duration();
    }
    
    @Override
    public boolean equals(Object that) {
        return that instanceof Rest & sameVal((Rest)that);
    }
    
    private boolean sameVal(Rest that) {
        return this.duration == that.duration;
    }
    
    @Override
    public int hashCode() {
        return (int) this.duration();
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
