package ch.dorf10.main;

import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.LinkedList;



import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

/**
 * Die Klasse GUI stellt das Fenster (JFrame) und
 * das Spielfeld (JPanel) des Snake-Spiels zur Verfuegung.
 * 
 * @author A. Scheidegger, M.Frieden
 * @version 4.0
 */
public class GUI implements GameListener{
	
	private Game game;
	public static final boolean FPS_MAX_ON = true;
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 800;
	private static final int FPS = 60;
	private long lastFrame = 0;

    public GUI() {
        
    	try {
    		Display.setTitle("3DSnake");
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
    	
    	setUpTime();
    	game = new Game(1);
        game.addGameListener(this);
    	
    	glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60, (float)WIDTH / (float)HEIGHT, 0.01f, 100);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glTranslated(-16, 3, -30);
		glRotated(-45, 1, 0, 0);
		glRotated(-45, 0, 0, 1);
        
        while (!Display.isCloseRequested()) {
        	
        	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    		
			game.processInput();
			game.update(getDelta());
			game.draw();
			
			Display.sync(FPS);
			Display.update();
		}
    }
	
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = currentTime;
		return delta;
	}
	
	public void setUpTime() {
		lastFrame = getTime();
	}

	@Override
	public void win(GameEvent event) {
		endGame("Gewonnen", event.getPoints());
	}

	@Override
	public void loose(GameEvent event) {
		endGame("Verloren", event.getPoints());
	}
	
	private void endGame(String message, int points) {
		Display.setTitle(message + "! [" + points + " PUNKT" + (points > 1 ? "E" : "") + "]");
	}
}
