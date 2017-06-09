package trash.oldschool.engine;

public interface GameAdapter {

	GameWindow createWindow(GameEngine engine);
	GameCanvas createCanvas(GameEngine engine);
	GameThread createThread(GameEngine engine);

}
