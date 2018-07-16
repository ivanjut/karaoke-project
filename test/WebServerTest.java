package karaoke;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.Test;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.sound.Music;

/**
 * Tests for WebServer
 * @category no_didit
 */
public class WebServerTest {
    
    // Testing strategy:
    //
    // In testing the web server, we will be using manual tests to check if the outputs for the client are correct, and the music is playing on the server
    //
    // Partition the cases as follows:
    //    # of clients: 1, multiple
    //    # of voices: 1, multiple
    //    lyrics: song with lyrics, song without lyrics
    //    
    // Tests will fully cover each of the above cases
    // 
    // Manually testing the web server involves running the web server, streaming the lyrics in either one client or for multiple clients
    // depending on the test. In each test, we make sure each lyric is timed and synchronized properly and also make sure that the correct
    // music is being played in the server. 
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test 
    public void testOneClientOneVoice() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/piece1.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test
    public void testMultClientsOneVoice() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/piece1.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test
    public void testMultClientsMultVoices() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/watch_out.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWithLyrics() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/piece3.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWithoutLyrics() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/fur_elise.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServer2() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/piece2.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServer3() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/piece3.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServerABC() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/abc_song.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServerFriday() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/Friday.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServerInvention() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/invention.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServerLittleNightMusic() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/little_night_music.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServerSSB() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/star_spangled_banner.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
    
    @Test 
    public void testWebServerWaxiesDargle() throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList("4444", "sample-abc/waxies_dargle.abc"));
        final int port;
        final Music music;
        
        try {
            port = Integer.parseInt(arguments.remove());
            System.out.println("Port: " + port);
            final String filename = arguments.remove().trim();
            System.out.println("fileName: " + filename);
            music = karaoke.sound.Music.parseFromFile(new File(filename));
            WebServer webServer = new WebServer(music, port);
            webServer.startPlayback();
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("missing or invalid PORT", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException ("Could not parse file");
        }
    }
}