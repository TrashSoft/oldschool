package trash.oldschool.engine;

import trash.oldschool.engine.impl.GameEngine;
import trash.oldschool.engine.intf.GameAdapter;
import trash.oldschool.engine.intf.GameDescriptor;
import trash.oldschool.engine.intf.GameEngineStep;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;
import trash.oldschool.swing.SwingGameAdapter;

/**
 * This class encapsulated the steps of building up a new engine instance.
 */
public class GameEngineBuilder {

	private boolean engineStarted = false;
	private GameEngine engine;

	/**
	 * Creates a new Game Engine instance as registers it as the
	 * commonly available engine.
	 */
	public GameEngineBuilder create(String id, String title, String version, String description) {
		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId(id);
		descriptor.setTitle(title);
		descriptor.setVersion(version);
		descriptor.setDescription(description);

		GameAdapter adapter = new SwingGameAdapter();
		engine = GameEngine.create(descriptor, adapter);

		return this;
	}

	/**
	 * Registers a new step callback for the current engine.
	 */
	public GameEngineBuilder step(String stepName, GameEngineCallback callback) {
		GameEngineStep step = GameEngineStep.valueOf(stepName);
		return step(step, callback);
	}

	/**
	 * Registers a new step callback for the current engine.
	 */
	public GameEngineBuilder step(GameEngineStep step, GameEngineCallback callback) {
		engine.step(step, callback);
		return this;
	}

	public void startEngine() {
		if(!engineStarted) {

			if(engine == null) {
				logger.error("Game Engine wasn't created!");
			} else {
				engine.startInNewWindow();
			}

			engineStarted = true;

		} else {

			logger.error("Game Engine already started!");

		}
	}

	private static final Logger logger = LoggerFactory.createLogger(GameEngineBuilder.class);
}
