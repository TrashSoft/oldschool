package trash.oldschool.swing;

import trash.oldschool.engine.impl.GameEngine;
import trash.oldschool.engine.intf.GameAdapter;
import trash.oldschool.engine.intf.GameCanvas;
import trash.oldschool.engine.intf.GameThread;
import trash.oldschool.engine.intf.GameWindow;

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
