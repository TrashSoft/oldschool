package trash.oldschool.engine;

import java.awt.image.BufferedImage;

import trash.oldschool.engine.g2d.GameColor;
import trash.oldschool.engine.g2d.GameHitShape;
import trash.oldschool.engine.g2d.GamePoint;
import trash.oldschool.engine.g2d.GameSprite;

public interface GameGraphics {

	int screenWidth();
	int screenHeight();

	void rotate(double angle);
	void translate(double tx, double ty);

	void crossHair(double x, double y);
	void drawTriangleStrip(GameColor color, GamePoint... gamePoints);
	void drawTriangles(GameColor[] colors, GamePoint[] gamePoints);
	void drawSprite(String spriteName, int x, int y);
	void drawSprite(GameSprite sprite, double x, double y);
	void drawSprite(BufferedImage image, double x, double y);
	void drawHitShape(GameHitShape hitShape, double x, double y, GameColor color);

}
