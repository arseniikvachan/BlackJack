
/**
 * Eine Klasse fuer das Spiel Blackjack, auch bekannt als 17 und 4.
 * 
 * @author Arsenii Kvachan 
 * @version (1.0)
 */
public class Spiel
{
    // Instanzvariablen
    private Spieler spieler;
    private Spieler dealer;
    private Kartendeck kartendeck;
    private boolean istBeendet;
    private Darstellung darstellung;

    /**
     * Konstruktor für Objekte der Klasse Spiel
     */
    public Spiel()
    {
        neuesSpiel();
    }

    public boolean getIstBeendet(){
        return istBeendet;
    }
    
    /**
     * Eroeffnet ein neues Spiel.
     */
    public void neuesSpiel() {
        // Instanzvariablen initialisieren
        spieler = new Spieler();
        dealer = new Spieler();
        kartendeck = new Kartendeck();
        istBeendet = false;
        darstellung = Darstellung.gibFenster();
        // Setze Darstellung zurueck.
        darstellung.setzeZurueck();
        // Ziehe die erste Karte fuer den Dealer.
        hitDealer();
        // Ziehe die ersten 2 Karten fuer den Spieler.
        for (int i=1; i<=2; i++) {
            hit();
        }
        // Stelle den Punktestand dar.
        darstellung.zeichnePunktestand(spieler.getPunktestand(), dealer.getPunktestand());
    }
    
    /**
     * Zieht eine Karte fuer den Spieler, fuegt sie seiner Hand hinzu, 
     * und ueberprueft, ob die Punktzahl des Spielers 21 uebertrifft.
     * Wenn ja, hat der Spieler sofort verloren.
     * Ist das Spiel bereits beendet, passiert nichts, wenn diese Methode aufgerufen wird.
     */
    public void hit()
    {
        // Falls das Spiel nicht beendet ist:
        if (!istBeendet) {
            // Ziehe eine zufaellige Karte aus dem Kartendeck.
        	int zufaelligeKarteSpieler = kartendeck.karteZiehen();
            // Stelle die Karte graphisch dar.
            darstellung.zeichneSpielerKarte(zufaelligeKarteSpieler);
            // Fuege die Karte der Hand des Spielers hinzu.
            spieler.hit(zufaelligeKarteSpieler);
            // Stelle den aktuellen Punktestand auf der Konsole dar.
            System.out.println("Summe der Spielerhand: " + spieler.getPunktestand());
            // Stelle den aktuellen Punktestand graphisch dar.
            darstellung.zeichnePunktestand(spieler.getPunktestand(), dealer.getPunktestand());         
            // Ueberpruefe, ob die Punktzahl des Spielers 21 uebersteigt.
            // Wenn ja, hat der Spieler verloren.
            boolean uebersteigt = spieler.istDrueber();
            if (uebersteigt == true) {
            	verloren();}
                
            
        }
    }
    
    /**
     * Zieht eine Karte fuer den Dealer und fuegt sie seiner Hand hinzu.
     */
    private void hitDealer()
    {
        // Ziehe eine zufaellige Karte aus dem Kartendeck.
    	int zufaelligeKarteDealer = kartendeck.karteZiehen();
        // Stelle die Karte graphisch dar.
    	darstellung.zeichneSpielerKarte(zufaelligeKarteDealer);
        // Fuege die Karte der Hand des Dealers hinzu.
        dealer.hit(zufaelligeKarteDealer);
        // Stelle den aktuellen Punktestand auf der Konsole dar.
        System.out.println("Summe der Dealerhand: " + dealer.getPunktestand());
        // Stelle den aktuellen Punktestand graphisch dar.
        darstellung.zeichnePunktestand(spieler.getPunktestand(), dealer.getPunktestand());   
    }
    
    /**
     * Zieht solange Karten fuer den Dealer, bis seine Punktzahl 17 oder groesser ist.
     * Diese Methode wird verwendet, um zu signalisieren, dass der Spieler keine Karten mehr moechte.
     * Ist das Spiel bereits beendet, passiert nichts, wenn diese Methode aufgerufen wird.
     */
    public void stand() 
    {
        // Falls das Spiel nicht beendet ist:
        // - Solange der Punktestand des Dealers kleiner als 17 ist, fuehre die Methode hitDealer() wiederholt aus. 
        // - Ermittle anschliessend das Resultat des Spiels.
    	if(istBeendet == false) {
    		while(dealer.getPunktestand() < 17) {
    			hitDealer();
    			darstellung.warte(750);
    		}
    		resultat();
    	}  
    }
    
    /**
     * Prueft, ob der Spieler gewonnen oder verloren hat, oder ob ein Unentschieden vorliegt.
     */
    private void resultat() 
    {
        // Falls der Punktestand des Dealers groesser als 21 ist oder kleiner als der Punktestand des Spielers, hat der Spieler gewonnen.
        // Falls der Punktestand des Dealers groesser als der des Spielers ist, hat der Spieler verloren.
        // Ansonsten endet das Spiel als Unentschieden. 
        if(dealer.istDrueber() || dealer.getPunktestand() < spieler.getPunktestand()) {
        	gewonnen();
        } else {
        	if(dealer.getPunktestand() > spieler.getPunktestand()) {
        		verloren();
        	} else {
        		unentschieden();
        	}
        }
        // Spiel ist beendet.
        istBeendet = true;
    }
    
    /**
     * Stellt dar, dass der Spieler gewonnen hat.
     */
    private void gewonnen() 
    {
        // Konsolendarstellung (Text)
        System.out.println(
            "Gewonnen! Dealer: " + dealer.getPunktestand() + 
            " Spieler: " + spieler.getPunktestand());
        // Graphische Darstellung
        darstellung.zeichneResultatNachricht("Gewonnen!");
        // Aufraumen (3 Sekunden warten und dann Fenster schliessen)
        darstellung.warte(3000);
        darstellung.close();
    }
    
    /**
     * Stellt dar, dass der Spieler verloren hat.
     */
    private void verloren() 
    {
        // Spiel ist beendet
        istBeendet = true;
        // Konsolendarstellung (Text)
        System.out.println(
            "Verloren! Dealer: " + dealer.getPunktestand() + 
            " Spieler: " + spieler.getPunktestand());
        // Graphische Darstellung
        darstellung.zeichneResultatNachricht("Verloren!");
        // Aufraumen (3 Sekunden warten und dann Fenster schliessen)
        darstellung.warte(3000);
        darstellung.close();
    }
    
    /**
     * Stellt dar, dass ein Unentschieden zwischen Spieler und Dealer 
     * vorliegt.
     */
    private void unentschieden() 
    {
        // Konsolendarstellung (Text)
        System.out.println(
            "Unentschieden! Dealer: " + dealer.getPunktestand() + 
            " Spieler: " + spieler.getPunktestand());
        // Graphische Darstellung
        darstellung.zeichneResultatNachricht("Unentschieden!");
        // Aufraumen (3 Sekunden warten und dann Fenster schliessen)
        darstellung.warte(3000);
        darstellung.close();
    }
}
