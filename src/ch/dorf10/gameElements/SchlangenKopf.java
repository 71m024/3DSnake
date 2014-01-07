package ch.dorf10.gameElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.IntHolder;

public class SchlangenKopf extends SchlangenGlied {
	
	private static final long serialVersionUID = 8288992889070558225L;
	
	public static final Point DIRECTION_LEFT = new Point(-1, 0);
	public static final Point DIRECTION_UP = new Point(0, 1);
	public static final Point DIRECTION_RIGHT = new Point(1, 0);
	public static final Point DIRECTION_DOWN = new Point(0, -1);
	public static final Point DIRECTION_START = DIRECTION_RIGHT;
	public static final int INVERT_FREQUENCY_MAX = 2;
	
	protected Point direction;
	
	private int invertedBevoreMoves = 0;
	private boolean alive = true;
	private int stomache = 0;

	public SchlangenKopf(Rectangle masse, IntHolder unit, Color color) {
		super(masse, unit, color);
		direction = DIRECTION_START;
	}
	
	public void die() {
		alive = false;
	}
	
	public void eatDiamant(Diamant dia) {
		stomache += dia.getPoints();
		dia.setCatched();
	}
	
	public void setDirection(Point direction) {
		if (direction.equals(invertDirection(this.direction))) {
			if (invertedBevoreMoves >= INVERT_FREQUENCY_MAX) {
				this.direction = invertDirection(getLastGlied().getDirection());
				invert();
			}
		} else {
			this.direction = direction;
		}
	}
	
	public void invert() {
		invertedBevoreMoves = 0;
		List<SchlangenGlied> glieder = new ArrayList<SchlangenGlied>();
		addAllGlieder(glieder);
		while (glieder.size() > 1) {
			
			SchlangenGlied firstGlied = glieder.get(0);
			SchlangenGlied lastGlied = glieder.get(glieder.size() - 1);
			
			Point tmpLocation = firstGlied.getLocation();
			firstGlied.setLocation(lastGlied.getLocation());
			lastGlied.setLocation(tmpLocation);
			
			glieder.remove(firstGlied);
			glieder.remove(lastGlied);
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getLength() {
		return getFollowingGlieder();
	}
	
	@Override
	public Point getDirection() {
		return direction;
	}
	
	@Override
	public void move() {
		if (alive) {
			if (stomache > 0) {
				addGlied();
				stomache--;
			}
			super.move();
			invertedBevoreMoves ++;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		Rectangle masse = getAbsoluteRect();
		
		//draw eyes
		g.setColor(Color.WHITE);
		g.fillOval((int)(masse.getX() + masse.getWidth() * 0.2), (int)(masse.getY() + masse.getHeight() * 0.4), (int)(masse.getWidth() / 6), (int)(masse.getHeight() / 6));
		g.fillOval((int)(masse.getX() + masse.getWidth() * 0.7), (int)(masse.getY() + masse.getHeight() * 0.4), (int)(masse.getWidth() / 6), (int)(masse.getHeight() / 6));
	}
	
	public static Point invertDirection(Point direction) {
		Point newDirection = (Point) direction.clone();
		newDirection.x *= -1;
		newDirection.y *= -1;
		return newDirection;
	}
}
