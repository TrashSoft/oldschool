package trash.oldschool.mole.engine;

import java.awt.Point;

import trash.oldschool.engine.GameEngineCallback;
import trash.oldschool.engine.GameGraphics;
import trash.oldschool.engine.g2d.GameSprite;
import trash.oldschool.engine.g2d.GameSpriteLibrary;
import trash.oldschool.facade.Facade;
import trash.oldschool.mole.gameplay.MoleMap;
import trash.oldschool.mole.gameplay.MoleModel;
import trash.oldschool.mole.gameplay.MoleMonster;
import trash.oldschool.mole.gameplay.MoleStone;

public class MoleRenderStep implements GameEngineCallback {

	private static final int TILE_WIDTH_AND_HEIGHT = 64;

	@Override
	public Object call(Facade facade) {
		MoleModel model = (MoleModel) facade.model();
		MoleMap map = model.map;
		
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
		GameSpriteLibrary library = facade.library();
		GameSprite wall = library.byName("wall");
		GameSprite dirt = library.byName("dirt");
		GameSprite exit = library.byName("exit");
		GameSprite diamond = library.byName("diamond");
		
		Point exitPosition = map.getExitPosition();
		boolean readyToExit = map.isReadyToExit();
		
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
				case '.':
					g.drawSprite(dirt, tileX, tileY);
					break;
				case '#':
					
					if(readyToExit && x == exitPosition.x && y == exitPosition.y) {
						g.drawSprite(exit, tileX, tileY);
					} else {
						g.drawSprite(wall, tileX, tileY);
					}
					
					break;
				case '*':
					g.drawSprite(diamond, tileX, tileY);
					break;
				default:
					break;
				}
				
			} // end for y
		} // end for x
		
		// 2. Render stones
		GameSprite stone = library.byName("stone");
		for(MoleStone s : map.getStones()) {
			int x = s.position.x * TILE_WIDTH_AND_HEIGHT;
			int y = s.position.y * TILE_WIDTH_AND_HEIGHT;
			g.drawSprite(stone, x, y);
		}
		
		// 3. Render player
		GameSprite player = library.byName("player");
		GameSprite deadPlayer = library.byName("dead_player");
		{
			int x = map.getPlayerPosition().x * TILE_WIDTH_AND_HEIGHT;
			int y = map.getPlayerPosition().y * TILE_WIDTH_AND_HEIGHT;
			g.drawSprite(player, x, y);
		}
		
		// 4. Render monsters
		GameSprite monster = library.byName("monster");
		GameSprite deadMonster = library.byName("dead_monster");
		for(MoleMonster m : map.getMonsters()) {
			int x = m.position.x * TILE_WIDTH_AND_HEIGHT;
			int y = m.position.y * TILE_WIDTH_AND_HEIGHT;
			g.drawSprite(monster, x, y);
		}
		
		return null;
	}

}
