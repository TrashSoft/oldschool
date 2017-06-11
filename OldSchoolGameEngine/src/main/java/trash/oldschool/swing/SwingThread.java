package trash.oldschool.swing;

import trash.oldschool.engine.impl.GameEngine;
import trash.oldschool.engine.intf.GameCanvas;
import trash.oldschool.engine.intf.GameEngineStep;
import trash.oldschool.engine.intf.GameThread;
import trash.oldschool.engine.intf.GameTimer;

public class SwingThread extends Thread implements GameThread, GameTimer {

	private GameEngine engine;
	private GameCanvas canvas;

	private long nanoTimeOnLastCall = System.nanoTime();
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
				nanoTimeOnLastCall = nanoTimeNow;
				canvas.repaint();
				Thread.sleep(10);
			}

		} catch(InterruptedException ex) {
			return;
		}
	}

	@Override
	public long getNanoTimeOnLastCall() {
		return nanoTimeOnLastCall;
	}

	@Override
	public long getNanoTimeNow() {
		return nanoTimeNow;
	}

	@Override
	public double elapsedTime() {
		long elapsedTimeInNanos = nanoTimeNow - nanoTimeOnLastCall;
		double elapsedTime = elapsedTimeInNanos / 1000000000.0;
		return elapsedTime;
	}

}