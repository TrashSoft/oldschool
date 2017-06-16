package trash.oldschool.engine.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.g2d.GameControlImpl;
import trash.oldschool.engine.intf.GameAdapter;
import trash.oldschool.engine.intf.GameCanvas;
import trash.oldschool.engine.intf.GameControl;
import trash.oldschool.engine.intf.GameDescriptor;
import trash.oldschool.engine.intf.GameEngineStep;
import trash.oldschool.engine.intf.GameGraphics;
import trash.oldschool.engine.intf.GameSpriteLibrary;
import trash.oldschool.engine.intf.GameThread;
import trash.oldschool.engine.intf.GameTimer;
import trash.oldschool.engine.intf.GameWindow;
import trash.oldschool.logging.Logger;
import trash.oldschool.logging.LoggerFactory;

public class GameEngine {

	public static GameEngine create(GameDescriptor descriptor, GameAdapter adapter) {
		GameEngine instance = new GameEngine();
		instance.descriptor = descriptor;
		instance.adapter = adapter;
		instance.control = new GameControlImpl();
		return instance;
	}

	private boolean initialized;

	private GameDescriptor descriptor;
	private GameAdapter adapter;
	private GameWindow window;
	private GameCanvas canvas;
	private GameThread thread;
	private GameTimer timer;
	private GameControl control;

	private Object model;
	private GameEngineFacade facade;
	private GameGraphics graphics;
	private GameSpriteLibrary spriteLibrary;

	private Map<GameEngineStep, List<GameEngineCallback>> callbacks;

	private GameEngine() {
		facade = new GameEngineFacadeImpl(this);
		callbacks = new HashMap<>();

		for(GameEngineStep step : GameEngineStep.values()) {
			List<GameEngineCallback> functions = new ArrayList<GameEngineCallback>();
			callbacks.put(step, functions);
		}

		graphics = null;
		spriteLibrary = new GameSpriteLibraryImpl();
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

	public GameEngineFacade getFacade() {
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

	public synchronized GameEngine runStep(GameEngineStep step, Object object) {
		if(step == GameEngineStep.RENDER) {
			graphics = (GameGraphics) object;
		}

		if(step == GameEngineStep.MODIFY) {
			timer = (GameTimer) object;
		}

		List<GameEngineCallback> list = callbacks.get(step);
		for(GameEngineCallback callback : list) {
			callback.call(facade);
		}

		graphics = null;
		timer = null;
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

	public GameTimer getTimer() {
		if(timer == null) {
			throw new IllegalStateException("No timer currently!");
		}
		return timer;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	private static final Logger logger = LoggerFactory.createLogger(GameEngine.class);
}
