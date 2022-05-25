/**
 * Die Klasse Kartendeck.
 * Ein Objekt dieser Klasse stellt ein Kartendeck zur Verfuegung, das 
 * eine Methode karteZiehen() anbietet, die aus dem Kartendeck eine 
 * zufaellige Karte zieht, d.h. genauer gesagt 
 * wird eine zufaellige Zahl zwischen 1 und 10 gezogen, 
 * die dem Wert einer Karte im Spiel Blackjack entspricht, wobei
 *     - 1 = Ass
 *     - 2 bis 9 = 2 bis 9
 *     - 10 = 10 oder Bube oder Dame oder Koenig
 * 
 * @author Arsenii Kvachan
 * @version (1.0)
 */
public class Kartendeck
{
    // Instanzvariablen
    private Zufallszahlengenerator zufall;
    /**
     * Konstruktor für Objekte der Klasse Kartendeck
     */
    public Kartendeck()
    {
        // Hier musst du die Instanzvariable fuer den Zufallszahlengenerator initialisieren, 
        // indem du ihr ein neues Objekt der Klasse Zufallszahlengenerator zuweist.
    	zufall = new Zufallszahlengenerator();
    }

    /**
     * Zieht eine zufaellige Karte aus dem Kartendeck, d.h. genauer gesagt 
     * wird eine zufaellige Zahl zwischen 1 und 10 gezogen, 
     * die dem Wert einer Karte im Spiel Blackjack entspricht, wobei
     *     - 1 = Ass
     *     - 2 bis 9 = 2 bis 9
     *     - 10 = 10 oder Bube oder Dame oder Koenig
     *     
     * @return Gibt einen zufaelligen int Wert zwischen 1 und 10 zurueck.
     */
    public int karteZiehen()
    {
        int zufallszahl = zufall.zufallszahl(1, 10);
        return zufallszahl;  // Diese Zeile musst du ersetzen!
    }
}
