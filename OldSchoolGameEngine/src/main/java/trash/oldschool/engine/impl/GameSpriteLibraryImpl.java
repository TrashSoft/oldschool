package trash.oldschool.engine.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import trash.oldschool.engine.g2d.GameSprite;
import trash.oldschool.engine.intf.GameSpriteLibrary;

public class GameSpriteLibraryImpl implements GameSpriteLibrary {

	private Map<String, GameSprite> filenames = new HashMap<>();
	private Map<String, GameSprite> names = new HashMap<>();

	@Override
	public GameSprite loadSprite(String name, String filename) {
		GameSprite sprite = filenames.get(filename);

		if(sprite == null) {

			BufferedImage bufferedImage;

			try {
				File input = new File(filename);
				bufferedImage = ImageIO.read(input);
			} catch (IOException ex) {
				throw new RuntimeException("Couldn't load: " + filename, ex);
			}

			sprite = new GameSprite(bufferedImage);
			filenames.put(filename, sprite);
		}

		names.put(name, sprite);
		return sprite;
	}

	public GameSprite byName(String name) {
		return names.get(name);
	}
}
