package trash.oldschool.scifinovel.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameSpriteLibrary;
import trash.oldschool.scifinovel.model.SciFiNovelModel;

public class SciFiNovelInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		facade.setModel(new SciFiNovelModel());

		GameSpriteLibrary library = facade.spriteLibrary();

		library.loadSprite("wall", "images/wall.jpg");

		library.loadSprite("box_0", "images/box_0.jpg");
		library.loadSprite("box_1", "images/box_1.jpg");
		library.loadSprite("box_2", "images/box_2.jpg");
		library.loadSprite("box_3", "images/box_3.jpg");
		library.loadSprite("box_4", "images/box_4.jpg");
		library.loadSprite("box_5", "images/box_5.jpg");
		library.loadSprite("box_6", "images/box_6.jpg");

		library.loadSprite("player", "images/player.jpg");
		library.loadSprite("player_dead", "images/player_dead.jpg");

		library.loadSprite("player_left", "images/player_left.jpg");
		library.loadSprite("player_right", "images/player_right.jpg");
		library.loadSprite("player_up", "images/player_up.jpg");
		library.loadSprite("player_down", "images/player_down.jpg");

		library.loadSprite("monster", "images/monster.jpg");

		facade.control().registerKeyListener(new SciFiNovelSpecialKeys());

		return null;
	}

}
