package br.com.fr.commons.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MultiBoxedImageCanvas {

	private Logger logger = Logger.getLogger(getClass());

	// classe Point está definida aqui como privada, verificar no final do
	// código
	private final CanvasPoint offset = new CanvasPoint();

	private boolean dragging = false;

	private double width = 350;
	private double height = 350;

	private Rectangle currentBox;
	private Rectangle boxes[];

	private int numBoxes;
	private Image image;

	private Canvas canvas;
	private GraphicsContext gc;

	public MultiBoxedImageCanvas(Image image) {
		this(image, 1);
	}

	public MultiBoxedImageCanvas(Image image, int numBoxes) {
		this.image = image;
		this.numBoxes = numBoxes;

		this.width = (image.getWidth() / numBoxes) - (numBoxes - 1);
		if (this.width <= 0) {
			this.width = Math.max(50, image.getWidth());
		}

		this.height = (image.getHeight() / numBoxes) - (numBoxes - 1);
		if (this.height <= 0) {
			this.height = Math.max(50, image.getHeight());
		}
	}

	public void resetBoxes(GraphicsContext gc) {
		try {
			int xx = 0;
			int yy = 0;

			gc.setStroke(Color.RED);

			for (int b = 0; b < boxes.length; b++) {

				gc.strokeRect(xx, yy, width, height);
				boxes[b] = new Rectangle(xx, yy, width, height);

				xx += width;
				xx++;
				
				if (xx > (width * numBoxes)) {
					xx = 0;
					yy += height;
					yy++;
				}
			}

		} catch (Exception ex) {
			logger.error("", ex);
		}
	}

	public Canvas getCanvas() {

		if (image == null) {
			canvas = new Canvas();
			gc = canvas.getGraphicsContext2D();

		} else {
			canvas = new Canvas((int) image.getWidth(), (int) image.getHeight());
			gc = canvas.getGraphicsContext2D();
			gc.drawImage(image, 0, 0);
		}

		boxes = new Rectangle[numBoxes];
		resetBoxes(gc);

		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent me) -> {
			if (dragging && currentBox != null) {
				double x = me.getX() - offset.x;
				double y = me.getY() - offset.y;

				currentBox.setX(x);
				currentBox.setY(y);
				redraw(x, y);
			}
		});

		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {

			CanvasPoint p = new CanvasPoint(me);

			currentBox = null;

			for (Rectangle box : boxes) {
				if (box.contains(new Point2D(p.x, p.y))) {
					offset.x = p.x - box.getX();
					offset.y = p.y - box.getY();
					currentBox = box;
				}
			}

			dragging = true;

		});

		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent me) -> {
			dragging = false;
			currentBox = null;
		});

		return canvas;

	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void redraw(double x, double y) {
		gc.drawImage(image, 0, 0);

		for (Rectangle box : boxes) {
			gc.strokeRect(box.getX(), box.getY(), width, height);
		}

	}

	public Image[] getCroppedImages() {

		Image[] images = new Image[numBoxes];

		for (int b = 0; b < numBoxes; b++) {

			ImageView imageView = new ImageView(image);

			SnapshotParameters parameters = new SnapshotParameters();
			parameters.setFill(Color.TRANSPARENT);
			parameters.setViewport(new Rectangle2D(boxes[b].getX(), boxes[b].getY(), boxes[b].getWidth(), boxes[b].getHeight()));

			WritableImage wi = new WritableImage((int) boxes[b].getWidth(), (int) boxes[b].getHeight());
			imageView.snapshot(parameters, wi);

			BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
			BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(), BufferedImage.OPAQUE);

			Graphics2D graphics = bufImageRGB.createGraphics();
			graphics.drawImage(bufImageARGB, 0, 0, null);

			graphics.dispose();

			images[b] = SwingFXUtils.toFXImage(bufImageRGB, null);

		}

		return images;

	}

}
