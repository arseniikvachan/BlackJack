import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

/**
 * Class Darstellung - Eine Klasse, die einfache grafische Zeichnungen 
 * in einem Programmfenster ermöglicht. 
 * Basiert auf der Klasse ZEICHENFENSTER aus der 
 * ISB Handreichung Gymnasium Informatik 10 (2008), 
 * Version 2007.05.07, 
 * von den Autoren Michael Kolling (mik), Bruce Quig und Christian Heidrich.
 * 
 * @author Arsenii Kvachan
 *
 * @version 1.0
 */
	
	

public class Darstellung
{
		
	private final static String PATH = "images/cardSpriteSheet.png";	
    private JFrame frame;
    private CanvasPane canvas;
    private JPanel steuerungOst,steuerungSued;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    // Attribute fuer Kartenbilder:
    private BufferedImage deckImg;
    public BufferedImage[][] cardPictures;
    private BufferedImage backOfACard;
    private int spielerkarteLinks;
    private int spielerkarteOben;
    private int dealerkarteLinks;
    private int dealerkarteOben;
    private int imgWidth = 950;   // Dies ist die Breite des Sprite-Sheet-Bildes in Pixeln.
    private int imgHeight = 392;  // Dies ist die Hoehe  des Sprite-Sheet-Bildes in Pixeln.
    private Random zufall;  // Zufallszahlengenerator
    
    private static Darstellung singleton;

    /**
     * Erzeugt eine Darstellung mit Standardmassen 600*500 und Hintergrundfarbe weiss
     * @param titel  Titel des Fensters     
     */
    public Darstellung(String titel)
    {
        this(titel, 600, 500, Color.green.darker().darker());        
    }

    /**
     * Erzeugt ein Darstellung.
     * @param titel  Fensterueberschirft
     * @param breite  Breite des Fensters
     * @param hoehe  Hoehe des Fensters
     * @param hintergrundFarbe  Hintergrundfarbe des Darstellungs
     */
    private Darstellung(String titel, int breite, int hoehe, Color hintergrundFarbe)
    {
        frame = new JFrame();
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(breite, hoehe));
        frame.getContentPane().add(canvas,BorderLayout.CENTER);
        JPanel p1=new JPanel();
        p1.setLayout(new BorderLayout());
        steuerungOst = new JPanel();
        steuerungSued = new JPanel();
        steuerungOst.setLayout(new BoxLayout(steuerungOst,BoxLayout.Y_AXIS));
        steuerungSued.setLayout(new BoxLayout(steuerungSued,BoxLayout.X_AXIS));
        p1.add(steuerungOst,BorderLayout.NORTH);
        frame.getContentPane().add(p1,BorderLayout.EAST);
        frame.getContentPane().add(steuerungSued,BorderLayout.SOUTH);
        frame.setTitle(titel);
        backgroundColor = hintergrundFarbe;
        frame.pack();
        zeige();
        // Lade Bilder fuer Karten
        try {
            deckImg = ImageIO.read(new File(PATH));  //we read the sprite sheet image.
        } catch (IOException e) {
        }
        
        cardPictures = new BufferedImage[4][13]; //we create this two-dimensional array to store the individiual card pictures.
        try {
            backOfACard = ImageIO.read(new File("images/backsideOfACard.jpg"));  //this image will be the back of a card.
        } catch (IOException e) {
        }
        
        for (int c = 0; c < 4; c++) { 
          for (int r = 0; r < 13; r++) {
            cardPictures[c][r] = deckImg.getSubimage(r*imgWidth/13, c*imgHeight/4, imgWidth/13, imgHeight/4);  //we assign the relative card pictures to the 2-D array.
          }
        }
        setzeKoordinatenZurueck();
        zeichneSpielerTitel();
        zeichnePunktestand(0, 0);
        zufall = new Random();
    }

    public static Darstellung gibFenster()
    {
        if (singleton==null){singleton=new Darstellung("Blackjack");}
        singleton.zeige();
        return singleton;
    }

    public void close(){
        frame.dispose();
    }

    /**
     * Macht das Darstellung sichtbar bzw. setzt es in den Vordergrund,
     * falls es bereits sichtbar ist.
     */
    private void zeige()
    {
        if(graphic == null) {
            // nur beim ersten Aufruf wird der Hintergrund mit der Hintergrundfarbe 
            // gefuellt
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(true);
    }
    
    /**
     * Loescht den Inhalt des Zeichenfensters und setzt Instanzattribute zurueck.
     */
    public void setzeZurueck()
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
        canvas.repaint();
        setzeKoordinatenZurueck();
        zeichneSpielerTitel();
        zeichnePunktestand(0, 0);
    }
    
    /**
     * Setzt Koordinaten fuer die Platzierung der Karten zurueck.
     */
    private void setzeKoordinatenZurueck()
    {
        spielerkarteLinks = 185;
        spielerkarteOben = 375;
        dealerkarteLinks = 185;
        dealerkarteOben = 50;
    }
    
    /**
     * Zeichnet die Anzeige fuer Spieler und Dealer.
     */
    private void zeichneSpielerTitel()
    {
        zeichneText("Dealer:", 270, dealerkarteOben + 130, 16);
        zeichneText("Spieler:", 270, spielerkarteOben - 20, 16);
    }
    
    /**
     * Zeichnet den Punktestand.
     */
    public void zeichnePunktestand(int spielerSumme, int dealerSumme)
    {
        // Loesche alten Punktestand.
        loescheRechteck(345, dealerkarteOben + 110, 300, 20);
        loescheRechteck(345, spielerkarteOben - 40, 300, 20);
        // Zeichne neuen Punktestand.
        zeichneText(Integer.toString(dealerSumme), 345, dealerkarteOben + 130, 16);
        zeichneText(Integer.toString(spielerSumme), 345, spielerkarteOben - 20, 16);
    }
    
    /**
     * Zeichnet das Resultat.
     */
    public void zeichneResultatNachricht(String resultat)
    {
        zeichneText(resultat, 225, 275, 32);
    }
    
    /**
     * Zeichnet eine Karte in das Zeichnenfenster.
     * @param  punktWert        int Wert zwischen 1 und 10, der den punktWert (Ass = 1, 2 bis 10 = 2 bis 10, Bube/Dame/Koenig = 10) der Karte angibt. 
     * @param  istSpieler       boolescher Wert, der angibt, ob die Karte fuer den Spieler (true) oder den Dealer (false) gelegt werden soll.
     * @return                  Gibt einen booleschen Wert zurueck, der angibt, ob die Karte gezeichnet werden konnte.
     */
    private boolean zeichneKarte(int punktWert, boolean istSpieler)
    {
        // Ziehe ein zufaelliges Kartensymbol und einen zufaelligen Kartenwert.
        int blattSymbol = zufall.nextInt(4);
        int blattWert;
        if (punktWert < 10) {  // Karte ist Ass oder eine Zahl von 2 bis 10
            blattWert = punktWert - 1;
        } else {  // Karte ist 10 oder Bube oder Dame oder Koenig
            blattWert = zufall.nextInt(3) + 10;
        }
        BufferedImage kartenbild = cardPictures[blattSymbol][blattWert];
        int x;
        int y;
        if (istSpieler) {
            x = spielerkarteLinks;
            y = spielerkarteOben;
            spielerkarteLinks += 50;
        } else {
            x = dealerkarteLinks;
            y = dealerkarteOben;
            dealerkarteLinks += 50;
        }
        return zeichneBild(kartenbild, x, y);
    }
    
    public boolean zeichneSpielerKarte(int punktWert) 
    {
        return zeichneKarte(punktWert, true);
    }
    
    public boolean zeichneDealerKarte(int punktWert) 
    {
        return zeichneKarte(punktWert, false);
    }

    /**
     * Zeichnet ein Bild in das Zeichnenfenster .
     * @param  bild    das anzuzeigende Bild 
     * @param  x       x-Koordinate des linken Bildrands 
     * @param  y       y-Koordinate des oberen Bildrands 
     * @return  gibt eines booleschen Wert zurueck, der angibt, ob das Bild vollstaendig geladen 
     *          werden konnte 
     */
    private boolean zeichneBild(Image bild, int x, int y)
    {
        boolean result = graphic.drawImage(bild, x, y, null);
        canvas.repaint();
        return result;
    }

    /**
     * Zeichnet einen Text.
     * @param  text    die anzuzeigende Zeichenkette 
     * @param  x       x-Koordinate des linken Rands 
     * @param  y       y-Koordinate des oberen Rands 
     */
    private void zeichneText(String text, int x, int y, int textGroesse)
    {
        Color originalColor = graphic.getColor();
        Font originalFont = graphic.getFont();
        graphic.setColor(Color.white);
        graphic.setFont(new Font("default", Font.BOLD, textGroesse));
        graphic.drawString(text, x, y);
        graphic.setColor(originalColor);
        graphic.setFont(originalFont);
        canvas.repaint();
    }
    
    /**
     * Loescht das Innere eines Rechtecks.
     * @param xPos,yPos Koordinaten der linken oberen Ecke 
     * @param breite, hoehe Breite und Hoehe des Rechtecks
     */
    private void loescheRechteck(int xPos, int yPos, int breite, int hoehe)
    {
        loesche(new Rectangle(xPos, yPos, breite, hoehe));
    }
    
    /**
     * Loescht das Innere eines Shape-Objekts.
     * @param  shape  das Shape-Object, welches geloescht werden soll 
     */
    private void loesche(Shape shape)
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        graphic.fill(shape);              // erase by filling background color
        graphic.setColor(original);
        canvas.repaint();
    }
    
    /**
     * Wartet eine bestimmte Zeit.
     * Eine kurze Verzoegerung kann z. B. fuer Animationen verwendet werden.
     * @param  zeit  Wartezeit in Millisekunden 
     */
    public void warte(int zeit)
    {
        try
        {
            Thread.sleep(zeit);
        } 
        catch (InterruptedException e)
        {
            // ignoring exception at the moment
        }
    }

    public BufferedImage getBackOfACard() {
		return backOfACard;
	}

	public void setBackOfACard(BufferedImage backOfACard) {
		this.backOfACard = backOfACard;
	}

	/************************************************************************
     * Nested class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel
    {
        private static final long serialVersionUID = 20060330L;
        
        public void paint(Graphics g)
        {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
}
