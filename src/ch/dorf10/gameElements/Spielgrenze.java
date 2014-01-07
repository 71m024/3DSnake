package ch.dorf10.gameElements;

import java.awt.Rectangle;

import org.omg.CORBA.IntHolder;

import static org.lwjgl.opengl.GL11.*;

public class Spielgrenze extends GameElement {
	
	private static final long serialVersionUID = -4765133453327219906L;

	public Spielgrenze(Rectangle masse, IntHolder unit) {
		super(masse, unit);
	}
	
	@Override
	public boolean colides(SchlangenKopf head) {
		if (!super.colides(head)) {
			head.die();
			return true;
		}
		return false;
	}

	@Override
	public void draw() {
		glColor3d(255, 204, 0);
		Rectangle bounds = getAbsoluteRect();
		glBegin(GL_QUADS);
			glVertex3i(bounds.x + width, bounds.y, 0);
			glVertex3i(bounds.x + width, bounds.y + height, 0);
			glVertex3i(bounds.x, bounds.y + height, 0);
			glVertex3i(bounds.x, bounds.y, 0);
		glEnd();
	}
}
