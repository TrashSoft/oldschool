package trash.oldschool.swing;

import trash.oldschool.engine.GameCanvas;
import trash.oldschool.engine.GamePhysics;
import trash.oldschool.engine.GameThread;

public class SwingThread extends Thread implements GameThread {

	private GamePhysics physics;
	private GameCanvas canvas;

	private long nanoTimeLastTime = System.nanoTime();
	private long nanoTimeNow = System.nanoTime();

	public SwingThread(GamePhysics physics, GameCanvas canvas) {
		this.physics = physics;
		this.canvas = canvas;
	}

	@Override
	public void run() {
		try {

			while(true) {
				nanoTimeNow = System.nanoTime();
				physics.call(nanoTimeNow, nanoTimeLastTime);
				nanoTimeLastTime = nanoTimeNow;
				canvas.repaint();
				Thread.sleep(10);
			}

		} catch(InterruptedException ex) {
			return;
		}
	}

}