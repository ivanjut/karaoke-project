package karaoke.sound;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import edu.mit.eecs.parserlib.UnableToParseException;

/**
 * Tests for parsing and AST
 */
public class MusicParserTest {
    
    // Testing Strategy: we will first test automatically each variant for proper parsing, then progressively larger pieces
    // 
    // Partition the variants as follows:
    //    Note: # of notes -> 1, > 1
    //    Rest: a single rest
    //    Concat: concatenation of two lines
    //    Chord: # of chords -> 1, > 1
    //    Tuplet: # of tuplets -> 1, > 1
    //    Repeat: same ending, different ending
    //    Voice: # voices -> 1, > 1
    //    Lyric: lyrics are tested within these tests
    //    Component: a component of chords
    //
    // After sufficient testing of the variants, we will test each of the sample pieces manually to make sure everything is complete
    // 
    // Partition the pieces as follows:
    //     piece1, piece2, piece3, sample1, sample2, sample3, abc_song, fur_elise, invention, little_night_musician, paddy, prelude, scale, waxies_dargle 
    //
    // These tests will not be tested on Didit, and as such as located in a separate file PieceParserTest.java
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TESTS ON VARIANTS
    
    // covers: note --> 1
    @Test public void testParseNoteOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/note.abc"));
            String expected = "X:1T:Note pieceC:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0 <mark>hi</mark>   0.1875 0.0z0.0C0.1875}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: note --> > 1
    @Test public void testParseNoteMoreThanOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/note_2.abc"));
            String expected = "X:2T:Note piece 2C:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0C0.0625D0.0625E0.25^C'0.0625}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: rest --> 1 rest
    @Test public void testParseRest() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/rest.abc"));
            String expected = "X:3T:Rest pieceC:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0z0.25}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: concat --> concatenation of two lines
    @Test public void testParseConcat() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/concat.abc"));
            String expected = "X:4T:Concat pieceC:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0C0.25C0.25C0.25C0.25D0.25D0.25D0.25D0.25}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: chord --> 1
    @Test public void testParseChordOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/chord.abc"));

            String expected = "X:5T:Chord pieceC:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0[G0.25B0.25G'0.25]}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: chord --> > 1
    @Test public void testParseChordMoreThanOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/chord_2.abc"));
            String expected = "X:5T:Chord piece 2C:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0[G0.25B0.25G'0.25][C'0.25E'0.25][D'0.25A'0.25B0.25][G0.25B0.25G'0.25C0.25]}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: tuplet --> 1
    @Test public void testParseTupletOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/tuplet.abc"));
            String expected = "X:6T:Tuplet pieceC:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0(3F0.25^F0.25G0.250.5}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: tuplet --> > 1
    @Test public void testParseTupletMoreThanOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/tuplet_2.abc"));
            String expected = "X:7T:Tuplet piece 2C:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0(3F0.25^F0.25G0.250.5(4A0.25B0.25C'0.25D'0.250.75}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: repeat --> same ending
    @Test public void testParseRepeatSame() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/repeat.abc"));
            String expected = "X:8T:Repeat piece 1C:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0C0.25D0.25E0.25F0.25G0.25A0.25B0.25C'0.25C0.25D0.25E0.25F0.25G0.25A0.25B0.25C'0.25}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: repeat --> different ending
    @Test public void testParseRepeatDifferent() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/repeat_2.abc"));
            String expected = "X:9T:Repeat piece 2C:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0C0.25D0.25E0.25F0.25G0.25A0.25B0.25C'0.25G0.25A0.25B0.25B0.25C0.25D0.25E0.25F0.25F0.25E0.25D0.25C0.25}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: voice --> 1
    @Test public void testParseVoiceOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/voice.abc"));
            String expected = "X:10T:Voice pieceC:UnknownM:1.0L:0.25V:[1]K:C\n" + "1: {z0.0C0.25D0.25E0.25F0.25}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: voice --> > 1
    @Test public void testParseVoiceMoreThanOne() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/voice_2.abc"));
            String expected = "X:11T:Voice piece 2C:UnknownM:1.0L:0.25V:[1, 2]K:C\n" + "1: {z0.0C0.25D0.25E0.25F0.25}\n" + "2: {z0.0C'0.25D'0.25E'0.25F'0.25}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    // covers: component --> chords
    @Test public void testParseComponentChords() throws MidiUnavailableException, InvalidMidiDataException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/component.abc"));
            String expected = "X:12T:Component pieceC:UnknownM:1.0L:0.25V:[default]K:C\n" + "default: {z0.0[C0.25E0.25G0.25][D0.25^F0.25A0.25][E0.25^G0.25B0.25][F0.25A0.25C'0.25][G0.25B0.25D'0.25][A0.25^C'0.25E'0.25][B0.25^D'0.25^F'0.25][C'0.25E'0.25G'0.25]}\n";
            assertEquals(expected, music.toString());
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
}
