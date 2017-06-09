package trash.oldschool.clojure;

import java.io.IOException;

import clojure.lang.Compiler;
import clojure.lang.RT;
import trash.oldschool.engine.GameEngine;
import trash.oldschool.engine.GameEngineStep;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;

public class ClojureRunner {

	public static Object loadAndStartClojureProgram(String filename) {
		Object ret;

		try {
			RT.init();
			ret = Compiler.loadFile(filename);
			if(ret != null) {
				logger.debug("Class of returned object: " + ret.getClass().getName());
			} else {
				logger.debug("No object is returned.");
			}

			if(ret == null || !ret.getClass().equals(GameEngine.class)) {
				logger.error("An instance of `trash.oldschool.engine.GameEngine` class should be returned!");
				ret = null;
			}

			if(ret != null) {
				GameEngine engine = (GameEngine) ret;
				startGameEngine(engine);
			}
		} catch (IOException e) {
			logger.error("Error occurred when loadding / starting clojure program: " + filename);
			ret = null;
		}

		return ret;
	}

	private static void startGameEngine(GameEngine engine) {
		engine.runStep(GameEngineStep.INIT, null);
		engine.runStep(GameEngineStep.BUILD, null);
		engine.startInNewWindow();
	}

	private static final Logger logger = LoggerFactory.createLogger(ClojureRunner.class);
}
