package trash.oldschool.facade;

import trash.oldschool.engine.GameControl;
import trash.oldschool.engine.GameEngine;
import trash.oldschool.engine.GameGraphics;
import trash.oldschool.engine.GameTimer;
import trash.oldschool.engine.g2d.GameSpriteLibrary;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;

public class GameEngineFacade implements Facade {

	private GameEngine engine;

	public GameEngineFacade(GameEngine engine) {
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
