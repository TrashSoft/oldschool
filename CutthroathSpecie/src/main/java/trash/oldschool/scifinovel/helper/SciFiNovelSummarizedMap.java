package trash.oldschool.scifinovel.helper;

import java.awt.Point;

import trash.oldschool.scifinovel.model.SciFiNovelMap;
import trash.oldschool.scifinovel.model.SciFiNovelModelBox;
import trash.oldschool.scifinovel.model.SciFiNovelMonster;

public class SciFiNovelSummarizedMap {

	private int width;
	private int height;
	private boolean[][] map;

	public SciFiNovelSummarizedMap(SciFiNovelMap initMap) {
		this.width = initMap.getWidth();
		this.height = initMap.getHeight();
		this.map = new boolean[this.width][this.height];

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				map[x][y] = (initMap.getTile(x, y) == ' ');
			}
		}

		for(SciFiNovelModelBox boxModel : initMap.getBoxes()) {
			map[boxModel.position.x][boxModel.position.y] = false;
			map[boxModel.target.x][boxModel.target.y] = false;
		}

		for(SciFiNovelMonster monster : initMap.getMonsters()) {
			map[monster.position.x][monster.position.y] = false;
			map[monster.target.x][monster.target.y] = false;
		}
	}

	public boolean free(Point p, Point d) {
		int x = p.x + (d == null ? 0 : d.x);
		int y = p.y + (d == null ? 0 : d.y);
		return free(x, y);
	}

	public boolean free(int x, int y) {
		if(x < 0 || x >= width)
			return false;

		if(y < 0 || y >= height) {
			return false;
		}
		return map[x][y];
	}

	public boolean wallAround(Point p, Point d) {
		int px = p.x + (d == null ? 0 : d.x);
		int py = p.y + (d == null ? 0 : d.y);

		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(x == 0 && y == 0) {
					continue; // this wall is the monster itself
				}

				boolean wall = !free(px + x, py + y);
				if(wall) {
					return true;
				}
			}
		}

		return false;
	}
}
