package ch.dorf10.gameElements;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.awt.Rectangle;

import org.omg.CORBA.IntHolder;

public abstract class GameElement extends Rectangle{

	private static final long serialVersionUID = 8553525279457955212L;
	
	protected IntHolder unit;

	protected GameElement(Rectangle masse, IntHolder unit) {
		super(masse);
		this.unit = unit;
	}
	
	public abstract void draw();
	
	public boolean colides(SchlangenKopf head) {
		return getBounds().intersects(head.getBounds()) && this != head;
	}
	
	public Rectangle getAbsoluteRect() {
		Rectangle absRect = new Rectangle();
		int unitVal = unit.value;
		absRect.x = x * unitVal;
		absRect.y = y * unitVal;
		absRect.width = width * unitVal;
		absRect.height = height * unitVal;
		return absRect;
	}
	
	public static void drawBox(double left, double right, double top, double bottom, double back, double front) {
		glBegin(GL_QUADS);
			glVertex3d(left, top, back);
			glVertex3d(right, top, back);
			glVertex3d(right, bottom, back);
			glVertex3d(left, bottom, back);
			
			glVertex3d(left, bottom, front);
			glVertex3d(right, bottom, front);
			glVertex3d(right, top, front);
			glVertex3d(left, top, front);
			
			glVertex3d(right, bottom, front);
			glVertex3d(right, bottom, back);
			glVertex3d(right, top, back);
			glVertex3d(right, top, front);
			
			glVertex3d(left, top, front);
			glVertex3d(left, top, back);
			glVertex3d(left, bottom, back);
			glVertex3d(left, bottom, front);
			
			glVertex3d(left, top, front);
			glVertex3d(right, top, front);
			glVertex3d(right, top, back);
			glVertex3d(left, top, back);
			
			glVertex3d(left, bottom, back);
			glVertex3d(right, bottom, back);
			glVertex3d(right, bottom, front);
			glVertex3d(left, bottom, front);
		glEnd();
	}
}
