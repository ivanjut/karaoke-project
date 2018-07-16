package karaoke.sound;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A Music object representing a concatenation of other Music objects
 */

public class Concat implements Music {
    
    private final Music music1;
    private final Music music2;
    
    // Abstraction function:
    //    AF(music1, music2) = a new Concat object that plays music1 then music2 in succession for a duration of music1.duration() + music2.duration()
    // Rep invariant:
    //    duration >= 0
    // Safety from rep exposure:
    //    All fields are private, final, and immutable
    // Thread safety argument:
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private, final, and immutable, so both the fields' object types and references are immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs 

    /**
     * Creates a new Concat object 
     * @param music1 The music object that will be the first part of the new Concat
     * @param music2 The music object that will be the second part of the new Concat
     */
    public Concat(Music music1, Music music2) {
        this.music1 = music1;
        this.music2 = music2;
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
        return music1.duration() + music2.duration();
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) throws InterruptedException {
        this.music1.play(player, atBeat, lyricStream, voice);
        this.music2.play(player, atBeat + this.music1.duration(), lyricStream, voice);
    }

    @Override
    public Music transpose(int semitonesUp) {
        checkRep();
        return new Concat(this.music1.transpose(semitonesUp), this.music2.transpose(semitonesUp));
    }

    @Override 
    public String toString() {
        checkRep();
        return this.music1.toString() + this.music2.toString();
    }
    
    @Override
    public String[] getVoices() {
        List<String> voices = new ArrayList<>();
        for (String voice: music1.getVoices()) {
            voices.add(voice);
        }
        for (String voice: music2.getVoices()) {
            voices.add(voice);
        }
        checkRep();
        return voices.toArray(new String[voices.size()]);
    }

    @Override
    public String getLyrics(String voice) {
        return this.music1.getLyrics(voice) + this.music2.getLyrics(voice);
    }
    
}
