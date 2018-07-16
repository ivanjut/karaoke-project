package karaoke.sound;

import java.io.OutputStream;

/**
 * Music object describing a single note in the music
 */

public class Note implements Music {
    
    private final double duration;
    private final Pitch pitch;
    private final Instrument instrument;
    
    // Abstraction function:
    //    AF(duration, pitch, instrument) = A single note of music that will play the specified pitch for the duration specified using the given instrument
    // Rep invariant:
    //    duration >= 0
    // Safety from rep exposure:
    //    All fields private final and immutable
    //    No methods return mutable references to the rep
    // Thread safety argument:
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private, final, and immutable, so both the fields' object types and references are immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs 
        
    /**
     * Creates a new Note object
     * @param duration the time in seconds that the note will play for
     * @param pitch pitch that the note plays
     * @param instrument instrument used to play note
     */
    public Note(double duration, Pitch pitch, Instrument instrument) {
        this.duration = duration;
        this.pitch = pitch;
        this.instrument = instrument;
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
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) {
        player.addNote(this.instrument, this.pitch, atBeat, this.duration);
    }

    @Override
    public Music transpose(int semitonesUp) {
        Pitch newPitch = this.pitch.transpose(semitonesUp);
        checkRep();
        return new Note(this.duration, newPitch, this.instrument);
    }
    
    @Override
    public String toString() {
        checkRep();
        return this.pitch.toString() + this.duration();
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
