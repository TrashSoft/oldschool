package trash.oldschool.scifinovel.model;

import java.awt.Point;

public class SciFiNovelMonster extends SciFiNovelMoveable {

	public Point direction = new Point(1, 0);

	public SciFiNovelMonster(int x, int y) {
		super(x, y);
	}


	public Point rotateLeft() {
		if(direction.x == 1 && direction.y == 0)  {
			direction.x = 0;
			direction.y = -1;
			return direction;
		}

		if(direction.x == 0 && direction.y == -1)  {
			direction.x = -1;
			direction.y = 0;
			return direction;
		}

		if(direction.x == -1 && direction.y == 0)  {
			direction.x = 0;
			direction.y = 1;
			return direction;
		}

		direction.x = 1;
		direction.y = 0;
		return direction;
	}

	public Point rotateRight() {
		if(direction.x == 1 && direction.y == 0)  {
			direction.x = 0;
			direction.y = 1;
			return direction;
		}

		if(direction.x == 0 && direction.y == 1)  {
			direction.x = -1;
			direction.y = 0;
			return direction;
		}

		if(direction.x == -1 && direction.y == 0)  {
			direction.x = 0;
			direction.y = -1;
			return direction;
		}

		direction.x = 1;
		direction.y = 0;
		return direction;
	}


	public void moveForward() {
		int tx = position.x + (direction == null ? 0 : direction.x);
		int ty = position.y + (direction == null ? 0 : direction.y);
		move(tx, ty);
	}
}
