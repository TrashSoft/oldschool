package trash.oldschool.engine.impl;

import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameControl;
import trash.oldschool.engine.intf.GameGraphics;
import trash.oldschool.engine.intf.GameSpriteLibrary;
import trash.oldschool.engine.intf.GameTimer;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;

public class GameEngineFacadeImpl implements GameEngineFacade {

	private GameEngine engine;

	public GameEngineFacadeImpl(GameEngine engine) {
		this.engine = engine;
	}

	@Override
	public Object model() {
		return engine.getModel();
	}

	@Override
	public Object setModel(Object model) {
		engine.setModel(model);
		return model;
	}

	@Override
	public GameEngineFacade requestRebuild() {
		return engine.requestRebuild();
	}

	@Override
	public GameGraphics graphics() {
		return engine.getGraphics();
	}

	@Override
	public GameSpriteLibrary spriteLibrary() {
		return engine.getSpriteLibrary();
	}

	@Override
	public GameTimer timer() {
		return engine.getTimer();
	}

	@Override
	public GameControl control() {
		return engine.getControl();
	}

	@Override
	public Logger logger() {
		return commonLogger;
	}

	private static final Logger commonLogger = LoggerFactory.createLogger(CommonLogger.class);
}
