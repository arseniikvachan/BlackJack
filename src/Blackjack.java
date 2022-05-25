import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Blackjack {
    public static void main(String[] args) {
    	System.out.println("BlackJack.java Copyright (C) 2022  Arsenii Kvachan\r\n"
    			+ "This program comes with ABSOLUTELY NO WARRANTY; \r\n"
    			+ "This is free software, and you are welcome to redistribute it."
    			+ "\r\n");
        // Spiel erstellen
        Spiel spiel = new Spiel();
        // Scanner erstellen
        Scanner scanner = new Scanner(System.in);
        
        boolean neuesSpiel = true;
        while(neuesSpiel){
            while(!spiel.getIstBeendet()){
                System.out.println("Hit (0) oder Stand (1)?");
                int eingabe = scanner.nextInt();
                wait(3000);
                if(eingabe == 0){
                    spiel.hit();
                } else {
                    spiel.stand();
                }
            }
            wait(3000);
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

public static void wait(int ms)
{
    try
    {
        Thread.sleep(ms);
    }
    catch(InterruptedException ex)
    {
        Thread.currentThread().interrupt();
    }
}
}
