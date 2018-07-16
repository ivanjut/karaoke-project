package karaoke.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * Tests for warm-up pieces
 * @category no_didit
 */
public class SequencePlayerTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testPiece1() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 140; // a beat is a quarter note, so this is 140 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 0;
        double numBeats = 1;
        player.addNote(piano, new Pitch('C'), startBeat, numBeats);
        startBeat += numBeats;
        player.addNote(piano, new Pitch('C'), startBeat, numBeats);
        startBeat += numBeats;
        player.addNote(piano, new Pitch('C'), startBeat, 0.75);
        startBeat += 0.75;
        player.addNote(piano, new Pitch('D'), startBeat, 0.25);
        startBeat += 0.25;
        player.addNote(piano, new Pitch('E'), startBeat++, numBeats);
        player.addNote(piano, new Pitch('E'), startBeat, 0.75);
        startBeat += 0.75;
        player.addNote(piano, new Pitch('D'), startBeat, 0.25);
        startBeat += 0.25;
        player.addNote(piano, new Pitch('E'), startBeat, 0.75);
        startBeat += 0.75;
        player.addNote(piano, new Pitch('F'), startBeat, 0.25);
        startBeat += 0.25;
        player.addNote(piano, new Pitch('G'), startBeat, 2);
        startBeat += 2;
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('G'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('G'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('G'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('E'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('E'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('E'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('C'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('C'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('C'), startBeat, (double) 1/3);
        startBeat += (double) 1/3;
        player.addNote(piano, new Pitch('G'), startBeat, 0.75);
        startBeat += 0.75;
        player.addNote(piano, new Pitch('F'), startBeat, 0.25);
        startBeat += 0.25;
        player.addNote(piano, new Pitch('E'), startBeat, 0.75);
        startBeat += 0.75;
        player.addNote(piano, new Pitch('D'), startBeat, 0.25);
        startBeat += 0.25;
        player.addNote(piano, new Pitch('C'), startBeat, 2);
        startBeat += 2;
        
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        
        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
    }
    
    @Test public void testPiece2() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 200; // a beat is a quarter note, so this is 200 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 0;
        double numBeats = 1;
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, 0.5);
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, 0.5);
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += numBeats;
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, 0.5);
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += numBeats;
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, 0.5);
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, numBeats);
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, numBeats);
        startBeat += numBeats;
        
        player.addNote(piano, new Pitch('G'), startBeat, numBeats);
        player.addNote(piano, new Pitch('B'), startBeat, numBeats);
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat, numBeats);
        startBeat += 2;
        player.addNote(piano, new Pitch('G'), startBeat, numBeats);
        startBeat += 2;
        
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, 1.5);
        startBeat += 1.5;
        player.addNote(piano, new Pitch('G'), startBeat, 0.5);
        startBeat += 1.5;
        player.addNote(piano, new Pitch('E'), startBeat, numBeats);
        startBeat += numBeats;

        player.addNote(piano, new Pitch('E'), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('A'), startBeat, numBeats);
        startBeat += numBeats;
        player.addNote(piano, new Pitch('B'), startBeat, numBeats);
        startBeat += numBeats;  
        player.addNote(piano, new Pitch('B').transpose(-1), startBeat, 0.5);
        startBeat += 0.5;   
        player.addNote(piano, new Pitch('A'), startBeat, numBeats);
        startBeat += numBeats;
        
        player.addNote(piano, new Pitch('G'), startBeat, (double) 2/3);
        startBeat += (double) 2/3;
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, (double) 2/3);
        startBeat += (double) 2/3;
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat, (double) 2/3);
        startBeat += (double) 2/3;
        player.addNote(piano, new Pitch('A').transpose(Pitch.OCTAVE), startBeat, numBeats);
        startBeat += numBeats;
        player.addNote(piano, new Pitch('F').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += numBeats;
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, numBeats);
        startBeat += numBeats;
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('D').transpose(Pitch.OCTAVE), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('B'), startBeat, 0.75);
        startBeat += 1.5;

        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        
        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
    }
    
    @Test public void testPiece3() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 50; // a beat is a quarter note, so this is 50 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 2;
        double numBeats = 1;
        player.addNote(piano, new Pitch('D'), startBeat, numBeats);
        player.addEvent(startBeat, (start) -> System.out.print("A - "));
        startBeat += numBeats;
        player.addNote(piano, new Pitch('G'), startBeat, 2);
        player.addEvent(startBeat, (start) -> System.out.print("ma - "));
        startBeat += 2;
        player.addNote(piano, new Pitch('B'), startBeat, 0.5);
        player.addEvent(startBeat, (start) -> System.out.print("zing "));
        startBeat += 0.5;
        player.addNote(piano, new Pitch('G'), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('B'), startBeat, 2);
        player.addEvent(startBeat, (start) -> System.out.print("grace! "));
        startBeat += 2;
        player.addNote(piano, new Pitch('A'), startBeat, numBeats);
        player.addEvent(startBeat, (start) -> System.out.print("How "));
        startBeat += numBeats;
        player.addNote(piano, new Pitch('G'), startBeat, 2);
        player.addEvent(startBeat, (start) -> System.out.print("sweet "));
        startBeat += 2;
        player.addNote(piano, new Pitch('E'), startBeat, numBeats);
        player.addEvent(startBeat, (start) -> System.out.print("the "));
        startBeat += numBeats;
        player.addNote(piano, new Pitch('D'), startBeat, 2);
        player.addEvent(startBeat, (start) -> System.out.print("sound "));
        startBeat += 2;
        player.addNote(piano, new Pitch('D'), startBeat, numBeats);
        player.addEvent(startBeat, (start) -> System.out.print("That "));
        startBeat += numBeats;
        player.addNote(piano, new Pitch('G'), startBeat, 2);
        player.addEvent(startBeat, (start) -> System.out.print("saved "));
        startBeat += 2;
        player.addNote(piano, new Pitch('B'), startBeat, 0.5);
        player.addEvent(startBeat, (start) -> System.out.print("a "));
        startBeat += 0.5;
        player.addNote(piano, new Pitch('G'), startBeat, 0.5);
        startBeat += 0.5;
        player.addNote(piano, new Pitch('B'), startBeat, 2);
        player.addEvent(startBeat, (start) -> System.out.print("wretch "));
        startBeat += 2;
        player.addNote(piano, new Pitch('A'), startBeat, numBeats);
        player.addEvent(startBeat, (start) -> System.out.print("like "));
        startBeat += numBeats;
        player.addNote(piano, new Pitch('D').transpose(Pitch.OCTAVE), startBeat, 3);
        player.addEvent(startBeat, (start) -> System.out.println("me."));
        startBeat += 3;
        
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        
        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
    }
    
}
