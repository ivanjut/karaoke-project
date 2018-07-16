package karaoke;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.Executors;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import karaoke.sound.MidiSequencePlayer;
import karaoke.sound.Music;
import karaoke.sound.SequencePlayer;

/**
 * Class implements the server for lyric streaming
 */

public class WebServer {
    
    private static final String LOCALHOST = "localhost";

    private final Music music;
    private final HttpServer server;
    
    // Abstraction function:
    //    AF(music, port) = a web server listening on the given port for clients who want to stream lyrics for the given music.
    // Rep invariant:
    //    port >= 0
    // Safety from rep exposure
    //    All fields are private, final, and immutable
    // Thread safety argument:
    //    Playback is confined to local machine
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private, final, and immutable, so both the fields' object types and references are immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs
    
    private static final int DEFAULT_BPM = 50;
    private static final int DEFAULT_TPB = 64;
    
    /**
     * Creates a new Web Server used to stream lyrics of given song to clients
     * 
     * @param music The music file whose lyrics will be streamed 
     * @param port server port number
     * @throws IOException 
     */
    public WebServer(Music music, int port) throws IOException {
        this.music = music;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // handle concurrent requests with multiple threads
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        String hostName = LOCALHOST;
        for (NetworkInterface iface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
            for (InetAddress address: Collections.list(iface.getInetAddresses())) {
                if (address instanceof Inet4Address) {                   
                    if (!address.getHostName().equals(LOCALHOST)) {
                        System.out.println("Address: " + address.getHostName());
                        hostName = address.getHostName();
                    }
                }
            }
        }
        
        for (String voice : music.getVoices()) {
            //handle requests for paths that start with /look/
            System.out.println(" In your web browser, navigate to\n" + hostName + ":" + port + "/" + voice + "/" + "\n to access the lyrics stream for this voice.");  
            server.createContext("/" + voice + "/", exchange -> {
                try {
                    handleClient(exchange);
                } catch (MidiUnavailableException e) {
                    throw new RuntimeException("Midi Unavailable");
                } catch (InvalidMidiDataException e) {
                    throw new RuntimeException("Invalid Midi Data");
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted");
                }
            });
        }
        checkRep();
        }
    
    private void checkRep() {
        assert server != null;
    }
    
    /**
     * Stop the server. It cannot be restarted.
     */
    public void stop() {
        System.err.println("Server will stop");
        server.stop(0);
        }
    
    /**
     * Streams the lyrics to the client
     * @param exchange request/reply object
     * @throws InvalidMidiDataException 
     * @throws MidiUnavailableException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    public void handleClient(HttpExchange exchange) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException, IOException {
        final String path = exchange.getRequestURI().getPath();
        final String base = exchange.getHttpContext().getPath();
        final String voice = path.substring(1, path.length()-1);
        SequencePlayer player = new MidiSequencePlayer(DEFAULT_BPM, DEFAULT_TPB);
        System.err.println("received request " + path);
        

        // plain text response
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");

        // must call sendResponseHeaders() before calling getResponseBody()
        final int successCode = 200;
        final int lengthNotKnownYet = 0;
        exchange.sendResponseHeaders(successCode, lengthNotKnownYet);

        
        music.play(player, 0, exchange.getResponseBody(), voice);
        
        PrintWriter out = new PrintWriter(new OutputStreamWriter(exchange.getResponseBody(), UTF_8), true);
        
        final int enoughBytesToStartStreaming = 2048;
        for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
            out.print(' ');
        }
        out.flush();
        
        if (music.getLyrics(voice).equals("")) {
            out.println("This song has has no lyrics");
        }
        
        synchronized(this.music) {
        music.wait();
        }
        System.err.println("past wait");
        Object lock = new Object();
        player.addEvent(music.duration(), (Double beat) -> {
            synchronized (lock) {
                lock.notifyAll();
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
        exchange.close();
        checkRep();                
    }
    
    /**
     * Starts the playback for the web server. 
     */
    public void startPlayback() {
        Scanner userInput = new Scanner(System.in);
        while(true) {
            System.out.println("Please press the \"p\" key when you are ready to begin playback");
            String input = userInput.next();
            if (input.equals("p")) {
                new Thread(() -> {
                    try {
                        
                            SequencePlayer musicPlayer = new MidiSequencePlayer(DEFAULT_BPM, DEFAULT_TPB);
                            try {
                                music.play(musicPlayer, 0, new ByteArrayOutputStream(), "");
                            } catch (InterruptedException e1) {
                                throw new RuntimeException("error");
                            }
                            synchronized(this.music) {
                            music.notifyAll();
                            }
                            Object lock = new Object();
                            musicPlayer.addEvent(music.duration(), (Double beat) -> {
                                synchronized (lock) {
                                    lock.notify();
                                }
                            });
                            musicPlayer.play();
                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }                        
                    } catch (MidiUnavailableException e) {
                        throw new RuntimeException("MidiSequencePlayer could not be created");
                    } catch (InvalidMidiDataException e) {
                        throw new RuntimeException("MidiSequencePlayer could not be created");
                    }
                    
                }).start();
            } else {
                System.out.println("Invalid Input.");
            }
            System.out.println("If you would like to play the music again, please type \"r\" followed by the Enter key. \n Otherwise type \"q\" followed by the Enter key.");
            input = userInput.nextLine();
            if (input.equals("q")) {
                System.out.println("Goodbye");
                break;
            } else if (!input.equals("r")) {
                System.out.println("Invalid Input");
                break;
            }
        }
        userInput.close();
    }
    
}
