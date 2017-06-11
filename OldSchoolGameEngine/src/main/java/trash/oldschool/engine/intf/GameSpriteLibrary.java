package trash.oldschool.engine.intf;

import trash.oldschool.engine.g2d.GameSprite;

/**
 * Loading and getting sprites.
 */
public interface GameSpriteLibrary {

	/**
	 * Loads sprite from file and registers with the given name.
	 */
	GameSprite loadSprite(String name, String filename);

	/**
	 * Gets the sprite referenced by the name parameter.
	 */
	GameSprite byName(String name);

}
