import java.util.Random;
/**
 * Die Klasse Zufallszahlengenerator.
 * Bietet die Methode zufallszahl(int von, int bis) an, 
 * mit der zufaellige ganze Zahlen (int) zwischen von und bis erzeugt werden koennen. 
 * von und bis sind dabei inklusive, d.h. 
 * es gilt: von <= erzeugte Zufallszahl <= bis.
 * 
 * @author Arsenii Kvachan 
 * @version (1.0)
 */
public class Zufallszahlengenerator
{
    // Instanzvariablen
    private Random zufall;  // Java Zufallszahlengenerator

    /**
     * Konstruktor für Objekte der Klasse Kartendeck
     */
    public Zufallszahlengenerator()
    {
        // Java Zufallszahlengenerator initialisieren
        zufall = new Random();
    }

    /**
     * Zieht eine zufaellige ganze Zahl zwischen von und bis, 
     * wobei von und bis inklusive sind, d.h. es gilt: 
     * von <= erzeugte Zufallszahl <= bis.
     * @return Gibt einen zufaelligen int Wert x zurueck, mit von <= x <= bis.
     */
    public int zufallszahl(int von, int bis)
    {
        // Ziehe eine zufaellige ganze Zahl (int) zwischen von und bis, 
        // wobei von und bis inklusive sind, d.h. es gilt: 
        // von <= erzeugte Zufallszahl <= bis.
        return zufall.nextInt(bis - von + 1) + von;
    }
}
