package br.com.fr.commons.util;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class CanvasPoint {

	public double x;
	public double y;

	public CanvasPoint() {
		x = 0;
		y = 0;
	}

	public CanvasPoint(MouseEvent me) {
		x = me.getX();
		y = me.getY();
	}

	public CanvasPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
