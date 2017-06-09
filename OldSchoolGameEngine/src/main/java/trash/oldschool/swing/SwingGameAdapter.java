package trash.oldschool.swing;

import trash.oldschool.engine.GameAdapter;
import trash.oldschool.engine.GameCanvas;
import trash.oldschool.engine.GameEngine;
import trash.oldschool.engine.GameThread;
import trash.oldschool.engine.GameWindow;

public class SwingGameAdapter implements GameAdapter {

	@Override
	public GameWindow createWindow(GameEngine engine) {
		SwingFrame frame = new SwingFrame(engine);
		return frame;
	}

	@Override
	public GameCanvas createCanvas(GameEngine engine) {
		return new SwingCanvas(engine);
	}

	@Override
	public GameThread createThread(GameEngine engine) {
		return new SwingThread(engine);
	}
}
