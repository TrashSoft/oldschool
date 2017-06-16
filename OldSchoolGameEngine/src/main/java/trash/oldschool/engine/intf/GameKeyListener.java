package trash.oldschool.engine.intf;

import trash.oldschool.engine.GameEngineFacade;

/**
 * Listener interface for checking a special key.
 */
public interface GameKeyListener {

	void hit(GameEngineFacade facade, char key);

}
