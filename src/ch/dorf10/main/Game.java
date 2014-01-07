package ch.dorf10.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.omg.CORBA.IntHolder;

import ch.dorf10.gameElements.*;

public class Game {
	
    private List<GameElement> gameElements = new ArrayList<GameElement> ();
    private SchlangenKopf schlange;
    private Set<Diamant> diamanten = new HashSet<Diamant>();
    private double elapsed = 0;
    private IntHolder unit = new IntHolder(-1);
    private Set<GameListener> listeners = new HashSet<GameListener>();
    
    public static final int MOVE_TIME_MILLS = 100;
    public static final Dimension FIELD_DIMENSION = new Dimension(25, 25);
    public static final int DIAMONDS_NUMBER_MAX = 50;
    public static final int DIAMONDS_NUMBER_MIN = 40;
    public static final int DIAMONDS_POINTS_MIN = 3;
    public static final int DIAMONDS_POINTS_MAX = 5;
    public static final boolean FPS_SHOW = true;
    
    public Game(int unit) {
    	this.unit.value = unit;
    	Dimension spielgrenzeSize = new Dimension(FIELD_DIMENSION.width, FIELD_DIMENSION.height);
    	Spielgrenze spielgrenze = new Spielgrenze(
    			new Rectangle(new Point(0, 0), spielgrenzeSize),
    			this.unit);
    	
    	schlange = new SchlangenKopf(
    			new Rectangle(
	    			(int)spielgrenze.getX() + 2,
	    			(int)spielgrenze.getY() + 2,
	    			1,
	    			1),
	    		this.unit,
    			Color.GREEN);
    	
    	gameElements.add(spielgrenze);
    	
    	for (int i = 1; i < Zufallsgenerator.zufallszahl(DIAMONDS_NUMBER_MIN, DIAMONDS_NUMBER_MAX) + 1; i++) {
    		Rectangle diamantRect = new Rectangle(
    				(int)spielgrenze.getX() + Zufallsgenerator.zufallszahl(0, FIELD_DIMENSION.width - 1),
    				(int)spielgrenze.getY() + Zufallsgenerator.zufallszahl(0, FIELD_DIMENSION.height - 1),
    				1,
    				1);
    		Diamant newDiamant = new Diamant(diamantRect, this.unit, Zufallsgenerator.zufallszahl(DIAMONDS_POINTS_MIN, DIAMONDS_POINTS_MAX));
    		gameElements.add(newDiamant);
    		diamanten.add(newDiamant);
    	}
    	
    	gameElements.add(schlange);
    }
    
    public void update(double elapsed) {
    	this.elapsed += elapsed;
    	while (this.elapsed > MOVE_TIME_MILLS) {
    		this.elapsed -= MOVE_TIME_MILLS;
    		
    		for (GameElement element : gameElements) {
    			element.colides(schlange);
    		}
    		
    		schlange.move();
    	}
    	
    	//check if there are catchable diamonds
    	boolean catchableDias = false;
    	for (Diamant dia : diamanten) {
    		if (!dia.isCatched()) {
    			catchableDias = true;
    		}
    	}
    	if (!catchableDias) {
    		for (GameListener listener : listeners) {
    			listener.win(new GameEvent(schlange.getLength()));
    		}
    	}
    	
    	//check if game is lost
    	if (!schlange.isAlive()) {
    		for (GameListener listener : listeners) {
    			listener.loose(new GameEvent(schlange.getLength()));
    		}
    	}
    }

    public void draw() {
    	for (GameElement gameElement : gameElements) {
    		gameElement.draw();
    	}
    }

	public void processInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			schlange.setDirection(SchlangenKopf.DIRECTION_UP);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			schlange.setDirection(SchlangenKopf.DIRECTION_DOWN);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			schlange.setDirection(SchlangenKopf.DIRECTION_RIGHT);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			schlange.setDirection(SchlangenKopf.DIRECTION_LEFT);
		}
	}
	
	public void addGameListener(GameListener listener) {
		listeners.add(listener);
	}
}
