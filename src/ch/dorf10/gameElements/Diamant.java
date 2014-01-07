package ch.dorf10.gameElements;

import static org.lwjgl.opengl.GL11.glColor3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.omg.CORBA.IntHolder;

public class Diamant extends GameElement {

	private static final long serialVersionUID = -1978284598779466410L;
	
	private int points;
	private boolean catched = false;
	
	public int getPoints() {
		return points;
	}

	public Diamant(Rectangle masse, IntHolder unit, int points) {
		super(masse, unit);
		this.points = points;
	}
	
	public void setCatched() {
		catched = true;
	}
	
	public boolean isCatched() {
		return catched;
	}
	
	@Override
	public boolean colides(SchlangenKopf head) {
		if (!catched && super.colides(head)) {
			head.eatDiamant(this);
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g) {
		if (!catched) {
			Rectangle masse = getAbsoluteRect();
			
			g.setColor(Color.cyan);
			g.fillOval(
				(int)masse.getX(),
				(int)masse.getY(),
				(int)masse.getWidth(),
				(int)masse.getHeight());
		}
	}
	
	@Override
	public void draw() {
		if (!catched) {
			Rectangle bounds = getAbsoluteRect();
			
			glColor3d(0, 200, 0);
			
			GameElement.drawBox(bounds.x, bounds.x + width, bounds.y, bounds.y + height, 1, 0);
		}
	}
}
