package karaoke.parser;

import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.sound.Chord;
import karaoke.sound.Component;
import karaoke.sound.Concat;
import karaoke.sound.Header;
import karaoke.sound.Instrument;
import karaoke.sound.Lyric;
import karaoke.sound.Music;
import karaoke.sound.Note;
import karaoke.sound.Piece;
import karaoke.sound.Pitch;
import karaoke.sound.Rest;
import karaoke.sound.Tuplet;

/**
 * A parser for Music.
 */
public class MusicParser {
    
    private Header header;
    private Map<Character, Integer> accidentalMap = new HashMap<Character, Integer>();
     
    private static final int OCTAVE_LENGTH = 12;
    private static final double HALF = 0.5;
    
    // the nonterminals of the grammar
    private static enum MusicGrammar {
        ABC,
        ABC_HEADER,
        FIELD_NUMBER,
        FIELD_TITLE,
        OTHER_FIELDS,
        FIELD_COMPOSER,
        FIELD_DEFAULT_LENGTH,
        FIELD_METER,
        FIELD_TEMPO,
        FIELD_VOICE,
        FIELD_KEY,
        KEY,
        KEYNOTE,
        KEY_ACCIDENTAL,
        MODE_MINOR,
        MEASURE,
        METER,
        METER_FRACTION,
        TEMPO,
        START_REPEAT,
        FINISH_REPEAT,
        FIRST_REPEAT,
        SECOND_REPEAT,
        ABC_BODY,
        ABC_LINE,
        NOTE_ELEMENT,
        NOTE,
        PITCH,
        OCTAVE,
        NOTE_LENGTH,
        NOTE_LENGTH_STRICT,
        ACCIDENTAL,
        BASENOTE,
        REST_ELEMENT,
        TUPLET_ELEMENT,
        TUPLET_SPEC,
        CHORD,
        BARLINE,
        LYRIC,
        LYRICAL_ELEMENT,
        LYRIC_TEXT,
        BACKSLASH_HYPHEN,
        COMMENT,
        COMMENT_TEXT,
        END_OF_LINE,
        TEXT,
        DIGIT,
        NEWLINE,
        SPACE_OR_TAB, 
        MAJOR_BARLINE,
        NORMAL_BARLINE
    }
    
    /**
     * Creates a new MusicParser
     */
    public MusicParser() {
        accidentalMap.put('A', 0);
        accidentalMap.put('B', 0);
        accidentalMap.put('C', 0);
        accidentalMap.put('D', 0);
        accidentalMap.put('E', 0);
        accidentalMap.put('F', 0);
        accidentalMap.put('G', 0);
        accidentalMap.put('a', 0);
        accidentalMap.put('b', 0);
        accidentalMap.put('c', 0);
        accidentalMap.put('d', 0);
        accidentalMap.put('e', 0);
        accidentalMap.put('f', 0);
        accidentalMap.put('g', 0);
    }
    
    /**
     * Takes in the grammar from a file and creates a music object
     * @param file The file containing the valid abc notation to be parsed into music 
     * @return a music object corresponding to the grammar
     * @throws UnableToParseException if notation does not match grammar 
     * @throws IOException 
     */
    public Music parseFromFile(File file) throws UnableToParseException, IOException {
        Parser<MusicGrammar> parser = 
                Parser.compile(new File("src/karaoke/parser/Abc.g"),
                               MusicGrammar.ABC);
        ParseTree<MusicGrammar> parseTree = parser.parse(file);
        parseHeader(parseTree);
        setAccidentalMap();
        return parseBody(parseTree);
    }
    
    /**
     * Updates the accidental map according to the key signature
     * @throws UnableToParseException
     */
    private void setAccidentalMap() throws UnableToParseException {
        for (Character key : accidentalMap.keySet()) {
            accidentalMap.replace(key, 0);
        }
        switch (this.header.getKey().trim()) {
            case "C":
            {
               break;
            }
            case "Am":
            {
                break;
            }
            case "G":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                break;
            }
            case "Em":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                break;
            }
            case "F":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                break;
            }
            case "Dm":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                break;
            }
            case "D":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                break;
            }
            case "Bm":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                break;
            }
            case "Bb":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                break;
            }
            case "Gm":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                break;
            }
            case "A":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                break;
            }
            case "F#m":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                break;
            }
            case "Eb":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                break;
            }
            case "Cm":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                break;
            }
            case "E":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                break;
            }
            case "C#m":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                break;
            }
            case "Ab":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                break;
            }
            case "Fm":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                break;
            }
            case "B":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                accidentalMap.replace('A', 1);
                accidentalMap.replace('a', 1);
            }
            case "G#m":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                accidentalMap.replace('A', 1);
                accidentalMap.replace('a', 1);
                break;
            }
            case "Db":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                accidentalMap.replace('G', -1);
                accidentalMap.replace('g', -1);
                break;
            }
            case "Bbm":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                accidentalMap.replace('G', -1);
                accidentalMap.replace('g', -1);
                break;
            }
            case "F#":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                accidentalMap.replace('A', 1);
                accidentalMap.replace('a', 1);
                accidentalMap.replace('E', 1);
                accidentalMap.replace('e', 1);
                break;
            }
            case "D#m":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                accidentalMap.replace('A', 1);
                accidentalMap.replace('a', 1);
                accidentalMap.replace('E', 1);
                accidentalMap.replace('e', 1);
                break;
            }
            case "Gb":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                accidentalMap.replace('G', -1);
                accidentalMap.replace('g', -1);
                accidentalMap.replace('C', -1);
                accidentalMap.replace('c', -1);
                break;
            }
            case "Ebm":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                accidentalMap.replace('G', -1);
                accidentalMap.replace('g', -1);
                accidentalMap.replace('C', -1);
                accidentalMap.replace('c', -1);
                break;
            }
            case "C#":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                accidentalMap.replace('A', 1);
                accidentalMap.replace('a', 1);
                accidentalMap.replace('E', 1);
                accidentalMap.replace('e', 1);
                accidentalMap.replace('B', 1);
                accidentalMap.replace('b', 1);
                break;
            }
            case "A#m":
            {
                accidentalMap.replace('F', 1);
                accidentalMap.replace('f', 1);
                accidentalMap.replace('C', 1);
                accidentalMap.replace('c', 1);
                accidentalMap.replace('G', 1);
                accidentalMap.replace('g', 1);
                accidentalMap.replace('D', 1);
                accidentalMap.replace('d', 1);
                accidentalMap.replace('A', 1);
                accidentalMap.replace('a', 1);
                accidentalMap.replace('E', 1);
                accidentalMap.replace('e', 1);
                accidentalMap.replace('B', 1);
                accidentalMap.replace('b', 1);
                break;
            }
            case "Cb":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                accidentalMap.replace('G', -1);
                accidentalMap.replace('g', -1);
                accidentalMap.replace('C', -1);
                accidentalMap.replace('c', -1);
                accidentalMap.replace('F', -1);
                accidentalMap.replace('f', -1);
                break;
            }
            case "Abm":
            {
                accidentalMap.replace('B', -1);
                accidentalMap.replace('b', -1);
                accidentalMap.replace('E', -1);
                accidentalMap.replace('e', -1);
                accidentalMap.replace('A', -1);
                accidentalMap.replace('a', -1);
                accidentalMap.replace('D', -1);
                accidentalMap.replace('d', -1);
                accidentalMap.replace('G', -1);
                accidentalMap.replace('g', -1);
                accidentalMap.replace('C', -1);
                accidentalMap.replace('c', -1);
                accidentalMap.replace('F', -1);
                accidentalMap.replace('f', -1);
                break;
            }
            default:
            {
                throw new UnableToParseException("invalid key signature");
            }
        }
    }
    
    /**
     * Parses header from a parse tree into a Header object
     * @param parseTree a tree representing the music written according to the ABC grammar
     */
    public void parseHeader(ParseTree<MusicGrammar> parseTree) {
        
        final Map<Character, String> headerMap = new HashMap<>();

        final List<ParseTree<MusicGrammar>> children = parseTree.children().get(0).children();
            for (ParseTree<MusicGrammar> child : children) {
                Character fieldKey = child.text().charAt(0);
                String fieldVal = child.text().substring(2,child.text().length()-1).trim();
                if (headerMap.containsKey('V') && fieldKey.equals('V')) {
                    headerMap.put(fieldKey, headerMap.get(fieldKey) + "\n" + fieldVal);
                } else {
                    headerMap.put(fieldKey, fieldVal);
                }
            }
        headerMap.putIfAbsent('V', "default");
        this.header = new Header(headerMap);
        return;
    }
    
    /**
     * Convert a parse tree into an abstract syntax tree
     * 
     * @param parseTree a tree representing the music written according to the ABC grammar
     * @return a Music object which is an abstract syntax tree representing the given parse tree
     */
    public Music parseBody(ParseTree<MusicGrammar> parseTree) {   
        switch (parseTree.name()) {
            case ABC:
                {
                    final List<ParseTree<MusicGrammar>> children = parseTree.children();
                    return parseBody(children.get(1));
                } 
            case ABC_BODY:
                {
                    final List<ParseTree<MusicGrammar>> children = parseTree.children();
                    List<ParseTree<MusicGrammar>> noComments = children.stream().filter(c -> c.name() != MusicGrammar.COMMENT).collect(Collectors.toList());
                    Map<String, ArrayList<ParseTree<MusicGrammar>>> byVoice = organizeByVoice(noComments);
                    Map<String, Music> voiceToMusic = new HashMap<String, Music>();
                    for (String voice: byVoice.keySet()) {
                        List<ParseTree<MusicGrammar>> voiceElements = byVoice.get(voice);
                        List<ParseTree<MusicGrammar>> musicElements = new ArrayList<>();
                        List<ParseTree<MusicGrammar>> lyricElements = new ArrayList<>();
                        for (ParseTree<MusicGrammar> element : voiceElements) {
                            if (element.name() == MusicGrammar.LYRIC) {
                                lyricElements.addAll(element.children());
                            } else {
                                if (element.name().equals(MusicGrammar.MEASURE)) {
                                    musicElements.addAll(element.children());
                                } else {
                                    musicElements.add(element);
                                }
                            }
                        }
                        // if voice has no lyrics
                        if (lyricElements.size() == 0) {
                            // create list of indices of notes which allows for a one to one mapping between a syllable and an element (representing the index of the note in musicElements) in the list
                            List<Integer> indexListAfterRepeats = createNoteIndexListAfterRepeats(musicElements);
                            List<Music> musicNoRepeats = musicElements.stream().map(tree -> parseBody(tree)).collect(Collectors.toList());
                            List<Music> musicWithRepeats = indexListAfterRepeats.stream().map(index -> musicNoRepeats.get(index)).collect(Collectors.toList());
                            Music pieceMusic = musicWithRepeats.stream().filter(music -> music.duration() > 0).reduce(new Rest(0), (m1, m2) -> new Concat(m1, m2));
                            Component newComponent = new Component(Arrays.asList(pieceMusic));
                            voiceToMusic.put(voice, newComponent);
                        } else {
                            // create list of indices of notes which allows for a one to one mapping between a syllable and an element (representing the index of the note in musicElements) in the list
                            List<Integer> indexListAfterRepeats = createNoteIndexListAfterRepeats(musicElements);
                            List<Music> musicNoRepeats = musicElements.stream().map(tree -> parseBody(tree)).collect(Collectors.toList());
                            // lyric at index i in lyricsNoRepeats corresponds to note at index i in musicNoRepeats
                            List<String> lyricsNoRepeats = createAlignedLyricList(lyricElements, musicElements);
                            List<String> lyricsWithRepeats = indexListAfterRepeats.stream().map(index -> lyricsNoRepeats.get(index)).collect(Collectors.toList());
                            List<Music> musicWithRepeats = indexListAfterRepeats.stream().map(index -> musicNoRepeats.get(index)).collect(Collectors.toList());
                            List<Music> pieceLyricsList = new ArrayList<Music>();
                            int m = 0;
                            
                            while (m < lyricsWithRepeats.size()-1) {
     
                                double duration = musicWithRepeats.get(m).duration();
                                String lyric = lyricsWithRepeats.get(m);
                                
                                int underscoreCount = 1;
                                while (lyricsWithRepeats.get(m+underscoreCount).equals("_")) {
                                    duration = duration + musicWithRepeats.get(m+underscoreCount).duration();
                                    underscoreCount++;
                            }
                                
                            // expand left till newline
                            boolean searchingLeft = true;
                            String leftElement = "";
                            int l = m-1;
                            while (searchingLeft) {
                                if (l >= 0) {
                                    if (lyricsWithRepeats.get(l).equals("\n")) {
                                        break;
                                    } else {
                                        String lyricToAddLeft = lyricsWithRepeats.get(l).equals("_") ? " " : lyricsWithRepeats.get(l);
                                        leftElement = lyricToAddLeft + " " + leftElement;
                                    }
                                } else {
                                    break;
                                }
                                l--;
                            }
                                
                            // expand right till newline
                            boolean searchingRight = true;
                            String rightElement = "";
                            int r = m+1;
                            while (searchingRight) {
                                if (r < lyricsWithRepeats.size()) {
                                    if (lyricsWithRepeats.get(r).equals("\n")) {
                                        break;
                                    } else {
                                        String lyricToAddRight = lyricsWithRepeats.get(r).equals("_") ? " " : lyricsWithRepeats.get(r);
                                        rightElement = rightElement + " " + lyricToAddRight;
                                    }
                                } else {
                                    break;
                                }
                                r++;
                            }
                            if (lyric.equals(" ")) {
                                pieceLyricsList.add(new Lyric(lyric, duration));
                            } else if (lyric.equals("_") | lyric.equals("\n")) {
                                pieceLyricsList.add(new Lyric(" ", 0));
                            }
                            else {
                                pieceLyricsList.add(new Lyric(leftElement + " <mark>" + lyric + "</mark> " + rightElement, duration));
                            }
                               m++;
                            }
                            Music pieceLyric = pieceLyricsList.stream().reduce(new Rest(0), (m1, m2) -> new Concat(m1, m2));
                            Music pieceMusic = musicWithRepeats.stream().filter(music -> music.duration() > 0).reduce(new Rest(0), (m1, m2) -> new Concat(m1, m2));
                            voiceToMusic.put(voice, new Component(Arrays.asList(pieceLyric, pieceMusic)));
                        }
                    }
                    return new Piece(voiceToMusic, header);
                } 
            case ABC_LINE:
                {
                    List<ParseTree<MusicGrammar>> children = parseTree.children();
                    int musicLineEnd = 0;
                    int i = 0;
                    
                    for (ParseTree<MusicGrammar> element: children) {
                        if (element.name() == MusicGrammar.END_OF_LINE) {
                            musicLineEnd = i;
                            break;
                        }
                        i++;
                    }
                    return parseBody(parseTree.children().get(0));
                } 
            case MEASURE:
                {
                    Stream<Music> musicStream = (parseTree.children().stream().map(x -> parseBody(x)));
                    return musicStream.reduce(new Rest(0), (music1, music2) -> new Concat(music1, music2));
                }
            case NOTE_ELEMENT:
                {
                    return parseBody(parseTree.children().get(0));
                } 
            case NOTE:
                {
                    // dealing with noteLength
                    List<ParseTree<MusicGrammar>> children = parseTree.children();
                    final double relativeNoteLength = parseMultiplicativeFactor(children.get(1).text());
                    // dealing with pitch
                    List<ParseTree<MusicGrammar>> pitchChildren = children.get(0).children();
                    Pitch pitch = null;
                    Character baseNote = 'X';
                    if (pitchChildren.size() == 1) {
                        baseNote = pitchChildren.get(0).text().charAt(0);
                        pitch = new Pitch(Character.toUpperCase(baseNote));
                        int n = baseNote.equals(Character.toUpperCase(baseNote)) ? 0 : OCTAVE_LENGTH;
                        Pitch newPitch = pitch.transpose(accidentalMap.get(baseNote) + n);
                        return new Note(relativeNoteLength * header.getNoteLength(), newPitch, Instrument.PIANO);
                    } else {
                        int transposeBy = 0;
                        if (pitchChildren.get(0).name() == MusicGrammar.ACCIDENTAL) {
                            baseNote = pitchChildren.get(1).text().charAt(0);
                            switch (pitchChildren.get(0).text()) {
                                case "^":
                                    accidentalMap.put(baseNote, accidentalMap.get(baseNote)+1);
                                    break;
                                case "^^":
                                    accidentalMap.put(baseNote, accidentalMap.get(baseNote)+2);
                                    break;
                                case "_":
                                    accidentalMap.put(baseNote, accidentalMap.get(baseNote)-1);
                                    break;
                                case "__":
                                    accidentalMap.put(baseNote, accidentalMap.get(baseNote)-2);
                                    break;
                                case "=":
                                    accidentalMap.put(baseNote, 0);
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (pitchChildren.get(pitchChildren.size()-1).name() == MusicGrammar.OCTAVE) {
                            baseNote = pitchChildren.get(pitchChildren.size()-2).text().charAt(0);                            
                            final int octavesUpOrDown = pitchChildren.get(pitchChildren.size()-1).text().contains(",") ? -1 : 1;
                            final int numOctaves = pitchChildren.get(pitchChildren.size()-1).text().trim().length();
                            transposeBy += OCTAVE_LENGTH*numOctaves*octavesUpOrDown;
                        }
                        pitch = new Pitch(Character.toUpperCase(baseNote));
                        int n = baseNote.equals(Character.toUpperCase(baseNote)) ? 0 : OCTAVE_LENGTH;
                        transposeBy += n + accidentalMap.get(baseNote);
                        Pitch newPitch = pitch.transpose(transposeBy);
                        return new Note(relativeNoteLength * header.getNoteLength(), newPitch, Instrument.PIANO);
                    }
                } 
            case REST_ELEMENT:
                {
                    final double relativeRestLength = parseMultiplicativeFactor(parseTree.children().get(0).text());
                    return new Rest(relativeRestLength*header.getNoteLength());
                } 
            case TUPLET_ELEMENT:
                {
                    List<ParseTree<MusicGrammar>> tupletNotes = parseTree.children().subList(1, parseTree.children().size());
                    return new Tuplet(tupletNotes.stream().map(x -> parseBody(x)).collect(Collectors.toList()));
                } 
            case CHORD:
                {
                    List<Music> notes = new ArrayList<Music>();
                    List<ParseTree<MusicGrammar>> children = parseTree.children();
                    for (ParseTree<MusicGrammar> note : children) {
                        notes.add(parseBody(note));
                    }
                    return new Chord(notes);
                } 
            case BARLINE:
                {
                    try {
                        setAccidentalMap();    
                    } catch (UnableToParseException e) {
                        System.err.println("invalid key signature");
                        e.printStackTrace();
                    }
                    return new Rest(0);
                } 
            default: 
            {
                return new Rest(0);
            }
        }
    }
    
    private static double parseMultiplicativeFactor(String factorText) {
        double relativeNoteLength = 1;
        factorText = factorText.trim();
        if (!factorText.equals("")) {
            if (factorText.contains("/")) {
                final String[] noteLength = factorText.split("/");

                if (noteLength.length == 0) {
                    relativeNoteLength = HALF;
                } else if (noteLength.length == 1) {
                    if (factorText.startsWith("/")) {
                        relativeNoteLength = 1.0 / Integer.parseInt(noteLength[0]);
                    } else {
                        relativeNoteLength = Integer.parseInt(noteLength[0]) / 2.0;
                    }
                } else {
                    final double lengthNumerator = noteLength[0].equals("") ? 1 : Integer.parseInt(noteLength[0]);
                    final double lengthDenominator = noteLength[1].equals("") ? 2 : Integer.parseInt(noteLength[1]);
                    relativeNoteLength = lengthNumerator / lengthDenominator;           
                }
            } else {
                relativeNoteLength = Integer.parseInt(factorText);
            }
        }
        return relativeNoteLength;
    }
    
    private static List<Integer> fromAtoB(int a, int b) {
        List<Integer> indexList = new ArrayList<Integer>();
        for (int i = a ; i < b; i++) {
            indexList.add(i);
        }
        return indexList;
    }
    
    private static List<Integer> createNoteIndexListAfterRepeats(List<ParseTree<MusicGrammar>> musicElements) {
        List<Integer> indexListAfterRepeats = new ArrayList<Integer>();
        int startRepeat = 0;
        int finishRepeat = -1;
        int firstRepeat = -1;
        boolean differentEnding = false;
        boolean startRepeatCompleted = false;
        for (int i = 0; i < musicElements.size(); i++) {
            indexListAfterRepeats.add(i);
            ParseTree<MusicGrammar> element = musicElements.get(i);
            if (element.name() == MusicGrammar.START_REPEAT) {
                startRepeat = i;
                startRepeatCompleted = false;
            }
            else if (element.name() == MusicGrammar.FINISH_REPEAT) {
                finishRepeat = i;
                startRepeatCompleted = true;
                if (!differentEnding) {
                    indexListAfterRepeats.addAll(fromAtoB(startRepeat+1,finishRepeat));
                } else {
                    indexListAfterRepeats.addAll(fromAtoB(startRepeat,firstRepeat));
                    differentEnding = false;
                }
            } 
            else if (element.name() == MusicGrammar.FIRST_REPEAT) {
                differentEnding = true;
                firstRepeat = i;
            } 
            else if (element.name() == MusicGrammar.BARLINE) {
                if (element.children().get(0).name() == MusicGrammar.MAJOR_BARLINE) { //Looking at the name of the barline at the end of a bar to see if it denotes the end of a major section
                    startRepeat = startRepeatCompleted ? i : startRepeat;
                }
            }
        }
        return indexListAfterRepeats;
    }
    
    private List<String> createAlignedLyricList(List<ParseTree<MusicGrammar>> lyricElements, List<ParseTree<MusicGrammar>> musicElements) {
        List<String> alignedLyricList = new ArrayList<String>();
        int i = 0;
        while (i < lyricElements.size()) {
            ParseTree<MusicGrammar> element = lyricElements.get(i);
            if (musicElements.size() == alignedLyricList.size()) {
                return alignedLyricList;
            } else if (alignedLyricList.size() < musicElements.size()) {    
                //case for when next music element is  a barline/repeat symbol
                if (parseBody(musicElements.get(alignedLyricList.size())).duration() == 0.0) {
                    alignedLyricList.add(" ");
                }
            }
            switch (element.text()) {
                case "-":
                    {
                        if (lyricElements.get(lyricElements.size()-1).text().equals(" ") || lyricElements.get(lyricElements.size()-1).text().equals("-")) {
                            alignedLyricList.add(" ");
                        }
                        break;
                    }
                case " ":
                    {
                        break;
                    }
                case "_":
                    {
                        alignedLyricList.add("_");
                        break;
                    }
                case "*":
                    {
                        alignedLyricList.add(" ");
                        break;
                    }
                case "~":
                    {   
                        i++;
                        while (lyricElements.get(i).text().equals(" ")) {
                            i++;
                        }
                        if (alignedLyricList.get(alignedLyricList.size()-1).equals(" ")) {
                            alignedLyricList.set(alignedLyricList.size()-2, alignedLyricList.get(alignedLyricList.size()-2) + lyricElements.get(i).text());
                        } else {
                            alignedLyricList.set(alignedLyricList.size()-1, alignedLyricList.get(alignedLyricList.size()-1) + lyricElements.get(i).text());
                        }
                        break;
                    }
                case "\\-":
                    {
                        i++;
                        while (lyricElements.get(i).text().equals(" ")) {
                            i++;
                        }
                        alignedLyricList.set(alignedLyricList.size()-1, alignedLyricList.get(alignedLyricList.size()-1) + "-" + lyricElements.get(i).text());
                        break;
                    }
                case "|":
                    {
                        while (musicElements.get(alignedLyricList.size()-1).name() == MusicGrammar.NOTE_ELEMENT) {
                            alignedLyricList.add(" ");
                        }
                        break;
                    }
                case "\n":
                    {
                        alignedLyricList.add("\n");
                        break;
                    }
                default:
                    {
                        alignedLyricList.add(element.text());
                        break;
                    }
            }
            i++;
        }
        
        while (alignedLyricList.size() < musicElements.size()) {    
            //case for when next music element is  a barline/repeat symbol
            alignedLyricList.add(" ");
        }
        // adds empty lyrics to the end if neccessary so that alignedList is aligned with musicElements
        if (alignedLyricList.size() < musicElements.size()) {
            if (parseBody(musicElements.get(alignedLyricList.size())).duration() == 0.0) {
                alignedLyricList.add(" ");
            }
        }
        return alignedLyricList;
    }
    
    private static Map<String, ArrayList<ParseTree<MusicGrammar>>> organizeByVoice(List<ParseTree<MusicGrammar>> body) {

        Map<String, ArrayList<ParseTree<MusicGrammar>>> byVoice = new HashMap<String, ArrayList<ParseTree<MusicGrammar>>>(); // Maps the name of a voice to the parseTrees holding the music associated with it.
        String voice = "default";
        for (int c = 0; c < body.size(); c++) { //Divide up the lines into a voice followed by the music lines corresponding to it. 
            ParseTree<MusicGrammar> line = body.get(c);
            if (line.children().size() > 0) {
                ParseTree<MusicGrammar> firstElement = line.children().get(0);
                if (firstElement.name() == MusicGrammar.FIELD_VOICE) {
                    voice = firstElement.text().substring(2, firstElement.text().length()).trim();
                } else {
                    ArrayList<ParseTree<MusicGrammar>> value = byVoice.getOrDefault(voice, new ArrayList<ParseTree<MusicGrammar>>());
                    value.addAll(line.children().subList(0, line.children().size()));
                    byVoice.put(voice, value);
                }
            }
        }
        return byVoice;
    }
   
}