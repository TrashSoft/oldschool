package trash.oldschool.facade;

import trash.oldschool.engine.GameControl;
import trash.oldschool.engine.GameGraphics;
import trash.oldschool.engine.GameTimer;
import trash.oldschool.engine.g2d.GameSpriteLibrary;
import trash.oldschool.logging.Logger;

/**
 * Everything that matters during running the engine is here.
 */
public interface Facade {

	Object model();
	Object setModel(Object model);
	GameGraphics graphics();
	GameSpriteLibrary spriteLibrary();
	GameControl control();
	GameTimer timer();
	Logger logger();

}
