package trash.oldschool.engine.g2d;

import java.awt.image.BufferedImage;

public class GameSprite {

	public GameSprite(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int width() {
		return image.getWidth();
	}

	public int height() {
		return image.getHeight();
	}

	private BufferedImage image;
}
