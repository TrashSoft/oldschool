package trash.oldschool.facade;

import trash.oldschool.engine.GameGraphics;
import trash.oldschool.engine.g2d.GameSpriteLibrary;
import trash.oldschool.logging.Logger;

/**
 * Everything that matters during running the engine is here.
 */
public interface Facade {

	GameGraphics graphics();
	GameSpriteLibrary library();
	Logger logger();

}
