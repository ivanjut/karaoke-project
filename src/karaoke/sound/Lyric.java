package karaoke.sound;


import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Music Object that only holds lyrics 
 */

public class Lyric implements Music {

    private final String lyric;
    private final double duration;

    // Abstraction Function:
    //    AF(lyric, duration) = A Lyric object that represents a lyric for a certain duration of beats
    // Rep Invariant:
    //    duration > 0
    //    lyric != ""
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
     * 
     * @param lyric the lyric syllable
     * @param beats the duration of the syllable in s
     */
    public Lyric(String lyric, double beats) {
        this.lyric = lyric;
        this.duration = beats;
        checkRep();
    }
    
    /**
     * Checks the rep invariant
     */
    private void checkRep() {
        assert this.duration() >= 0;
        assert this.lyric.length() > 0;
    }
    
    @Override
    public double duration() {
        return this.duration;
    }

    @Override
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) {
        if (!voice.isEmpty()) {
            PrintWriter lyricOut = new PrintWriter(new OutputStreamWriter(lyricStream, UTF_8), true);
            player.addEvent(atBeat, x -> {
                if(!this.lyric.isEmpty()  && !this.lyric.equals(" ")) {
                    lyricOut.write(this.lyric + "<br>");
                    lyricOut.flush();
                }
            });
        }
    }

    @Override
    public Music transpose(int semitonesUp) {
        checkRep();
        return new Lyric(this.lyric, this.duration);
    }
    
    @Override
    public String toString() {
        return this.lyric + this.duration();
    }
    
    @Override
    public String[] getVoices() {
        checkRep();
        return new String[0];
    }
    
    @Override
    public String getLyrics(String voice) {
        return this.lyric;
    }

}
