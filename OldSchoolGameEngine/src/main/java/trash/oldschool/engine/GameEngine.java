package trash.oldschool.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trash.oldschool.engine.g2d.GameControlImpl;
import trash.oldschool.engine.g2d.GameSpriteLibrary;
import trash.oldschool.engine.impl.DefaultGameWindowListener;
import trash.oldschool.facade.Facade;
import trash.oldschool.facade.GameEngineFacade;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;

public class GameEngine {

	public static GameEngine create(GameDescriptor descriptor, GameAdapter adapter) {
		GameEngine engine = new GameEngine();
		engine.descriptor = descriptor;
		engine.adapter = adapter;
		engine.control = new GameControlImpl();
		return engine;
	}

	private boolean initialized;
	
	private GameDescriptor descriptor;
	private GameAdapter adapter;
	private GameWindow window;
	private GameCanvas canvas;
	private GameThread thread;
	private GameControl control;

	private Object model;
	private Facade facade;
	private GameGraphics graphics;
	private GameSpriteLibrary spriteLibrary;

	private Map<GameEngineStep, List<GameEngineCallback>> callbacks;

	private GameEngine() {
		facade = new GameEngineFacade(this);
		callbacks = new HashMap<>();

		for(GameEngineStep step : GameEngineStep.values()) {
			callbacks.put(step, new ArrayList<GameEngineCallback>());
		}

		graphics = null;
		spriteLibrary = new GameSpriteLibrary();
		initialized = false;
	}

	public void startInNewWindow() {
		logger.info("Starting in new window.");
		GameWindow gameWindow = adapter.createWindow(this);
		startIn(gameWindow);
	}

	public void startIn(GameWindow gameWindow) {
		if(!initialized) {
			logger.info("Running initialization.");
			runStep(GameEngineStep.INIT, null);
			logger.info("Running build step.");
			runStep(GameEngineStep.BUILD, null);
			logger.info("Engine initialized.");
			initialized = true;
		}

		this.window = gameWindow;
		this.canvas = adapter.createCanvas(this);
		this.thread = adapter.createThread(this);

		thread.start();
		window.add(canvas);
		window.register(new DefaultGameWindowListener(this.thread, this));
		window.showWindow();
	}

	public GameDescriptor getDescriptor() {
		return descriptor;
	}

	public GameCanvas getCanvas() {
		return canvas;
	}

	public GameControl getControl() {
		return control;
	}

	public GameWindow getWindow() {
		return window;
	}

	public Facade getFacade() {
		return facade;
	}

	public GameEngine step(String stepName, GameEngineCallback callback) {
		GameEngineStep step = GameEngineStep.valueOf(stepName);
		return step(step, callback);
	}

	public GameEngine step(GameEngineStep step, GameEngineCallback callback) {
		callbacks.get(step).add(callback);
		return this;
	}

	public GameEngine removeStep(String stepName, GameEngineCallback callback) {
		GameEngineStep step = GameEngineStep.valueOf(stepName);
		return removeStep(step, callback);
	}

	public GameEngine removeStep(GameEngineStep step, GameEngineCallback callback) {
		callbacks.get(step).remove(callback);
		return this;
	}

	public GameEngine runStep(GameEngineStep step, Object object) {
		if(step == GameEngineStep.RENDER) {
			graphics = (GameGraphics) object;
		}

		List<GameEngineCallback> list = callbacks.get(step);
		for(GameEngineCallback callback : list) {
			callback.call(facade);
		}

		graphics = null;
		return this;
	}

	public GameGraphics getGraphics() {
		if(graphics == null) {
			throw new IllegalStateException("No graphics currently!");
		}
		return graphics;
	}

	public GameSpriteLibrary getSpriteLibrary() {
		return spriteLibrary;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	private static final Logger logger = LoggerFactory.createLogger(GameEngine.class);
}
