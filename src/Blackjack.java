import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args) {
        // Spiel erstellen
        Spiel spiel = new Spiel();
        // Scanner erstellen
        Scanner scanner = new Scanner(System.in);
        
        boolean neuesSpiel = true;
        while(neuesSpiel){
            while(!spiel.getIstBeendet()){
                System.out.println("Hit (0) oder Stand (1)?");
                int eingabe = scanner.nextInt();
                if(eingabe == 0){
                    spiel.hit();
                } else {
                    spiel.stand();
                }
            }
            System.out.println("Noch ein Spiel? Ja = 0 / Nein = 1");
            int eingabe = scanner.nextInt();
            if(eingabe == 0){
                spiel.neuesSpiel();
            } else {
                neuesSpiel = false;
            }
        }
        // Scanner schliessen
        scanner.close();
    }
}
