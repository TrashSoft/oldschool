package trash.oldschool.engine;

import trash.oldschool.engine.intf.GameEngineStep;

public interface GameEngineCallback {

	/**
	 * Handling of a step in the Game Engine's work.
	 * @see GameEngineStep
	 */
	Object call(GameEngineFacade facade);

}
