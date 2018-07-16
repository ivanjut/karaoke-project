package karaoke.sound;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an entire musical piece with instruments and lyrics. Contains all info about the piece
 */

public class Piece implements Music {

    private final Map<String, Music> voiceToMusic;
    private final Header header;
    
    // Abstraction function: 
    //    AF(music, header) = a new Music that stores all the information from the given header in a Header object, and has a map voiceToMusic that maps unique voices to the musics they're associated with
    // Rep invariant:
    //    duration >= 0
    // Safety from rep exposure:
    //    All fields private and final
    //    Defensive copy of map is created and never altered.
    // Thread safety argument:
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private and final, so the fields' references are immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs 
    //       - music points to an immutable object
    //       - header points to a mutable Map object, but that Map is encapsulated
    //         in this object, not shared with any other object or exposed to a client
    
    /**
     * Creates a new piece
     * @param voiceToMusic is a dictionary mapping each voice to its music
     * @param header A map holding the information from the header of a music file
     */
    public Piece(Map<String, Music> voiceToMusic, Header header) {
        this.voiceToMusic = new HashMap<String, Music>(voiceToMusic);
        this.header = header;
        checkRep();
    }
    
    /**
     * Checks the rep invariant
     */
    private void checkRep() {
        assert this.duration() >= 0;
    }
    
    /**
     * @return a Header object containing the information for this piece of music
     */
    public Header getHeader() {
        return this.header;
    }
    
    @Override
    public double duration() {
        return voiceToMusic.values().stream().map(music -> music.duration()).reduce(0.0, (a, b) -> Math.max(a, b));
    }

    @Override
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) throws InterruptedException {
        
        // IMPORTANT: some web browsers don't start displaying a page until at least 2K bytes
        // have been received.  So we'll send a line containing 2K spaces first.
        PrintWriter out = new PrintWriter(new OutputStreamWriter(lyricStream, UTF_8), true);
        final int enoughBytesToStartStreaming = 2048;
        for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
            out.print(' ');
        }
        out.flush();
        if (!voice.isEmpty()) {
            voiceToMusic.get(voice).play(player, atBeat, lyricStream, voice);
           
        } else {
            for (Music music : voiceToMusic.values()) {
                music.play(player, atBeat, lyricStream, voice);
            }
        }
    }

    @Override
    public Music transpose(int semitonesUp) {
        checkRep();
        Map<String, Music> newVoiceToMusic = new HashMap<String, Music>(voiceToMusic);
        for (String key : newVoiceToMusic.keySet()) {
            newVoiceToMusic.put(key, voiceToMusic.get(key).transpose(semitonesUp));
        }
        checkRep();
        return new Piece (newVoiceToMusic, header);
    }

    @Override
    public String toString() {
        String result = "";
        String headerString = "X:" + this.header.getIndex() + "T:" + this.header.getTitle() + "C:" + this.header.getComposer() + "M:" + this.header.getMeter() + "L:" + this.header.getNoteLength() + "V:" + this.header.getVoices() + "K:" + this.header.getKey() + '\n'; 
        result += headerString;
        for (String voice : voiceToMusic.keySet()) {
            String bodyString = voice + ": " + voiceToMusic.get(voice) + "\n";
            result += bodyString;
        }
        checkRep();
        return result;
    }
    
    @Override
    public String[] getVoices() {
        checkRep();
        return this.header.getVoices().toArray(new String[this.header.getVoices().size()]);
    }
    
    @Override
    public String getLyrics(String voice) {
        return voiceToMusic.get(voice).getLyrics(voice);
    }

}
