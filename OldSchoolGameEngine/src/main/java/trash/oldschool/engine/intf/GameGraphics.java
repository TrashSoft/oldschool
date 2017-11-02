package trash.oldschool.engine.intf;

import java.awt.image.BufferedImage;

import trash.oldschool.engine.g2d.GameColor;
import trash.oldschool.engine.g2d.GameHitShape;
import trash.oldschool.engine.g2d.GamePoint;
import trash.oldschool.engine.g2d.GameSprite;

/**
 * All the possible functions may be done with the screen during rendering step.
 */
public interface GameGraphics {

	int screenWidth();
	int screenHeight();

	void rotate(double angle);
	void translate(double tx, double ty);
	void scale(double scaling);
	void resetTransformations();

	void crossHair(double x, double y);
	void drawTriangleStrip(GameColor color, GamePoint... gamePoints);
	void drawTriangles(GameColor[] colors, GamePoint[] gamePoints);

	void drawSprite(String spriteName, int x, int y);
	void drawSprite(GameSprite sprite, double x, double y);
	void drawSprite(BufferedImage image, double x, double y);

	void drawHitShape(GameHitShape hitShape, double x, double y, GameColor color);

	void drawLine(double x1, double y1, double x2, double y2, GameColor color);
	void drawOval(double x, double y, double horizontalRadius, double verticalRadius, GameColor color);
	void fillOval(double x, double y, double horizontalRadius, double verticalRadius, GameColor color);
}
