package karaoke.sound;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.parser.MusicParser;

/**
 * An interface to represent music as a collection of notes.
 * Immutable and thread safe data type.
 */

public interface Music {
    
    // ADT Definition:
    //
    // Music = Note(duration:Double, pitch:Pitch, instrument:Instrument) + 
    //         Rest(duration:Double) + 
    //         Chord(notes:List<Note>) + 
    //         Tuplet(notes:List<Music>) + 
    //         Concat(m1:Music, m2:Music) +
    //         Piece(music:Music, lyrics:Music, header:Map) +
    //         Lyric(lyric:String, duration:Double) +
    //         Component(music:List<Music>, hasLyrics:Boolean)
    
    /**
     * Takes in the grammar from a file and creates a music object
     * @param file The file containing the valid abc notation to be parsed into music 
     * @return a music object corresponding to the grammar
     * @throws UnableToParseException if notation does not match grammar 
     * @throws IOException 
     */
    public static Music parseFromFile(File file) throws UnableToParseException, IOException {
        MusicParser musicParser = new MusicParser();
        return musicParser.parseFromFile(file);
    }
    
    /**
     * Returns the time it takes to play the entire music
     * @return the length of the music in seconds
     */
    public double duration();
    
    /**
     * Plays the music object
     * @param player player object to play music through
     * @param atBeat the time at which to play the music
     * @param lyricStream the output stream for lyrics
     * @param voice the voice that is specified
     * @throws InterruptedException 
     */
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) throws InterruptedException;
    
    /**
     * Raises or lowers the pitch of the music by the specified amount
     * @param semitonesUp the number specifying how many semitones to raise the pitch (negative value is equivalent to lowering the pitch)
     * @return a new music identical to the old music but with the transpose applied
     */
    public Music transpose(int semitonesUp);

    /**
     * Returns the voice label of this music if it possesses one.
     * @return A String array containing one or more voices included in this music.
     */
    public String[] getVoices();
    
    /**
     * @param voice the given voice consisted in a music
     * @return lyrics the lyrics associated with that voice in this music
     */
    public String getLyrics(String voice);
}

