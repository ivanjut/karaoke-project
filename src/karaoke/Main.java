package karaoke;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.sound.Music;

/**
 * Main entry point of your application.
 */
public class Main {

    /**
     * Parses a given music file and displays instructions on how to access the karaoke player for this music 
     * 
     * @param args Array should include an abc music file to parse
     * Prints the title and composer (if any)
     * Prints instructions about how to view lyrics streams with a web browser
     * Prints instructions about how to start music playback
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        final Queue<String> arguments = new LinkedList<>(Arrays.asList(args));
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
