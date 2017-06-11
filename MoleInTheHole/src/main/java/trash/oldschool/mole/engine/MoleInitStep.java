package trash.oldschool.mole.engine;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.g2d.GameSpriteLibrary;
import trash.oldschool.facade.Facade;
import trash.oldschool.mole.model.MoleModel;

public class MoleInitStep implements GameEngineCallback {

	@Override
	public Object call(Facade facade) {
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
		
		return null;
	}

}
