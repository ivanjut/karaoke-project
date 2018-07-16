package karaoke.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import edu.mit.eecs.parserlib.UnableToParseException;

/**
 * Manual tests on various example pieces
 * @category no_didit
 */
public class PieceParserTest {
    
    // TESTING STRATEGY:
    //
    // These will be manual tests that we run. We will follow along the abc file to make sure everything sounds as it should
    // We have slowed down the speed so that we could accurately and diligently check most of the notes, and have reset the speeds
    // here to reflect what we believe is an appropriate tempo.
    //
    // We test each of the sample pieces that we are given, as well as a few more we found or created ourselves.
    
    @Test
    public void testParsePiece1() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/piece1.abc"));
            SequencePlayer player = new MidiSequencePlayer(40, 64);
            music.play(player, 0, System.out, "");
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParsePiece2() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/piece2.abc"));
            SequencePlayer player = new MidiSequencePlayer(60, 64);
            music.play(player, 0, System.out, "");
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParsePiece3() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/piece3.abc"));
            SequencePlayer player = new MidiSequencePlayer(20, 64);
            music.play(player, 0, System.out, "" );
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseSample1() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/sample1.abc"));
            SequencePlayer player = new MidiSequencePlayer(20, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseSample2() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/sample2.abc"));
            SequencePlayer player = new MidiSequencePlayer(20, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseSample3() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/sample3.abc"));
            SequencePlayer player = new MidiSequencePlayer(20, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseAbcSong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/abc_song.abc"));
            SequencePlayer player = new MidiSequencePlayer(30, 64);
            music.play(player, 0, System.out, "default");

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseWaxiesDargleSong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/waxies_dargle.abc"));
            SequencePlayer player = new MidiSequencePlayer(80, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseScaleSong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/scale.abc"));
            SequencePlayer player = new MidiSequencePlayer(20, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParsePreludeSong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/prelude.abc"));
            SequencePlayer player = new MidiSequencePlayer(120, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseLittleNightMusicSong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/little_night_music.abc"));
            SequencePlayer player = new MidiSequencePlayer(40, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParsePaddySong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/paddy.abc"));
            SequencePlayer player = new MidiSequencePlayer(60, 64);
            music.play(player, 0, System.out, "" );
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseInventionSong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/invention.abc"));
            SequencePlayer player = new MidiSequencePlayer(20, 64);
            music.play(player, 0, System.out, "" );
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test
    public void testParseFurEliseSong() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/fur_elise.abc"));
            SequencePlayer player = new MidiSequencePlayer(150, 64);
            music.play(player, 0, System.out, "" );

            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testParseTurkishMarch() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/turkish_march.abc"));
            SequencePlayer player = new MidiSequencePlayer(50, 64);
            music.play(player, 0, System.out, "" );
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testParseParis() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/paris.abc"));
            SequencePlayer player = new MidiSequencePlayer(40, 64);
            music.play(player, 0, System.out, "" );
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testParseWatchOut() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/watch_out.abc"));
            SequencePlayer player = new MidiSequencePlayer(8, 64);
            music.play(player, 0, System.out, "2");
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testParseStarSpangledBanner() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/star_spangled_banner.abc"));
            SequencePlayer player = new MidiSequencePlayer(30, 64);
            music.play(player, 0, System.out, "" );
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testParsePayphone() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/payphone.abc"));
            SequencePlayer player = new MidiSequencePlayer(30, 64);
            music.play(player, 0, System.out, "" );
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testParseFriday() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException   {
        try {
            Music music = karaoke.sound.Music.parseFromFile(new File("sample-abc/friday.abc"));
            SequencePlayer player = new MidiSequencePlayer(30, 64);
            music.play(player, 0, System.out, "");
            Object lock = new Object();
            player.addEvent(music.duration(), (Double beat) -> {
                synchronized (lock) {
                    lock.notify();
                }
            });
            player.play();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Done!");
        } catch (UnableToParseException | IOException e) {
            System.err.println("failed to parse");
            e.printStackTrace();
        }
    }
}