package karaoke.sound;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stores header information of a piece
 */

public class Header {

    private final String composer;
    private final String key;
    private final String title;
    private final List<String> voices;
    private final double noteLength;
    private final double meter;
    private final int index;
    private final double bpm;
    
    // Abstraction function:
    //    AF(composer, key, title, voices, noteLength, meter, index, bpm) = a Header object that stores information about a piece's composer in composer, key signature in key, title in title,
    //                                                                               all the voices in voices, the default note length in noteLength, the meter in meter, and the index of the piece in index, with a specified beats per minute bpm
    // Rep invariant:
    //    title.length() > 0
    //    key.length() > 0
    // Safety from rep exposure:
    //    All fields are private and final
    //    The mutable field notes is never returned in any methods, and thus is not exposed to the client
    // Thread safety argument:
    //    This class is thread safe because it is immutable:
    //       - There are no mutator methods in this class
    //       - All fields are private and final, so the field's reference is immutable
    //       - The rep is not exposed to the client
    //       - No mutation of any kind occurs 
    //       - voices points to a mutable List object, but that List is encapsulated
    //         in this object, not shared with any other object or exposed to a client
    
    private static final double DEFAULT_BPM = 100.0;
    private static final int DENOM = 3;
    private static final double THREE_QUARTERS = 0.75;
    private static final double SIXTEEN = 16.0;
    private static final double EIGHT = 8.0;
    private static final int LAST = 4;
    
    /**
     * Creates a new Header
     * 
     * @param headerMap the header information 
     */
   public Header(Map<Character, String> headerMap) {
       
       this.index = Integer.parseInt(headerMap.get('X'));
       this.title = headerMap.get('T');
       this.key = headerMap.get('K');
       
       this.composer = headerMap.getOrDefault('C', "Unknown");
       this.voices = Arrays.asList(headerMap.getOrDefault('V', "").split("\n"));
       
       if (headerMap.containsKey('M')) {
           String meterFractionString = headerMap.get('M');
           int meterNumerator = meterFractionString.substring(0,1).equals("C") ? 1 : Integer.parseInt(meterFractionString.substring(0, 1));
           int meterDenominator = meterFractionString.substring(0,1).equals("C") ? 1 : Integer.parseInt(meterFractionString.substring(2, DENOM));
           this.meter = (double) meterNumerator / (double) meterDenominator;
       } else {
           this.meter = 1;
       }
       
       if (headerMap.containsKey('L')) {
           String noteLengthFractionString = headerMap.get('L');
           int noteLengthNumerator = Integer.parseInt(noteLengthFractionString.substring(0, 1));
           int noteLengthDenominator = Integer.parseInt(noteLengthFractionString.substring(2, DENOM));
           this.noteLength = (double) noteLengthNumerator / (double) noteLengthDenominator;
       } else {
           this.noteLength = this.meter < THREE_QUARTERS ? 1.0/SIXTEEN : 1.0/EIGHT;
       }
       
       if (headerMap.containsKey('Q')) {
           String tempoFractionString = headerMap.get('Q');
           int tempoNumerator = Integer.parseInt(tempoFractionString.substring(0, 1));
           int tempoDenominator = Integer.parseInt(tempoFractionString.substring(2, DENOM));
           double tempo = (double) tempoNumerator / (double) tempoDenominator;
           this.bpm = (tempo/this.noteLength) * ((double) Integer.parseInt(tempoFractionString.substring(LAST)));
       } else {
           this.bpm = DEFAULT_BPM;
       }  
       checkRep();
   }
   
   /**
    * Checks the rep invariant.
    */
   private void checkRep() {
       assert this.title.length() > 0;
       assert this.key.length() > 0;
   }
   
   @Override
   public String toString() {
       String result = "";
       result += "X:" + this.getIndex() + "T:" + this.getTitle() + "C:" + this.getComposer() + "V:" + this.getVoices() + "M:" + this.getMeter() + "L:" + this.getNoteLength() + "Q:" + this.getBPM() + "K:" + this.getKey();
       return result;
   }
   
   /**
    * @return the composer
    */
   public String getComposer() {
       return this.composer;
   }
   
   /**
    * @return the key signature
    */
   public String getKey() {
       return this.key;
   }
   
   /**
    * @return the title
    */
   public String getTitle() {
       return this.title;
   }
   
   /**
    * @return the voices present
    */
   public List<String> getVoices() {
       return new ArrayList<String>(this.voices);
   }
   
   /**
    * @return the default note length
    */
   public double getNoteLength() {
       return this.noteLength;
   }
   
   /**
    * @return the meter
    */
   public double getMeter() {
       return this.meter;
   }
   
   /**
    * @return the beats per minute tempo
    */
   public double getBPM() {
       return this.bpm;
   }
   
   /**
    * @return the index
    */
   public int getIndex() {
       return this.index;
   }
}
