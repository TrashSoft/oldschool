package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.intf.GameSpriteLibrary;
import trash.oldschool.mole.model.MoleModel;

public class MoleInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		facade.setModel(new MoleModel());

		GameSpriteLibrary library = facade.spriteLibrary();

		library.loadSprite("wall", "images/wall.jpg");
		library.loadSprite("dirt", "images/dirt.jpg");
		library.loadSprite("exit", "images/exit.jpg");

		library.loadSprite("stone", "images/stone.jpg");
		library.loadSprite("diamond", "images/diamond.jpg");

		library.loadSprite("player", "images/player.jpg");
		library.loadSprite("dead_player", "images/dead_player.jpg");

		library.loadSprite("monster", "images/monster.jpg");
		library.loadSprite("dead_monster", "images/dead_monster.jpg");

		facade.control().registerKeyListener(new MoleSpecialKeys());

		return null;
	}

}
