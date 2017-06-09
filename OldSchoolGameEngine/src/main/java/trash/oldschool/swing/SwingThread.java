package trash.oldschool.swing;

import trash.oldschool.engine.GameCanvas;
import trash.oldschool.engine.GameEngine;
import trash.oldschool.engine.GameEngineStep;
import trash.oldschool.engine.GameThread;

public class SwingThread extends Thread implements GameThread {

	private GameEngine engine;
	private GameCanvas canvas;

	private long nanoTimeLastTime = System.nanoTime();
	private long nanoTimeNow = System.nanoTime();

	public SwingThread(GameEngine engine) {
		this.engine = engine;
		this.canvas = engine.getCanvas();
	}

	@Override
	public void run() {
		try {

			while(true) {
				nanoTimeNow = System.nanoTime();
				engine.runStep(GameEngineStep.MODIFY, this);
				nanoTimeLastTime = nanoTimeNow;
				canvas.repaint();
				Thread.sleep(10);
			}

		} catch(InterruptedException ex) {
			return;
		}
	}

	public long getNanoTimeLastTime() {
		return nanoTimeLastTime;
	}

	public long getNanoTimeNow() {
		return nanoTimeNow;
	}

}