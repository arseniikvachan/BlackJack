
/**
 * Eine Klasse, die einen Spieler im Spiel Blackjack repraesentiert.
 * 
 * @author Arsenii Kvachan
 * @version (1.0)
 */
public class Spieler
{
    // Instanzvariablen
    private int punktestand;

    /**
     * Konstruktor für Objekte der Klasse Spieler
     */
    public Spieler()
    {

    	this.punktestand = 0;
        
    }

	/**
     * Gibt den Punktestand des Spielers zurueck.
     * @return Gibt den Punktestand des Spielers als int zurueck.
     */
    public int getPunktestand() 
    {
        return this.punktestand;
    }
    
    /**
     * Prueft, ob der Punktestand des Spielers groesser als 21 ist.
     * @return Gibt einen booleschen Wert zurueck, der angibt, ob der Punktestand groesser als 21 ist.
     */
    public boolean istDrueber() 
    {
        if (this.getPunktestand() <= 21) {
        	return false;
        } else {
        	return true;
        }
    }

    /**
     * Fuegt dem Punktestand des Spielers den Punktewert einer Spielkarte (als ganze Zahl bzw. int) hinzu.
     * @param spielWert  Punktewert der Spielkarte (als int)
     */
    public void hit(int spielWert)
    {
        if (spielWert == 1 && (this.getPunktestand() + 11) <= 21) {
        	spielWert = 11;
        }
        this.punktestand = this.punktestand + spielWert;
    }
}
