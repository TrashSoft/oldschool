package trash.oldschool.engine;

import trash.oldschool.engine.intf.GameControl;
import trash.oldschool.engine.intf.GameGraphics;
import trash.oldschool.engine.intf.GameSpriteLibrary;
import trash.oldschool.engine.intf.GameTimer;
import trash.oldschool.logging.Logger;

/**
 * Everything that matters during running the engine is here.
 */
public interface GameEngineFacade {

	/**
	 * Gets the model object registered in the engine.
	 */
	Object model();

	/**
	 * Asks for new re-run of the BUILD step.
	 */
	GameEngineFacade requestRebuild();

	/**
	 * @return Returns the input parameter.
	 */
	Object setModel(Object model);

	/**
	 * Gets the graphics interface for rendering.
	 * Only available during rendering step, otherwise an exception will be thrown.
	 */
	GameGraphics graphics();

	/**
	 * Gets the sprite library.
	 */
	GameSpriteLibrary spriteLibrary();

	/**
	 * Gets the game control interface.
	 */
	GameControl control();

	/**
	 * Gets the timer interface.
	 * Only available in the modifying step, otherwise this method throws an exception.
	 */
	GameTimer timer();

	/**
	 * Gets the common logger object.
	 */
	Logger logger();

}
