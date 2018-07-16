package karaoke.sound;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Music object representing multiple musics played simultaneously representing a chord
 */

public class Chord implements Music {
    
    private final List<Music> music;
    
    // Abstraction function:
    //    AF(music) = a Chord object that plays every Music object in music on top of each other for the duration of the first Music in the list
    // Rep invariant:
    //    music.size() > 0
    //    duration >= 0
    //    cannot contain rests or tuplets
    // Safety from rep exposure:
    //    All fields are private and final
    //    The mutable field notes is never returned in any methods, and thus is not exposed to the client
    //    Defensive copies are made for passed in and returned objects
    // Thread safety argument:
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private and final, so the field's reference is immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs 
    //       - music points to a mutable List object, but that List is encapsulated
    //         in this object, not shared with any other object or exposed to a client

    /**
     * Creates a new Chord object with the specified notes
     * @param music the different Music objects that will play over each other 
     */
    public Chord(List<Music> music) {
        this.music = Collections.unmodifiableList(music);
        checkRep();
    }
    
    /**
     * Checks the rep invariant
     */
    private void checkRep() {
        assert this.music.size() > 0;
        assert this.duration() >= 0;
        for(Music item : this.music) {
            if(item instanceof Rest || item instanceof Tuplet) {
                assert false;
            }
        }
    }
    
    @Override
    public double duration() {
        return this.music.get(0).duration();
    }

    @Override
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) throws InterruptedException {
        for (Music chord: this.music) {
           chord.play(player, atBeat, lyricStream, voice);
        }
    }

    @Override
    public Music transpose(int semitonesUp) {
        List<Music> newMusic = new ArrayList<>();
        for (Music chord: this.music) {
            newMusic.add(chord.transpose(semitonesUp));
        }
        checkRep();
        return new Component(newMusic);
    }
    
    @Override
    public String toString() {
        String result = "[";
        for (Music note : this.music) {
            result += note.toString();
        }
        checkRep();
        return result + "]";
    }

    @Override
    public String[] getVoices() {
        List<String> names = new ArrayList<>();
        for (Music comp: this.music) {
            if (comp.getVoices().length > 0) {
                for (String voice: comp.getVoices()) {
                    names.add(voice);
                }
            }
        }
        checkRep();
        return names.toArray(new String[names.size()]);
    }
    
    @Override
    public String getLyrics(String voice) {
        return "";
    }

}
