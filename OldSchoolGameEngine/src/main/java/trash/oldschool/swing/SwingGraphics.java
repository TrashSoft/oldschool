package trash.oldschool.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import trash.oldschool.engine.g2d.GameColor;
import trash.oldschool.engine.g2d.GameHitBox;
import trash.oldschool.engine.g2d.GameHitCircle;
import trash.oldschool.engine.g2d.GameHitShape;
import trash.oldschool.engine.g2d.GamePoint;
import trash.oldschool.engine.g2d.GameSprite;
import trash.oldschool.engine.impl.GameEngine;
import trash.oldschool.engine.intf.GameGraphics;

public class SwingGraphics implements GameGraphics {

	private int[] xPoints = new int[3];
	private int[] yPoints = new int[3];

	private GameEngine engine;
	private Graphics2D g;
	private int width;
	private int height;

	private AffineTransform originalTransformation;

	public SwingGraphics(GameEngine engine, Graphics2D g2d, int width, int height) {
		this.engine = engine;
		this.g = g2d;
		this.width = width;
		this.height = height;
	}

	@Override
	public int screenWidth() {
		return width;
	}

	@Override
	public int screenHeight() {
		return height;
	}

	@Override
	public void rotate(double angle) {
		if(originalTransformation == null)
			originalTransformation = g.getTransform();
		g.rotate(angle);
	}

	@Override
	public void translate(double tx, double ty) {
		if(originalTransformation == null)
			originalTransformation = g.getTransform();
		g.translate(tx, ty);
	}

	@Override
	public void scale(double scaling) {
		if(originalTransformation == null)
			originalTransformation = g.getTransform();
		g.scale(scaling, scaling);
	}

	@Override
	public void resetTransformations() {
		g.setTransform(originalTransformation);
		originalTransformation = null;
	}

	@Override
	public void crossHair(double x, double y) {
		g.setColor(Color.black);
		g.drawLine((int)(x - 20), (int)(y), (int)(x + 20), (int)(y));
		g.drawLine((int)(x), (int)(y - 20), (int)(x), (int)(y + 20));
	}

	@Override
	public void drawTriangleStrip(GameColor color, GamePoint... gamePoints) {
		int len = gamePoints.length;
		g.setColor(new Color(color.r, color.g, color.b, color.a));

		int a = 0;
		while(a < len - 2) {
			for(int i = 0; i < 3; i++) {
				GamePoint gp = gamePoints[a+i];
				xPoints[i] = (int)gp.x;
				yPoints[i] = (int)gp.y;
			} // end for

			g.fillPolygon(xPoints, yPoints, 3);
			a++;
		} // end while

	}

	@Override
	public void drawTriangles(GameColor[] colors, GamePoint[] gamePoints) {
		int len = colors.length;
		if(len * 3 != gamePoints.length) {
			throw new IllegalArgumentException("Color and triangle count is not the same.");
		}

		for(int a = 0; a < len; a++) {
			GameColor color = colors[a];
			g.setColor(toSwingColor(color));

			for(int i = 0; i < 3; i++) {
				GamePoint gp = gamePoints[a*3 + i];
				xPoints[i] = (int)gp.x;
				yPoints[i] = (int)gp.y;
			} // end for

			g.fillPolygon(xPoints, yPoints, 3);
		} // end while

	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2, GameColor color) {
		g.setColor(toSwingColor(color));
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}

	@Override
	public void drawOval(double x, double y, double horizontalRadius, double verticalRadius, GameColor color) {
		g.setColor(toSwingColor(color));
		g.drawOval((int)(x-horizontalRadius), (int)(y-verticalRadius), (int)(horizontalRadius*2), (int)(verticalRadius*2));
	}

	@Override
	public void fillOval(double x, double y, double horizontalRadius, double verticalRadius, GameColor color) {
		g.setColor(toSwingColor(color));
		g.fillOval((int)(x-horizontalRadius), (int)(y-verticalRadius), (int)(horizontalRadius*2), (int)(verticalRadius*2));
	}

	@Override
	public void drawSprite(String spriteName, int x, int y) {
		GameSprite sprite = engine.getSpriteLibrary().byName(spriteName);
		g.drawImage(sprite.getImage(), x, y, null);
	}

	@Override
	public void drawSprite(GameSprite sprite, double x, double y) {
		g.drawImage(sprite.getImage(), (int)x, (int)y, null);
	}

	@Override
	public void drawSprite(BufferedImage image, double x, double y) {
		g.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void drawHitShape(GameHitShape hitShape, double x, double y, GameColor color) {
		g.setColor(toSwingColor(color));

		// TODO: no instanceof !!!

		if(hitShape instanceof GameHitBox) {
			GameHitBox box = (GameHitBox) hitShape;
			g.drawRect((int)(x + box.minX), (int)(y + box.minY), (int)(box.maxX - box.minX), (int)(box.maxY - box.minY));
		}

		if(hitShape instanceof GameHitCircle) {
			GameHitCircle circle = (GameHitCircle) hitShape;
			g.drawOval((int)(x + circle.x - circle.r), (int)(y + circle.y - circle.r), (int)(2 * circle.r), (int)(2 * circle.r));
		}
	}

	private Color toSwingColor(GameColor color) {
		return new Color(color.r, color.g, color.b, color.a);
	}

}
