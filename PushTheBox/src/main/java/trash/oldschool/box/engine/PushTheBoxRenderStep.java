package trash.oldschool.box.engine;

import trash.oldschool.box.model.PushTheBoxMap;
import trash.oldschool.box.model.PushTheBoxModel;
import trash.oldschool.box.model.PushTheBoxModelBox;
import trash.oldschool.box.model.PushTheBoxMonster;
import trash.oldschool.box.model.PushTheBoxPlayer;
import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameEngineFacade;
import trash.oldschool.engine.g2d.GameSprite;
import trash.oldschool.engine.intf.GameGraphics;
import trash.oldschool.engine.intf.GameSpriteLibrary;

public class PushTheBoxRenderStep implements GameEngineCallback {

	private static final int TILE_WIDTH_AND_HEIGHT = 64;

	@Override
	public Object call(GameEngineFacade facade) {
		PushTheBoxModel model = (PushTheBoxModel) facade.model();
		PushTheBoxMap map = model.map;

		int mapWidth = map.getWidth();
		int mapHeight = map.getHeight();
		char[][] mapChars = map.getMap();

		if(mapWidth == 0 || mapHeight == 0) {
			// map is empty; probably showing menu
			//   or game is not initialized right
			return null;
		}

		GameGraphics g = facade.graphics();
		int screenWidth = g.screenWidth();
		int screenHeight = g.screenHeight();

		int horizontalTiles = screenWidth / TILE_WIDTH_AND_HEIGHT;
		if(screenWidth % TILE_WIDTH_AND_HEIGHT > 0) {
			horizontalTiles++;
		}

		int verticalTiles = screenHeight / TILE_WIDTH_AND_HEIGHT;
		if(screenHeight % TILE_WIDTH_AND_HEIGHT > 0) {
			verticalTiles++;
		}

		// TODO: center player, if needed
		int translateXTiles = 0;
		int translateYTiles = 0;

		// 1. Drawing map
		GameSpriteLibrary library = facade.spriteLibrary();
		GameSprite wallSprite = library.byName("wall");

		for(int x = translateXTiles; x < translateXTiles + horizontalTiles; x++) {
			if(x < 0 || x >= mapWidth) {
				continue;
			}

			for(int y = translateYTiles; y < translateYTiles + verticalTiles; y++) {
				if(y < 0 || y >= mapHeight) {
					continue;
				}

				int tileX = x * TILE_WIDTH_AND_HEIGHT;
				int tileY = y * TILE_WIDTH_AND_HEIGHT;

				switch(mapChars[x][y]) {
				case '#':
					g.drawSprite(wallSprite, tileX, tileY);
					break;
				default:
					break;
				}

			} // end for y
		} // end for x

		// 2. Render player
		for(PushTheBoxPlayer playerModel : map.getPlayers()) {
			int x = (int)(playerModel.currentX() * TILE_WIDTH_AND_HEIGHT);
			int y = (int)(playerModel.currentY() * TILE_WIDTH_AND_HEIGHT);

			if(playerModel.alive) {
				GameSprite playerSprite = library.byName("player_" + playerModel.animationDirection);
				g.drawSprite(playerSprite, x, y);
			} else {
				GameSprite playerSprite = library.byName("player_dead");
				g.drawSprite(playerSprite, x, y);
			}
		}

		// 3. Render monsters
		GameSprite monsterSprite = library.byName("monster");
		for(PushTheBoxMonster monsterModel : map.getMonsters()) {
			int x = (int)(monsterModel.currentX() * TILE_WIDTH_AND_HEIGHT);
			int y = (int)(monsterModel.currentY() * TILE_WIDTH_AND_HEIGHT);
			g.drawSprite(monsterSprite, x, y);
		}

		// 4. Render stones
		for(PushTheBoxModelBox boxModel : map.getStones()) {
			int x = (int)(boxModel.currentX() * TILE_WIDTH_AND_HEIGHT);
			int y = (int)(boxModel.currentY() * TILE_WIDTH_AND_HEIGHT);

			GameSprite boxSprite = library.byName("box_" + boxModel.type);
			g.drawSprite(boxSprite, x, y);
		}

		return null;
	}

}
