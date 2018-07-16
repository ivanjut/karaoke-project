package karaoke.sound;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Component implements Music {

private final List<Music> music;
private final boolean hasLyrics;
    
    // Abstraction function:
    //    AF(music, hasLyrics) = a Component object that plays every Music object in music on top of each other for the duration of the first Music in the list, hasLyrics is true if there are lyrics
    // Rep invariant:
    //    music.size() > 0
    //    duration >= 0
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
    public Component(List<Music> music) {
        this.music = Collections.unmodifiableList(music);
        this.hasLyrics = this.music.size() != 1;
        checkRep();
    }
    
    /**
     * Checks the rep invariant
     */
    private void checkRep() {
        assert this.music.size() > 0;
        assert this.duration() >= 0;
    }
    
    @Override
    public double duration() {
        return this.music.get(0).duration();
    }

    @Override
    public void play(SequencePlayer player, double atBeat, OutputStream lyricStream, String voice) throws InterruptedException {
        for (Music component: this.music) {
            component.play(player, atBeat, lyricStream, voice);
        }
    }

    @Override
    public Music transpose(int semitonesUp) {
        List<Music> newMusic = new ArrayList<>();
        for (Music component: this.music) {
            newMusic.add(component.transpose(semitonesUp));
        }
        checkRep();
        return new Component(newMusic);
    }
    
    @Override
    public String toString() {
        String result = "{";
        for (Music note : this.music) {
            result += note.toString();
        }
        checkRep();
        return result + "}";
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
        String lyrics = "";
        for (Music element: this.music) {
            lyrics += element.getLyrics(voice);
        }
        return lyrics;
    }
    
    /**
     * @return true if the component consists of lyrics, false otherwise
     */
    public boolean containsLyrics() {
        return this.hasLyrics;
    }

}
