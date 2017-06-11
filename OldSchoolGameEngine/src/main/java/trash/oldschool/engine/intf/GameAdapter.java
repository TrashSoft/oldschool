package trash.oldschool.engine.intf;

import trash.oldschool.engine.impl.GameEngine;

public interface GameAdapter {

	GameWindow createWindow(GameEngine engine);
	GameCanvas createCanvas(GameEngine engine);
	GameThread createThread(GameEngine engine);

}
