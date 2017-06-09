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

public class GameEngine {

	public static GameEngine create(GameDescriptor descriptor, GameAdapter adapter) {
		GameEngine engine = new GameEngine();
		engine.descriptor = descriptor;
		engine.adapter = adapter;
		engine.control = new GameControlImpl();
		return engine;
	}

	private GameDescriptor descriptor;
	private GameAdapter adapter;
	private GameWindow window;
	private GameCanvas canvas;
	private GameThread thread;
	private GameControl control;

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
	}

	public void startInNewWindow() {
		GameWindow gameWindow = adapter.createWindow(this);
		startIn(gameWindow);
	}

	public void startIn(GameWindow gameWindow) {
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
}
