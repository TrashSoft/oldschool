package trash.oldschool.clojure;

import java.io.IOException;

import clojure.lang.Compiler;
import clojure.lang.RT;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;

public class ClojureRunner {

	public static Object loadAndStartClojureProgram(String filename) {
		Object ret;

		try {
			RT.init();
			ret = Compiler.loadFile(filename);
			logger.debug("Returned by Clojure program: " + ret);
			if(ret != null) {
				logger.debug("Class: " + ret.getClass().getName());
			}
		} catch (IOException e) {
			logger.error("Error occurred when loadding / starting clojure program: " + filename);
			ret = null;
		}

		return ret;
	}

	public Object start(ClojureWrapper wrapper) {
		logger.debug("Wrapper class: " + wrapper.getClass().getName());
		Object ret = wrapper.run(this);
		logger.debug("Returned by callback: " + ret);
		if(ret != null) {
			logger.debug("Class: " + ret.getClass().getName());
		}

		return ret;
	}

	private static final Logger logger = LoggerFactory.createLogger(ClojureRunner.class);
}
