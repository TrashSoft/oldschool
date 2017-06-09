package trash.oldschool.engine.impl;

import trash.oldschool.engine.GameEngine;
import trash.oldschool.engine.GameThread;
import trash.oldschool.engine.GameWindowListener;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;

public class DefaultGameWindowListener implements GameWindowListener {

	private GameThread thread;
	private GameEngine engine;

	public DefaultGameWindowListener(GameThread thread, GameEngine engine) {
		this.thread = thread;
		this.engine = engine;
	}

	@Override
	public void onFinishingWork() {
		logger.info("Interrupting thread.");
		thread.interrupt();

		logger.info("Disposing engine.");
		engine.dispose();

		logger.info("Default game window listener finished.");
	}

	private static final Logger logger = LoggerFactory.createLogger(DefaultGameWindowListener.class);
}
