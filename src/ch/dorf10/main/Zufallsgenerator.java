package ch.dorf10.main;

import java.awt.Color;
import java.util.Random;

/**
 * Hilfsklasse zum Erzeugen von Zufallszahlen.
 * 
 * @author Andres Scheidegger
 */
public class Zufallsgenerator {
    /**
     * Random-Objekt zur Erzeugung von Zufallszahlen
     */
    private static final Random RANDOM = new Random(System.currentTimeMillis());


    /**
     * Hilfsmethode zum Erzeugen von Zufallszahlen in einem gegebenen Intervall,
     * inklusive Intervallgrenzen.
     * @param untereGrenze Unter Grenze des Intervalls
     * @param obereGrenze Obere Grenze des Intervalls
     * @return Zufallszahl innerhalb des gegebenen Intervalls
     */
    public static int zufallszahl(int untereGrenze, int obereGrenze) {
        return Math.abs(RANDOM.nextInt() % (obereGrenze + 1 - untereGrenze)) + untereGrenze;
    }
    
    public static int zufallsFarbKanal() {
    	return zufallsFarbKanal(128, 128);
    }
    
    public static int zufallsFarbKanal(int ausgangslage, int maxAbweichung) {
    	int min = ausgangslage - maxAbweichung;
    	int max = ausgangslage + maxAbweichung;
    	return zufallszahl(min < 0 ? 0 : min, max > 255 ? 255 : max);
    }
    
    public static Color zufallsFarbe() {
    	return new Color(zufallsFarbKanal(), zufallsFarbKanal(), zufallsFarbKanal());
    }
    
    public static Color zufallsFarbe(Color ausgangslage, int maxAbweichung) {
    	int red = zufallsFarbKanal(ausgangslage.getRed(), maxAbweichung);
    	int green = zufallsFarbKanal(ausgangslage.getGreen(), maxAbweichung);
    	int blue = zufallsFarbKanal(ausgangslage.getBlue(), maxAbweichung);
    	return new Color(red, green, blue);
    }

}
